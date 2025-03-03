// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.datafactory.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;

/** Base class for all execution activities. */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    defaultImpl = ExecutionActivity.class)
@JsonTypeName("Execution")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "Copy", value = CopyActivity.class),
    @JsonSubTypes.Type(name = "HDInsightHive", value = HDInsightHiveActivity.class),
    @JsonSubTypes.Type(name = "HDInsightPig", value = HDInsightPigActivity.class),
    @JsonSubTypes.Type(name = "HDInsightMapReduce", value = HDInsightMapReduceActivity.class),
    @JsonSubTypes.Type(name = "HDInsightStreaming", value = HDInsightStreamingActivity.class),
    @JsonSubTypes.Type(name = "HDInsightSpark", value = HDInsightSparkActivity.class),
    @JsonSubTypes.Type(name = "ExecuteSSISPackage", value = ExecuteSsisPackageActivity.class),
    @JsonSubTypes.Type(name = "Custom", value = CustomActivity.class),
    @JsonSubTypes.Type(name = "SqlServerStoredProcedure", value = SqlServerStoredProcedureActivity.class),
    @JsonSubTypes.Type(name = "Delete", value = DeleteActivity.class),
    @JsonSubTypes.Type(name = "AzureDataExplorerCommand", value = AzureDataExplorerCommandActivity.class),
    @JsonSubTypes.Type(name = "Lookup", value = LookupActivity.class),
    @JsonSubTypes.Type(name = "WebActivity", value = WebActivity.class),
    @JsonSubTypes.Type(name = "GetMetadata", value = GetMetadataActivity.class),
    @JsonSubTypes.Type(name = "AzureMLBatchExecution", value = AzureMLBatchExecutionActivity.class),
    @JsonSubTypes.Type(name = "AzureMLUpdateResource", value = AzureMLUpdateResourceActivity.class),
    @JsonSubTypes.Type(name = "AzureMLExecutePipeline", value = AzureMLExecutePipelineActivity.class),
    @JsonSubTypes.Type(name = "DataLakeAnalyticsU-SQL", value = DataLakeAnalyticsUsqlActivity.class),
    @JsonSubTypes.Type(name = "DatabricksNotebook", value = DatabricksNotebookActivity.class),
    @JsonSubTypes.Type(name = "DatabricksSparkJar", value = DatabricksSparkJarActivity.class),
    @JsonSubTypes.Type(name = "DatabricksSparkPython", value = DatabricksSparkPythonActivity.class),
    @JsonSubTypes.Type(name = "AzureFunctionActivity", value = AzureFunctionActivity.class),
    @JsonSubTypes.Type(name = "ExecuteDataFlow", value = ExecuteDataFlowActivity.class),
    @JsonSubTypes.Type(name = "Script", value = ScriptActivity.class)
})
@Fluent
public class ExecutionActivity extends Activity {
    /*
     * Linked service reference.
     */
    @JsonProperty(value = "linkedServiceName")
    private LinkedServiceReference linkedServiceName;

    /*
     * Activity policy.
     */
    @JsonProperty(value = "policy")
    private ActivityPolicy policy;

    /**
     * Get the linkedServiceName property: Linked service reference.
     *
     * @return the linkedServiceName value.
     */
    public LinkedServiceReference linkedServiceName() {
        return this.linkedServiceName;
    }

    /**
     * Set the linkedServiceName property: Linked service reference.
     *
     * @param linkedServiceName the linkedServiceName value to set.
     * @return the ExecutionActivity object itself.
     */
    public ExecutionActivity withLinkedServiceName(LinkedServiceReference linkedServiceName) {
        this.linkedServiceName = linkedServiceName;
        return this;
    }

    /**
     * Get the policy property: Activity policy.
     *
     * @return the policy value.
     */
    public ActivityPolicy policy() {
        return this.policy;
    }

    /**
     * Set the policy property: Activity policy.
     *
     * @param policy the policy value to set.
     * @return the ExecutionActivity object itself.
     */
    public ExecutionActivity withPolicy(ActivityPolicy policy) {
        this.policy = policy;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ExecutionActivity withName(String name) {
        super.withName(name);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ExecutionActivity withDescription(String description) {
        super.withDescription(description);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ExecutionActivity withDependsOn(List<ActivityDependency> dependsOn) {
        super.withDependsOn(dependsOn);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public ExecutionActivity withUserProperties(List<UserProperty> userProperties) {
        super.withUserProperties(userProperties);
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    @Override
    public void validate() {
        super.validate();
        if (linkedServiceName() != null) {
            linkedServiceName().validate();
        }
        if (policy() != null) {
            policy().validate();
        }
    }
}
