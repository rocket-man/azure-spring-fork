// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.storagecache.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Properties pertaining to the BlobNfsTarget. */
@Fluent
public final class BlobNfsTarget {
    /*
     * Resource ID of the storage container.
     */
    @JsonProperty(value = "target")
    private String target;

    /*
     * Identifies the StorageCache usage model to be used for this storage
     * target.
     */
    @JsonProperty(value = "usageModel")
    private String usageModel;

    /**
     * Get the target property: Resource ID of the storage container.
     *
     * @return the target value.
     */
    public String target() {
        return this.target;
    }

    /**
     * Set the target property: Resource ID of the storage container.
     *
     * @param target the target value to set.
     * @return the BlobNfsTarget object itself.
     */
    public BlobNfsTarget withTarget(String target) {
        this.target = target;
        return this;
    }

    /**
     * Get the usageModel property: Identifies the StorageCache usage model to be used for this storage target.
     *
     * @return the usageModel value.
     */
    public String usageModel() {
        return this.usageModel;
    }

    /**
     * Set the usageModel property: Identifies the StorageCache usage model to be used for this storage target.
     *
     * @param usageModel the usageModel value to set.
     * @return the BlobNfsTarget object itself.
     */
    public BlobNfsTarget withUsageModel(String usageModel) {
        this.usageModel = usageModel;
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
