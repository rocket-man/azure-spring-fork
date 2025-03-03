// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.analytics.synapse.artifacts.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.JsonFlatten;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import java.util.Map;

/** DatabricksSparkJar activity. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("DatabricksSparkJar")
@JsonFlatten
@Fluent
public class DatabricksSparkJarActivity extends ExecutionActivity {
    /*
     * The full name of the class containing the main method to be executed.
     * This class must be contained in a JAR provided as a library. Type:
     * string (or Expression with resultType string).
     */
    @JsonProperty(value = "typeProperties.mainClassName", required = true)
    private Object mainClassName;

    /*
     * Parameters that will be passed to the main method.
     */
    @JsonProperty(value = "typeProperties.parameters")
    private List<Object> parameters;

    /*
     * A list of libraries to be installed on the cluster that will execute the
     * job.
     */
    @JsonProperty(value = "typeProperties.libraries")
    private List<Map<String, Object>> libraries;

    /**
     * Get the mainClassName property: The full name of the class containing the main method to be executed. This class
     * must be contained in a JAR provided as a library. Type: string (or Expression with resultType string).
     *
     * @return the mainClassName value.
     */
    public Object getMainClassName() {
        return this.mainClassName;
    }

    /**
     * Set the mainClassName property: The full name of the class containing the main method to be executed. This class
     * must be contained in a JAR provided as a library. Type: string (or Expression with resultType string).
     *
     * @param mainClassName the mainClassName value to set.
     * @return the DatabricksSparkJarActivity object itself.
     */
    public DatabricksSparkJarActivity setMainClassName(Object mainClassName) {
        this.mainClassName = mainClassName;
        return this;
    }

    /**
     * Get the parameters property: Parameters that will be passed to the main method.
     *
     * @return the parameters value.
     */
    public List<Object> getParameters() {
        return this.parameters;
    }

    /**
     * Set the parameters property: Parameters that will be passed to the main method.
     *
     * @param parameters the parameters value to set.
     * @return the DatabricksSparkJarActivity object itself.
     */
    public DatabricksSparkJarActivity setParameters(List<Object> parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * Get the libraries property: A list of libraries to be installed on the cluster that will execute the job.
     *
     * @return the libraries value.
     */
    public List<Map<String, Object>> getLibraries() {
        return this.libraries;
    }

    /**
     * Set the libraries property: A list of libraries to be installed on the cluster that will execute the job.
     *
     * @param libraries the libraries value to set.
     * @return the DatabricksSparkJarActivity object itself.
     */
    public DatabricksSparkJarActivity setLibraries(List<Map<String, Object>> libraries) {
        this.libraries = libraries;
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DatabricksSparkJarActivity setLinkedServiceName(LinkedServiceReference linkedServiceName) {
        super.setLinkedServiceName(linkedServiceName);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DatabricksSparkJarActivity setPolicy(ActivityPolicy policy) {
        super.setPolicy(policy);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DatabricksSparkJarActivity setName(String name) {
        super.setName(name);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DatabricksSparkJarActivity setDescription(String description) {
        super.setDescription(description);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DatabricksSparkJarActivity setDependsOn(List<ActivityDependency> dependsOn) {
        super.setDependsOn(dependsOn);
        return this;
    }

    /** {@inheritDoc} */
    @Override
    public DatabricksSparkJarActivity setUserProperties(List<UserProperty> userProperties) {
        super.setUserProperties(userProperties);
        return this;
    }
}
