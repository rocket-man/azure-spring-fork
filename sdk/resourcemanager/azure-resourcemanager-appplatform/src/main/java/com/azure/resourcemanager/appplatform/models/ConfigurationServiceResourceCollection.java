// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.appplatform.models;

import com.azure.core.annotation.Fluent;
import com.azure.resourcemanager.appplatform.fluent.models.ConfigurationServiceResourceInner;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Object that includes an array of configuration service resources and a possible link for next set. */
@Fluent
public final class ConfigurationServiceResourceCollection {
    /*
     * Collection of configuration service resources
     */
    @JsonProperty(value = "value")
    private List<ConfigurationServiceResourceInner> value;

    /*
     * URL client should use to fetch the next page (per server side paging).
     * It's null for now, added for future use.
     */
    @JsonProperty(value = "nextLink")
    private String nextLink;

    /**
     * Get the value property: Collection of configuration service resources.
     *
     * @return the value value.
     */
    public List<ConfigurationServiceResourceInner> value() {
        return this.value;
    }

    /**
     * Set the value property: Collection of configuration service resources.
     *
     * @param value the value value to set.
     * @return the ConfigurationServiceResourceCollection object itself.
     */
    public ConfigurationServiceResourceCollection withValue(List<ConfigurationServiceResourceInner> value) {
        this.value = value;
        return this;
    }

    /**
     * Get the nextLink property: URL client should use to fetch the next page (per server side paging). It's null for
     * now, added for future use.
     *
     * @return the nextLink value.
     */
    public String nextLink() {
        return this.nextLink;
    }

    /**
     * Set the nextLink property: URL client should use to fetch the next page (per server side paging). It's null for
     * now, added for future use.
     *
     * @param nextLink the nextLink value to set.
     * @return the ConfigurationServiceResourceCollection object itself.
     */
    public ConfigurationServiceResourceCollection withNextLink(String nextLink) {
        this.nextLink = nextLink;
        return this;
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
