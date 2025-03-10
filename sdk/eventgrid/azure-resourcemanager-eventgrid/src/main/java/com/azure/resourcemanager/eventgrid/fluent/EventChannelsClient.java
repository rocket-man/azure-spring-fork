// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.eventgrid.fluent;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.management.polling.PollResult;
import com.azure.core.util.Context;
import com.azure.core.util.polling.SyncPoller;
import com.azure.resourcemanager.eventgrid.fluent.models.EventChannelInner;

/** An instance of this class provides access to all the operations defined in EventChannelsClient. */
public interface EventChannelsClient {
    /**
     * Get properties of an event channel.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @param eventChannelName Name of the event channel.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return properties of an event channel.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    EventChannelInner get(String resourceGroupName, String partnerNamespaceName, String eventChannelName);

    /**
     * Get properties of an event channel.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @param eventChannelName Name of the event channel.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return properties of an event channel along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<EventChannelInner> getWithResponse(
        String resourceGroupName, String partnerNamespaceName, String eventChannelName, Context context);

    /**
     * Asynchronously creates a new event channel with the specified parameters.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @param eventChannelName Name of the event channel.
     * @param eventChannelInfo EventChannel information.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return event Channel.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    EventChannelInner createOrUpdate(
        String resourceGroupName,
        String partnerNamespaceName,
        String eventChannelName,
        EventChannelInner eventChannelInfo);

    /**
     * Asynchronously creates a new event channel with the specified parameters.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @param eventChannelName Name of the event channel.
     * @param eventChannelInfo EventChannel information.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return event Channel along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<EventChannelInner> createOrUpdateWithResponse(
        String resourceGroupName,
        String partnerNamespaceName,
        String eventChannelName,
        EventChannelInner eventChannelInfo,
        Context context);

    /**
     * Delete existing event channel.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @param eventChannelName Name of the event channel.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link SyncPoller} for polling of long-running operation.
     */
    @ServiceMethod(returns = ReturnType.LONG_RUNNING_OPERATION)
    SyncPoller<PollResult<Void>, Void> beginDelete(
        String resourceGroupName, String partnerNamespaceName, String eventChannelName);

    /**
     * Delete existing event channel.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @param eventChannelName Name of the event channel.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link SyncPoller} for polling of long-running operation.
     */
    @ServiceMethod(returns = ReturnType.LONG_RUNNING_OPERATION)
    SyncPoller<PollResult<Void>, Void> beginDelete(
        String resourceGroupName, String partnerNamespaceName, String eventChannelName, Context context);

    /**
     * Delete existing event channel.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @param eventChannelName Name of the event channel.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void delete(String resourceGroupName, String partnerNamespaceName, String eventChannelName);

    /**
     * Delete existing event channel.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @param eventChannelName Name of the event channel.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void delete(String resourceGroupName, String partnerNamespaceName, String eventChannelName, Context context);

    /**
     * List all the event channels in a partner namespace.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return result of the List Event Channels operation as paginated response with {@link PagedIterable}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<EventChannelInner> listByPartnerNamespace(String resourceGroupName, String partnerNamespaceName);

    /**
     * List all the event channels in a partner namespace.
     *
     * @param resourceGroupName The name of the resource group within the user's subscription.
     * @param partnerNamespaceName Name of the partner namespace.
     * @param filter The query used to filter the search results using OData syntax. Filtering is permitted on the
     *     'name' property only and with limited number of OData operations. These operations are: the 'contains'
     *     function as well as the following logical operations: not, and, or, eq (for equal), and ne (for not equal).
     *     No arithmetic operations are supported. The following is a valid filter example: $filter=contains(namE,
     *     'PATTERN') and name ne 'PATTERN-1'. The following is not a valid filter example: $filter=location eq
     *     'westus'.
     * @param top The number of results to return per page for the list operation. Valid range for top parameter is 1 to
     *     100. If not specified, the default number of results to be returned is 20 items per page.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return result of the List Event Channels operation as paginated response with {@link PagedIterable}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<EventChannelInner> listByPartnerNamespace(
        String resourceGroupName, String partnerNamespaceName, String filter, Integer top, Context context);
}
