// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.storage.file.share.implementation.models;

import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.rest.ResponseBase;

/** Contains all response data for the getPermission operation. */
public final class SharesGetPermissionResponse extends ResponseBase<SharesGetPermissionHeaders, SharePermission> {
    /**
     * Creates an instance of SharesGetPermissionResponse.
     *
     * @param request the request which resulted in this SharesGetPermissionResponse.
     * @param statusCode the status code of the HTTP response.
     * @param rawHeaders the raw headers of the HTTP response.
     * @param value the deserialized value of the HTTP response.
     * @param headers the deserialized headers of the HTTP response.
     */
    public SharesGetPermissionResponse(
            HttpRequest request,
            int statusCode,
            HttpHeaders rawHeaders,
            SharePermission value,
            SharesGetPermissionHeaders headers) {
        super(request, statusCode, rawHeaders, value, headers);
    }

    /**
     * Gets the deserialized response body.
     *
     * @return the deserialized response body.
     */
    @Override
    public SharePermission getValue() {
        return super.getValue();
    }
}
