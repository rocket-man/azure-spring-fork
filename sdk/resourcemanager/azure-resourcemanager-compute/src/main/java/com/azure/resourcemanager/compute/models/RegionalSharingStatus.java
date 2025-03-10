// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.compute.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Gallery regional sharing status. */
@Fluent
public final class RegionalSharingStatus {
    /*
     * Region name
     */
    @JsonProperty(value = "region")
    private String region;

    /*
     * The sharing state of the gallery. Gallery sharing state in current
     * region
     */
    @JsonProperty(value = "state", access = JsonProperty.Access.WRITE_ONLY)
    private SharingState state;

    /*
     * Details of gallery regional sharing failure.
     */
    @JsonProperty(value = "details")
    private String details;

    /**
     * Get the region property: Region name.
     *
     * @return the region value.
     */
    public String region() {
        return this.region;
    }

    /**
     * Set the region property: Region name.
     *
     * @param region the region value to set.
     * @return the RegionalSharingStatus object itself.
     */
    public RegionalSharingStatus withRegion(String region) {
        this.region = region;
        return this;
    }

    /**
     * Get the state property: The sharing state of the gallery. Gallery sharing state in current region.
     *
     * @return the state value.
     */
    public SharingState state() {
        return this.state;
    }

    /**
     * Get the details property: Details of gallery regional sharing failure.
     *
     * @return the details value.
     */
    public String details() {
        return this.details;
    }

    /**
     * Set the details property: Details of gallery regional sharing failure.
     *
     * @param details the details value to set.
     * @return the RegionalSharingStatus object itself.
     */
    public RegionalSharingStatus withDetails(String details) {
        this.details = details;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }
}
