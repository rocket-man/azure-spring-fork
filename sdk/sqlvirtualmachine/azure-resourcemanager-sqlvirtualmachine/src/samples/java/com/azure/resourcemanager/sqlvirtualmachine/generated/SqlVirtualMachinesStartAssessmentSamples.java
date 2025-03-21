// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.sqlvirtualmachine.generated;

import com.azure.core.util.Context;

/** Samples for SqlVirtualMachines StartAssessment. */
public final class SqlVirtualMachinesStartAssessmentSamples {
    /*
     * x-ms-original-file: specification/sqlvirtualmachine/resource-manager/Microsoft.SqlVirtualMachine/preview/2021-11-01-preview/examples/StartAssessmentOnSqlVirtualMachine.json
     */
    /**
     * Sample code: Starts Assessment on SQL virtual machine.
     *
     * @param manager Entry point to SqlVirtualMachineManager.
     */
    public static void startsAssessmentOnSQLVirtualMachine(
        com.azure.resourcemanager.sqlvirtualmachine.SqlVirtualMachineManager manager) {
        manager.sqlVirtualMachines().startAssessment("testrg", "testvm", Context.NONE);
    }
}
