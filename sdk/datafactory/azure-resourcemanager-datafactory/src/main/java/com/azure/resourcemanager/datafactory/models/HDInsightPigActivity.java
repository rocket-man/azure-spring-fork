// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.datafactory.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.datafactory.fluent.models.HDInsightPigActivityTypeProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import java.util.Map;

/** HDInsight Pig activity type. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("HDInsightPig")
@Fluent
public final class HDInsightPigActivity extends ExecutionActivity {
    /*
     * HDInsight Pig activity properties.
     */
    @JsonProperty(value = "typeProperties", required = true)
    private HDInsightPigActivityTypeProperties innerTypeProperties = new HDInsightPigActivityTypeProperties();

    /**
     * Get the innerTypeProperties property: HDInsight Pig activity properties.
     *
     * @return the innerTypeProperties value.
     */
    private HDInsightPigActivityTypeProperties innerTypeProperties() {
        return this.innerTypeProperties;
    }

    /** {@inheritDoc} */
    @Override
    public HDInsightPigActivity withLinkedServiceName(LinkedServiceReference linkedServiceName) {
        super.withLinkedServiceName(linkedServiceName);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public HDInsightPigActivity withPolicy(ActivityPolicy policy) {
        super.withPolicy(policy);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public HDInsightPigActivity withName(String name) {
        super.withName(name);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public HDInsightPigActivity withDescription(String description) {
        super.withDescription(description);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public HDInsightPigActivity withDependsOn(List<ActivityDependency> dependsOn) {
        super.withDependsOn(dependsOn);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public HDInsightPigActivity withUserProperties(List<UserProperty> userProperties) {
        super.withUserProperties(userProperties);
        return this;
    }

    /**
     * Get the storageLinkedServices property: Storage linked service references.
     *
     * @return the storageLinkedServices value.
     */
    public List<LinkedServiceReference> storageLinkedServices() {
        return this.innerTypeProperties() == null ? null : this.innerTypeProperties().storageLinkedServices();
    }

    /**
     * Set the storageLinkedServices property: Storage linked service references.
     *
     * @param storageLinkedServices the storageLinkedServices value to set.
     * @return the HDInsightPigActivity object itself.
     */
    public HDInsightPigActivity withStorageLinkedServices(List<LinkedServiceReference> storageLinkedServices) {
        if (this.innerTypeProperties() == null) {
            this.innerTypeProperties = new HDInsightPigActivityTypeProperties();
        }
        this.innerTypeProperties().withStorageLinkedServices(storageLinkedServices);
        return this;
    }

    /**
     * Get the arguments property: User specified arguments to HDInsightActivity. Type: array (or Expression with
     * resultType array).
     *
     * @return the arguments value.
     */
    public Object arguments() {
        return this.innerTypeProperties() == null ? null : this.innerTypeProperties().arguments();
    }

    /**
     * Set the arguments property: User specified arguments to HDInsightActivity. Type: array (or Expression with
     * resultType array).
     *
     * @param arguments the arguments value to set.
     * @return the HDInsightPigActivity object itself.
     */
    public HDInsightPigActivity withArguments(Object arguments) {
        if (this.innerTypeProperties() == null) {
            this.innerTypeProperties = new HDInsightPigActivityTypeProperties();
        }
        this.innerTypeProperties().withArguments(arguments);
        return this;
    }

    /**
     * Get the getDebugInfo property: Debug info option.
     *
     * @return the getDebugInfo value.
     */
    public HDInsightActivityDebugInfoOption getDebugInfo() {
        return this.innerTypeProperties() == null ? null : this.innerTypeProperties().getDebugInfo();
    }

    /**
     * Set the getDebugInfo property: Debug info option.
     *
     * @param getDebugInfo the getDebugInfo value to set.
     * @return the HDInsightPigActivity object itself.
     */
    public HDInsightPigActivity withGetDebugInfo(HDInsightActivityDebugInfoOption getDebugInfo) {
        if (this.innerTypeProperties() == null) {
            this.innerTypeProperties = new HDInsightPigActivityTypeProperties();
        }
        this.innerTypeProperties().withGetDebugInfo(getDebugInfo);
        return this;
    }

    /**
     * Get the scriptPath property: Script path. Type: string (or Expression with resultType string).
     *
     * @return the scriptPath value.
     */
    public Object scriptPath() {
        return this.innerTypeProperties() == null ? null : this.innerTypeProperties().scriptPath();
    }

    /**
     * Set the scriptPath property: Script path. Type: string (or Expression with resultType string).
     *
     * @param scriptPath the scriptPath value to set.
     * @return the HDInsightPigActivity object itself.
     */
    public HDInsightPigActivity withScriptPath(Object scriptPath) {
        if (this.innerTypeProperties() == null) {
            this.innerTypeProperties = new HDInsightPigActivityTypeProperties();
        }
        this.innerTypeProperties().withScriptPath(scriptPath);
        return this;
    }

    /**
     * Get the scriptLinkedService property: Script linked service reference.
     *
     * @return the scriptLinkedService value.
     */
    public LinkedServiceReference scriptLinkedService() {
        return this.innerTypeProperties() == null ? null : this.innerTypeProperties().scriptLinkedService();
    }

    /**
     * Set the scriptLinkedService property: Script linked service reference.
     *
     * @param scriptLinkedService the scriptLinkedService value to set.
     * @return the HDInsightPigActivity object itself.
     */
    public HDInsightPigActivity withScriptLinkedService(LinkedServiceReference scriptLinkedService) {
        if (this.innerTypeProperties() == null) {
            this.innerTypeProperties = new HDInsightPigActivityTypeProperties();
        }
        this.innerTypeProperties().withScriptLinkedService(scriptLinkedService);
        return this;
    }

    /**
     * Get the defines property: Allows user to specify defines for Pig job request.
     *
     * @return the defines value.
     */
    public Map<String, Object> defines() {
        return this.innerTypeProperties() == null ? null : this.innerTypeProperties().defines();
    }

    /**
     * Set the defines property: Allows user to specify defines for Pig job request.
     *
     * @param defines the defines value to set.
     * @return the HDInsightPigActivity object itself.
     */
    public HDInsightPigActivity withDefines(Map<String, Object> defines) {
        if (this.innerTypeProperties() == null) {
            this.innerTypeProperties = new HDInsightPigActivityTypeProperties();
        }
        this.innerTypeProperties().withDefines(defines);
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
        if (innerTypeProperties() == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        "Missing required property innerTypeProperties in model HDInsightPigActivity"));
        } else {
            innerTypeProperties().validate();
        }
    }

    private static final ClientLogger LOGGER = new ClientLogger(HDInsightPigActivity.class);
}
