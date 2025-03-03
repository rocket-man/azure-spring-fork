// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.mobilenetwork.generated;

import com.azure.core.util.Context;

/** Samples for PacketCoreDataPlanes ListByPacketCoreControlPlane. */
public final class PacketCoreDataPlanesListByPacketCoreControlPlaneSamples {
    /*
     * x-ms-original-file: specification/mobilenetwork/resource-manager/Microsoft.MobileNetwork/preview/2022-03-01-preview/examples/PacketCoreDataPlaneListByPacketCoreControlPlane.json
     */
    /**
     * Sample code: List packet core data planes in a control plane.
     *
     * @param manager Entry point to MobileNetworkManager.
     */
    public static void listPacketCoreDataPlanesInAControlPlane(
        com.azure.resourcemanager.mobilenetwork.MobileNetworkManager manager) {
        manager.packetCoreDataPlanes().listByPacketCoreControlPlane("rg1", "testPacketCoreCP", Context.NONE);
    }
}
