// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.core.http.policy;

import com.azure.core.http.ContentType;
import com.azure.core.http.HttpHeader;
import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpPipelineCallContext;
import com.azure.core.http.HttpPipelineNextPolicy;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.HttpResponse;
import com.azure.core.implementation.AccessibleByteArrayOutputStream;
import com.azure.core.implementation.ImplUtils;
import com.azure.core.implementation.http.HttpPipelineCallContextHelper;
import com.azure.core.implementation.jackson.ObjectMapperShim;
import com.azure.core.implementation.logging.LoggingKeys;
import com.azure.core.util.Context;
import com.azure.core.util.CoreUtils;
import com.azure.core.util.FluxUtil;
import com.azure.core.util.UrlBuilder;
import com.azure.core.util.logging.ClientLogger;
import com.azure.core.util.logging.LogLevel;
import com.azure.core.util.logging.LoggingEventBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * The pipeline policy that handles logging of HTTP requests and responses.
 */
public class HttpLoggingPolicy implements HttpPipelinePolicy {
    private static final ObjectMapperShim PRETTY_PRINTER = ObjectMapperShim.createPrettyPrintMapper();
    private static final int MAX_BODY_LOG_SIZE = 1024 * 16;
    private static final String REDACTED_PLACEHOLDER = "REDACTED";

    // Use a cache to retain the caller method ClientLogger.
    //
    // The same method may be called thousands or millions of times, so it is wasteful to create a new logger instance
    // each time the method is called. Instead, retain the created ClientLogger until a certain number of unique method
    // calls have been made and then clear the cache and rebuild it. Long term, this should be replaced with an LRU,
    // or another type of cache, for better cache management.
    private static final int LOGGER_CACHE_MAX_SIZE = 1000;
    private static final Map<String, ClientLogger> CALLER_METHOD_LOGGER_CACHE = new ConcurrentHashMap<>();

    private static final ClientLogger LOGGER = new ClientLogger(HttpLoggingPolicy.class);

    private final HttpLogDetailLevel httpLogDetailLevel;
    private final Set<String> allowedHeaderNames;
    private final Set<String> allowedQueryParameterNames;
    private final boolean prettyPrintBody;

    private final HttpRequestLogger requestLogger;
    private final HttpResponseLogger responseLogger;

    /**
     * Key for {@link Context} to pass request retry count metadata for logging.
     */
    public static final String RETRY_COUNT_CONTEXT = "requestRetryCount";

    private static final String REQUEST_LOG_MESSAGE = "HTTP request";
    private static final String RESPONSE_LOG_MESSAGE = "HTTP response";

    /**
     * Creates an HttpLoggingPolicy with the given log configurations.
     *
     * @param httpLogOptions The HTTP logging configuration options.
     */
    public HttpLoggingPolicy(HttpLogOptions httpLogOptions) {
        if (httpLogOptions == null) {
            this.httpLogDetailLevel = HttpLogDetailLevel.NONE;
            this.allowedHeaderNames = Collections.emptySet();
            this.allowedQueryParameterNames = Collections.emptySet();
            this.prettyPrintBody = false;

            this.requestLogger = new DefaultHttpRequestLogger();
            this.responseLogger = new DefaultHttpResponseLogger();
        } else {
            this.httpLogDetailLevel = httpLogOptions.getLogLevel();
            this.allowedHeaderNames = httpLogOptions.getAllowedHeaderNames()
                .stream()
                .map(headerName -> headerName.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());
            this.allowedQueryParameterNames = httpLogOptions.getAllowedQueryParamNames()
                .stream()
                .map(queryParamName -> queryParamName.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());
            this.prettyPrintBody = httpLogOptions.isPrettyPrintBody();

            this.requestLogger = (httpLogOptions.getRequestLogger() == null)
                ? new DefaultHttpRequestLogger()
                : httpLogOptions.getRequestLogger();
            this.responseLogger = (httpLogOptions.getResponseLogger() == null)
                ? new DefaultHttpResponseLogger()
                : httpLogOptions.getResponseLogger();
        }
    }

    @Override
    public Mono<HttpResponse> process(HttpPipelineCallContext context, HttpPipelineNextPolicy next) {
        // No logging will be performed, trigger a no-op.
        if (httpLogDetailLevel == HttpLogDetailLevel.NONE) {
            return next.process();
        }


        final ClientLogger logger = getOrCreateMethodLogger((String) context.getData("caller-method").orElse(""));
        final long startNs = System.nanoTime();

        return requestLogger.logRequest(logger, getRequestLoggingOptions(context))
            .then(next.process())
            .flatMap(response -> responseLogger.logResponse(logger,
                getResponseLoggingOptions(response, startNs, context)))
            .doOnError(throwable -> logger.warning("<-- HTTP FAILED: ", throwable));
    }

    private HttpRequestLoggingContext getRequestLoggingOptions(HttpPipelineCallContext callContext) {
        return new HttpRequestLoggingContext(callContext.getHttpRequest(),
            HttpPipelineCallContextHelper.getContext(callContext),
            getRequestRetryCount(HttpPipelineCallContextHelper.getContext(callContext)));
    }

    private HttpResponseLoggingContext getResponseLoggingOptions(HttpResponse httpResponse, long startNs,
        HttpPipelineCallContext callContext) {
        return new HttpResponseLoggingContext(httpResponse, Duration.ofNanos(System.nanoTime() - startNs),
            HttpPipelineCallContextHelper.getContext(callContext),
            getRequestRetryCount(HttpPipelineCallContextHelper.getContext(callContext)));
    }

    private final class DefaultHttpRequestLogger implements HttpRequestLogger {
        @Override
        public Mono<Void> logRequest(ClientLogger logger, HttpRequestLoggingContext loggingOptions) {
            final LogLevel logLevel = getLogLevel(loggingOptions);
            if (logger.canLogAtLevel(logLevel)) {
                log(logLevel, logger, loggingOptions);
            }

            return Mono.empty();
        }

        private void log(LogLevel logLevel, ClientLogger logger, HttpRequestLoggingContext loggingOptions) {
            final HttpRequest request = loggingOptions.getHttpRequest();
            LoggingEventBuilder logBuilder = getLogBuilder(logLevel, logger);

            if (httpLogDetailLevel.shouldLogUrl()) {
                logBuilder
                    .addKeyValue(LoggingKeys.HTTP_METHOD_KEY, request.getHttpMethod())
                    .addKeyValue(LoggingKeys.URL_KEY, getRedactedUrl(request.getUrl(), allowedQueryParameterNames));

                Integer retryCount = loggingOptions.getTryCount();
                if (retryCount != null) {
                    logBuilder.addKeyValue(LoggingKeys.TRY_COUNT_KEY, retryCount);
                }
            }

            if (httpLogDetailLevel.shouldLogHeaders() && logger.canLogAtLevel(LogLevel.VERBOSE)) {
                addHeadersToLogMessage(allowedHeaderNames, request.getHeaders(), logBuilder);
            }

            if (request.getBody() == null) {
                logBuilder.addKeyValue(LoggingKeys.CONTENT_LENGTH_KEY, 0)
                    .log(REQUEST_LOG_MESSAGE);
                return;
            }

            String contentType = request.getHeaders().getValue("Content-Type");
            long contentLength = getContentLength(logger, request.getHeaders());

            logBuilder.addKeyValue(LoggingKeys.CONTENT_LENGTH_KEY, contentLength);

            if (httpLogDetailLevel.shouldLogBody() && shouldBodyBeLogged(contentType, contentLength)) {
                AccessibleByteArrayOutputStream stream = new AccessibleByteArrayOutputStream((int) contentLength);

                // Add non-mutating operators to the data stream.
                request.setBody(
                    request.getBody()
                        .doOnNext(byteBuffer -> {
                            try {
                                ImplUtils.writeByteBufferToStream(byteBuffer.duplicate(), stream);
                            } catch (IOException ex) {
                                throw LOGGER.logExceptionAsError(new UncheckedIOException(ex));
                            }
                        })
                        .doFinally(ignored -> {
                            logBuilder.addKeyValue(LoggingKeys.BODY_KEY, prettyPrintIfNeeded(logger, prettyPrintBody, contentType,
                                    new String(stream.toByteArray(), 0, stream.count(), StandardCharsets.UTF_8)))
                                .log(REQUEST_LOG_MESSAGE);

                        }));
                return;
            }

            logBuilder.log(REQUEST_LOG_MESSAGE);
        }
    }

    private final class DefaultHttpResponseLogger implements HttpResponseLogger {
        @Override
        public Mono<HttpResponse> logResponse(ClientLogger logger, HttpResponseLoggingContext loggingOptions) {
            final LogLevel logLevel = getLogLevel(loggingOptions);
            final HttpResponse response = loggingOptions.getHttpResponse();

            if (!logger.canLogAtLevel(logLevel)) {
                return Mono.just(response);
            }

            LoggingEventBuilder logBuilder = getLogBuilder(logLevel, logger);
            String contentLengthString = response.getHeaderValue("Content-Length");
            if (!CoreUtils.isNullOrEmpty(contentLengthString)) {
                logBuilder.addKeyValue(LoggingKeys.CONTENT_LENGTH_KEY, contentLengthString);
            }

            if (httpLogDetailLevel.shouldLogUrl()) {
                logBuilder
                    .addKeyValue(LoggingKeys.STATUS_CODE_KEY, response.getStatusCode())
                    .addKeyValue(LoggingKeys.URL_KEY, getRedactedUrl(response.getRequest().getUrl(), allowedQueryParameterNames))
                    .addKeyValue(LoggingKeys.DURATION_MS_KEY, loggingOptions.getResponseDuration().toMillis());
            }

            if (httpLogDetailLevel.shouldLogHeaders() && logger.canLogAtLevel(LogLevel.VERBOSE)) {
                addHeadersToLogMessage(allowedHeaderNames, response.getHeaders(), logBuilder);
            }

            if (httpLogDetailLevel.shouldLogBody()) {
                String contentTypeHeader = response.getHeaderValue("Content-Type");
                long contentLength = getContentLength(logger, response.getHeaders());
                if (shouldBodyBeLogged(contentTypeHeader, contentLength)) {
                    return Mono.just(new LoggingHttpResponse(response, logBuilder, logger,
                        (int) contentLength, contentTypeHeader, prettyPrintBody));
                }
            }

            logBuilder.log(RESPONSE_LOG_MESSAGE);
            return Mono.just(response);
        }
    }

    /*
     * Generates the redacted URL for logging.
     *
     * @param url URL where the request is being sent.
     * @return A URL with query parameters redacted based on configurations in this policy.
     */
    private static String getRedactedUrl(URL url, Set<String> allowedQueryParameterNames) {
        UrlBuilder builder = UrlBuilder.parse(url);
        String allowedQueryString = getAllowedQueryString(url.getQuery(), allowedQueryParameterNames);

        // return a UrlBuilder with a new query.
        // builder.clearQuery() is required here to explicitly clear
        // UrlBuilder.query field - simply calling setQuery() will not do it.
        // setQuery() could have full-replacement semantics, but some SDKs are
        // using it as an appending mechanism.
        return builder
                .clearQuery()
                .setQuery(allowedQueryString)
                .toString();
    }

    /*
     * Generates the logging safe query parameters string.
     *
     * @param queryString Query parameter string from the request URL.
     * @return A query parameter string redacted based on the configurations in this policy.
     */
    private static String getAllowedQueryString(String queryString, Set<String> allowedQueryParameterNames) {
        if (CoreUtils.isNullOrEmpty(queryString)) {
            return "";
        }

        StringBuilder queryStringBuilder = new StringBuilder();
        String[] queryParams = queryString.split("&");
        for (String queryParam : queryParams) {
            if (queryStringBuilder.length() > 0) {
                queryStringBuilder.append("&");
            }

            String[] queryPair = queryParam.split("=", 2);
            if (queryPair.length == 2) {
                String queryName = queryPair[0];
                if (allowedQueryParameterNames.contains(queryName.toLowerCase(Locale.ROOT))) {
                    queryStringBuilder.append(queryParam);
                } else {
                    queryStringBuilder.append(queryPair[0]).append("=").append(REDACTED_PLACEHOLDER);
                }
            } else {
                queryStringBuilder.append(queryParam);
            }
        }

        return queryStringBuilder.toString();
    }

    /*
     * Adds HTTP headers into the StringBuilder that is generating the log message.
     *
     * @param headers HTTP headers on the request or response.
     * @param sb StringBuilder that is generating the log message.
     * @param logLevel Log level the environment is configured to use.
     */
    private static void addHeadersToLogMessage(Set<String> allowedHeaderNames, HttpHeaders headers, LoggingEventBuilder logBuilder) {
        for (HttpHeader header : headers) {
            String headerName = header.getName();
            logBuilder.addKeyValue(headerName, allowedHeaderNames.contains(headerName.toLowerCase(Locale.ROOT)) ? header.getValue() : REDACTED_PLACEHOLDER);
        }
    }

    /*
     * Determines and attempts to pretty print the body if it is JSON.
     *
     * <p>The body is pretty printed if the Content-Type is JSON and the policy is configured to pretty print JSON.</p>
     *
     * @param logger Logger used to log a warning if the body fails to pretty print as JSON.
     * @param contentType Content-Type header.
     * @param body Body of the request or response.
     * @return The body pretty printed if it is JSON, otherwise the unmodified body.
     */
    private static String prettyPrintIfNeeded(ClientLogger logger, boolean prettyPrintBody, String contentType,
        String body) {
        String result = body;
        if (prettyPrintBody && contentType != null
            && (contentType.startsWith(ContentType.APPLICATION_JSON) || contentType.startsWith("text/json"))) {
            try {
                final Object deserialized = PRETTY_PRINTER.readTree(body);
                result = PRETTY_PRINTER.writeValueAsString(deserialized);
            } catch (Exception e) {
                logger.warning("Failed to pretty print JSON", e);
            }
        }
        return result;
    }

    /*
     * Attempts to retrieve and parse the Content-Length header into a numeric representation.
     *
     * @param logger Logger used to log a warning if the Content-Length header is an invalid number.
     * @param headers HTTP headers that are checked for containing Content-Length.
     * @return
     */
    private static long getContentLength(ClientLogger logger, HttpHeaders headers) {
        long contentLength = 0;

        String contentLengthString = headers.getValue("Content-Length");
        if (CoreUtils.isNullOrEmpty(contentLengthString)) {
            return contentLength;
        }

        try {
            contentLength = Long.parseLong(contentLengthString);
        } catch (NumberFormatException | NullPointerException e) {
            logger.warning("Could not parse the HTTP header content-length: '{}'.",
                headers.getValue("content-length"), e);
        }

        return contentLength;
    }

    /*
     * Determines if the request or response body should be logged.
     *
     * <p>The request or response body is logged if the Content-Type is not "application/octet-stream" and the body
     * isn't empty and is less than 16KB in size.</p>
     *
     * @param contentTypeHeader Content-Type header value.
     * @param contentLength Content-Length header represented as a numeric.
     * @return A flag indicating if the request or response body should be logged.
     */
    private static boolean shouldBodyBeLogged(String contentTypeHeader, long contentLength) {
        return !ContentType.APPLICATION_OCTET_STREAM.equalsIgnoreCase(contentTypeHeader)
            && contentLength != 0
            && contentLength < MAX_BODY_LOG_SIZE;
    }

    /*
     * Gets the request retry count to include in logging.
     *
     * If there is no value set or it isn't a valid number null will be returned indicating that retry count won't be
     * logged.
     */
    private static Integer getRequestRetryCount(Context context) {
        Object rawRetryCount = context.getData(RETRY_COUNT_CONTEXT).orElse(null);
        if (rawRetryCount == null) {
            return null;
        }

        try {
            return Integer.valueOf(rawRetryCount.toString());
        } catch (NumberFormatException ex) {
            LOGGER.warning("Could not parse the request retry count: '{}'.", rawRetryCount);
            return null;
        }
    }

    /*
     * Get or create the ClientLogger for the method having its request and response logged.
     */
    private static ClientLogger getOrCreateMethodLogger(String methodName) {
        if (CALLER_METHOD_LOGGER_CACHE.size() > LOGGER_CACHE_MAX_SIZE) {
            CALLER_METHOD_LOGGER_CACHE.clear();
        }

        return CALLER_METHOD_LOGGER_CACHE.computeIfAbsent(methodName, ClientLogger::new);
    }

    private static LoggingEventBuilder getLogBuilder(LogLevel logLevel, ClientLogger logger) {
        switch (logLevel) {
            case ERROR:
                return logger.atError();
            case WARNING:
                return logger.atWarning();
            case INFORMATIONAL:
                return logger.atInfo();
            case VERBOSE:
            default:
                return logger.atVerbose();
        }
    }

    private static final class LoggingHttpResponse extends HttpResponse {
        private final HttpResponse actualResponse;
        private final LoggingEventBuilder logBuilder;
        private final int contentLength;
        private final ClientLogger logger;
        private final boolean prettyPrintBody;
        private final String contentTypeHeader;

        private LoggingHttpResponse(HttpResponse actualResponse, LoggingEventBuilder logBuilder,
            ClientLogger logger, int contentLength, String contentTypeHeader,
            boolean prettyPrintBody) {
            super(actualResponse.getRequest());
            this.actualResponse = actualResponse;
            this.logBuilder = logBuilder;
            this.logger = logger;
            this.contentLength = contentLength;
            this.contentTypeHeader = contentTypeHeader;
            this.prettyPrintBody = prettyPrintBody;
        }

        @Override
        public int getStatusCode() {
            return actualResponse.getStatusCode();
        }

        @Override
        public String getHeaderValue(String name) {
            return actualResponse.getHeaderValue(name);
        }

        @Override
        public HttpHeaders getHeaders() {
            return actualResponse.getHeaders();
        }

        @Override
        public Flux<ByteBuffer> getBody() {
            AccessibleByteArrayOutputStream stream = new AccessibleByteArrayOutputStream(contentLength);

            return actualResponse.getBody()
                .doOnNext(byteBuffer -> {
                    try {
                        ImplUtils.writeByteBufferToStream(byteBuffer.duplicate(), stream);
                    } catch (IOException ex) {
                        throw LOGGER.logExceptionAsError(new UncheckedIOException(ex));
                    }
                })
                .doFinally(ignored -> {
                    logBuilder.addKeyValue(LoggingKeys.BODY_KEY, prettyPrintIfNeeded(logger, prettyPrintBody, contentTypeHeader,
                            new String(stream.toByteArray(), 0, stream.count(), StandardCharsets.UTF_8)))
                        .log(RESPONSE_LOG_MESSAGE);
                });
        }

        @Override
        public Mono<byte[]> getBodyAsByteArray() {
            return FluxUtil.collectBytesFromNetworkResponse(getBody(), actualResponse.getHeaders());
        }

        @Override
        public Mono<String> getBodyAsString() {
            return getBodyAsByteArray().map(String::new);
        }

        @Override
        public Mono<String> getBodyAsString(Charset charset) {
            return getBodyAsByteArray().map(bytes -> new String(bytes, charset));
        }
    }
}
