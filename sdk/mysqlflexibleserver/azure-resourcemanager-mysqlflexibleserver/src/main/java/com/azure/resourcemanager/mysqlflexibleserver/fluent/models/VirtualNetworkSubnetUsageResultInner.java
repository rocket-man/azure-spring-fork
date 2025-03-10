// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.mysqlflexibleserver.fluent.models;

import com.azure.core.annotation.Immutable;
import com.azure.resourcemanager.mysqlflexibleserver.models.DelegatedSubnetUsage;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Virtual network subnet usage data. */
@Immutable
public final class VirtualNetworkSubnetUsageResultInner {
    /*
     * A list of delegated subnet usage
     */
    @JsonProperty(value = "delegatedSubnetsUsage", access = JsonProperty.Access.WRITE_ONLY)
    private List<DelegatedSubnetUsage> delegatedSubnetsUsage;

    /**
     * Get the delegatedSubnetsUsage property: A list of delegated subnet usage.
     *
     * @return the delegatedSubnetsUsage value.
     */
    public List<DelegatedSubnetUsage> delegatedSubnetsUsage() {
        return this.delegatedSubnetsUsage;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (delegatedSubnetsUsage() != null) {
            delegatedSubnetsUsage().forEach(e -> e.validate());
        }
    }
}
