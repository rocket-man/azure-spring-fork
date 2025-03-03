// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.machinelearning.models;

import com.azure.core.annotation.Fluent;
import com.azure.resourcemanager.machinelearning.fluent.models.ClusterUpdateProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** AmlCompute update parameters. */
@Fluent
public final class ClusterUpdateParameters {
    /*
     * The properties of the amlCompute.
     */
    @JsonProperty(value = "properties")
    private ClusterUpdateProperties innerProperties;

    /**
     * Get the innerProperties property: The properties of the amlCompute.
     *
     * @return the innerProperties value.
     */
    private ClusterUpdateProperties innerProperties() {
        return this.innerProperties;
    }

    /**
     * Get the properties property: Properties of ClusterUpdate.
     *
     * @return the properties value.
     */
    public ScaleSettingsInformation properties() {
        return this.innerProperties() == null ? null : this.innerProperties().properties();
    }

    /**
     * Set the properties property: Properties of ClusterUpdate.
     *
     * @param properties the properties value to set.
     * @return the ClusterUpdateParameters object itself.
     */
    public ClusterUpdateParameters withProperties(ScaleSettingsInformation properties) {
        if (this.innerProperties() == null) {
            this.innerProperties = new ClusterUpdateProperties();
        }
        this.innerProperties().withProperties(properties);
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (innerProperties() != null) {
            innerProperties().validate();
        }
    }
}
