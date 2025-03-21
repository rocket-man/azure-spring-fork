// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.compute.generated;

import com.azure.core.util.Context;
import com.azure.resourcemanager.compute.fluent.models.DiskAccessInner;

/** Samples for DiskAccesses CreateOrUpdate. */
public final class DiskAccessesCreateOrUpdateSamples {
    /*
     * x-ms-original-file: specification/compute/resource-manager/Microsoft.Compute/stable/2022-03-02/DiskRP/examples/diskAccessExamples/DiskAccess_Create.json
     */
    /**
     * Sample code: Create a disk access resource.
     *
     * @param azure The entry point for accessing resource management APIs in Azure.
     */
    public static void createADiskAccessResource(com.azure.resourcemanager.AzureResourceManager azure) {
        azure
            .virtualMachines()
            .manager()
            .serviceClient()
            .getDiskAccesses()
            .createOrUpdate(
                "myResourceGroup", "myDiskAccess", new DiskAccessInner().withLocation("West US"), Context.NONE);
    }
}
