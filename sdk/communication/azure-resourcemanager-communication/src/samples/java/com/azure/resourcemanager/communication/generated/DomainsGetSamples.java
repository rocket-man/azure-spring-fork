// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.communication.generated;

import com.azure.core.util.Context;

/** Samples for Domains Get. */
public final class DomainsGetSamples {
    /*
     * x-ms-original-file: specification/communication/resource-manager/Microsoft.Communication/preview/2021-10-01-preview/examples/domains/get.json
     */
    /**
     * Sample code: Get Domains resource.
     *
     * @param manager Entry point to CommunicationManager.
     */
    public static void getDomainsResource(com.azure.resourcemanager.communication.CommunicationManager manager) {
        manager.domains().getWithResponse("MyResourceGroup", "MyEmailServiceResource", "mydomain.com", Context.NONE);
    }
}
