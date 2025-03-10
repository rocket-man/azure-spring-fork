// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.containerinstance.models;

import com.azure.core.annotation.Immutable;
import com.azure.resourcemanager.containerinstance.fluent.models.UsageInner;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** The response containing the usage data. */
@Immutable
public final class UsageListResult {
    /*
     * The usage data.
     */
    @JsonProperty(value = "value", access = JsonProperty.Access.WRITE_ONLY)
    private List<UsageInner> value;

    /**
     * Get the value property: The usage data.
     *
     * @return the value value.
     */
    public List<UsageInner> value() {
        return this.value;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (value() != null) {
            value().forEach(e -> e.validate());
        }
    }
}
