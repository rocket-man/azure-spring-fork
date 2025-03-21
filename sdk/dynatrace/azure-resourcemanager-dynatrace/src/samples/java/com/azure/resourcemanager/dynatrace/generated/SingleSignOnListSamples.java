// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.dynatrace.generated;

import com.azure.core.util.Context;

/** Samples for SingleSignOn List. */
public final class SingleSignOnListSamples {
    /*
     * x-ms-original-file: specification/dynatrace/resource-manager/Dynatrace.Observability/preview/2021-09-01-preview/examples/SingleSignOn_List_MaximumSet_Gen.json
     */
    /**
     * Sample code: SingleSignOn_List_MaximumSet_Gen.
     *
     * @param manager Entry point to DynatraceManager.
     */
    public static void singleSignOnListMaximumSetGen(com.azure.resourcemanager.dynatrace.DynatraceManager manager) {
        manager.singleSignOns().list("myResourceGroup", "myMonitor", Context.NONE);
    }

    /*
     * x-ms-original-file: specification/dynatrace/resource-manager/Dynatrace.Observability/preview/2021-09-01-preview/examples/SingleSignOn_List_MinimumSet_Gen.json
     */
    /**
     * Sample code: SingleSignOn_List_MinimumSet_Gen.
     *
     * @param manager Entry point to DynatraceManager.
     */
    public static void singleSignOnListMinimumSetGen(com.azure.resourcemanager.dynatrace.DynatraceManager manager) {
        manager.singleSignOns().list("myResourceGroup", "myMonitor", Context.NONE);
    }
}
