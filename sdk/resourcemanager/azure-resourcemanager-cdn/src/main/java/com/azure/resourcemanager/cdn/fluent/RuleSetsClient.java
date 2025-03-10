// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.cdn.fluent;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.management.polling.PollResult;
import com.azure.core.util.Context;
import com.azure.core.util.polling.PollerFlux;
import com.azure.core.util.polling.SyncPoller;
import com.azure.resourcemanager.cdn.fluent.models.RuleSetInner;
import com.azure.resourcemanager.cdn.fluent.models.UsageInner;
import java.nio.ByteBuffer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/** An instance of this class provides access to all the operations defined in RuleSetsClient. */
public interface RuleSetsClient {
    /**
     * Lists existing AzureFrontDoor rule sets within a profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return result of the request to list rule sets as paginated response with {@link PagedFlux}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedFlux<RuleSetInner> listByProfileAsync(String resourceGroupName, String profileName);

    /**
     * Lists existing AzureFrontDoor rule sets within a profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return result of the request to list rule sets as paginated response with {@link PagedIterable}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<RuleSetInner> listByProfile(String resourceGroupName, String profileName);

    /**
     * Lists existing AzureFrontDoor rule sets within a profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return result of the request to list rule sets as paginated response with {@link PagedIterable}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<RuleSetInner> listByProfile(String resourceGroupName, String profileName, Context context);

    /**
     * Gets an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     * resource group and profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     *     resource group and profile along with {@link Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<RuleSetInner>> getWithResponseAsync(String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Gets an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     * resource group and profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     *     resource group and profile on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<RuleSetInner> getAsync(String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Gets an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     * resource group and profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     *     resource group and profile.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    RuleSetInner get(String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Gets an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     * resource group and profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     *     resource group and profile along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<RuleSetInner> getWithResponse(
        String resourceGroupName, String profileName, String ruleSetName, Context context);

    /**
     * Creates a new rule set within the specified profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return friendly RuleSet name mapping to the any RuleSet or secret related information along with {@link
     *     Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<RuleSetInner>> createWithResponseAsync(
        String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Creates a new rule set within the specified profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return friendly RuleSet name mapping to the any RuleSet or secret related information on successful completion
     *     of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<RuleSetInner> createAsync(String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Creates a new rule set within the specified profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return friendly RuleSet name mapping to the any RuleSet or secret related information.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    RuleSetInner create(String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Creates a new rule set within the specified profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return friendly RuleSet name mapping to the any RuleSet or secret related information along with {@link
     *     Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<RuleSetInner> createWithResponse(
        String resourceGroupName, String profileName, String ruleSetName, Context context);

    /**
     * Deletes an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     * resource group and profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link Response} on successful completion of {@link Mono}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<Flux<ByteBuffer>>> deleteWithResponseAsync(
        String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Deletes an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     * resource group and profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link PollerFlux} for polling of long-running operation.
     */
    @ServiceMethod(returns = ReturnType.LONG_RUNNING_OPERATION)
    PollerFlux<PollResult<Void>, Void> beginDeleteAsync(
        String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Deletes an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     * resource group and profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link SyncPoller} for polling of long-running operation.
     */
    @ServiceMethod(returns = ReturnType.LONG_RUNNING_OPERATION)
    SyncPoller<PollResult<Void>, Void> beginDelete(String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Deletes an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     * resource group and profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link SyncPoller} for polling of long-running operation.
     */
    @ServiceMethod(returns = ReturnType.LONG_RUNNING_OPERATION)
    SyncPoller<PollResult<Void>, Void> beginDelete(
        String resourceGroupName, String profileName, String ruleSetName, Context context);

    /**
     * Deletes an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     * resource group and profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return A {@link Mono} that completes when a successful response is received.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Void> deleteAsync(String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Deletes an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     * resource group and profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void delete(String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Deletes an existing AzureFrontDoor rule set with the specified rule set name under the specified subscription,
     * resource group and profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void delete(String resourceGroupName, String profileName, String ruleSetName, Context context);

    /**
     * Checks the quota and actual usage of endpoints under the given CDN profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the list usages operation response as paginated response with {@link PagedFlux}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedFlux<UsageInner> listResourceUsageAsync(String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Checks the quota and actual usage of endpoints under the given CDN profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the list usages operation response as paginated response with {@link PagedIterable}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<UsageInner> listResourceUsage(String resourceGroupName, String profileName, String ruleSetName);

    /**
     * Checks the quota and actual usage of endpoints under the given CDN profile.
     *
     * @param resourceGroupName Name of the Resource group within the Azure subscription.
     * @param profileName Name of the Azure Front Door Standard or Azure Front Door Premium profile which is unique
     *     within the resource group.
     * @param ruleSetName Name of the rule set under the profile which is unique globally.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the list usages operation response as paginated response with {@link PagedIterable}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<UsageInner> listResourceUsage(
        String resourceGroupName, String profileName, String ruleSetName, Context context);
}
