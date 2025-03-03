// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.analytics.synapse.artifacts.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/** The LinkConnectionSourceDatabase model. */
@Fluent
public final class LinkConnectionSourceDatabase {
    /*
     * Linked service reference
     */
    @JsonProperty(value = "linkedService")
    private LinkedServiceReference linkedService;

    /*
     * Source database type properties
     */
    @JsonProperty(value = "typeProperties")
    private LinkConnectionSourceDatabaseTypeProperties typeProperties;

    /**
     * Get the linkedService property: Linked service reference.
     *
     * @return the linkedService value.
     */
    public LinkedServiceReference getLinkedService() {
        return this.linkedService;
    }

    /**
     * Set the linkedService property: Linked service reference.
     *
     * @param linkedService the linkedService value to set.
     * @return the LinkConnectionSourceDatabase object itself.
     */
    public LinkConnectionSourceDatabase setLinkedService(LinkedServiceReference linkedService) {
        this.linkedService = linkedService;
        return this;
    }

    /**
     * Get the typeProperties property: Source database type properties.
     *
     * @return the typeProperties value.
     */
    public LinkConnectionSourceDatabaseTypeProperties getTypeProperties() {
        return this.typeProperties;
    }

    /**
     * Set the typeProperties property: Source database type properties.
     *
     * @param typeProperties the typeProperties value to set.
     * @return the LinkConnectionSourceDatabase object itself.
     */
    public LinkConnectionSourceDatabase setTypeProperties(LinkConnectionSourceDatabaseTypeProperties typeProperties) {
        this.typeProperties = typeProperties;
        return this;
    }
}
