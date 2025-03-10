// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.iothub.generated;

import com.azure.core.util.Context;

/** Samples for IotHubResource GetKeysForKeyName. */
public final class IotHubResourceGetKeysForKeyNameSamples {
    /*
     * x-ms-original-file: specification/iothub/resource-manager/Microsoft.Devices/stable/2021-07-02/examples/iothub_getkey.json
     */
    /**
     * Sample code: IotHubResource_GetKeysForKeyName.
     *
     * @param manager Entry point to IotHubManager.
     */
    public static void iotHubResourceGetKeysForKeyName(com.azure.resourcemanager.iothub.IotHubManager manager) {
        manager
            .iotHubResources()
            .getKeysForKeyNameWithResponse("myResourceGroup", "testHub", "iothubowner", Context.NONE);
    }
}
