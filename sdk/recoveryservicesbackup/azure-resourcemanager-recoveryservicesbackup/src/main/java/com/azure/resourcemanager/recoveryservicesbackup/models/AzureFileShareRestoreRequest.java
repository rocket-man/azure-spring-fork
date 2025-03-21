// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.recoveryservicesbackup.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;

/** AzureFileShare Restore Request. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonTypeName("AzureFileShareRestoreRequest")
@Fluent
public final class AzureFileShareRestoreRequest extends RestoreRequest {
    /*
     * Type of this recovery.
     */
    @JsonProperty(value = "recoveryType")
    private RecoveryType recoveryType;

    /*
     * Source storage account ARM Id
     */
    @JsonProperty(value = "sourceResourceId")
    private String sourceResourceId;

    /*
     * Options to resolve copy conflicts.
     */
    @JsonProperty(value = "copyOptions")
    private CopyOptions copyOptions;

    /*
     * Restore Type (FullShareRestore or ItemLevelRestore)
     */
    @JsonProperty(value = "restoreRequestType")
    private RestoreRequestType restoreRequestType;

    /*
     * List of Source Files/Folders(which need to recover) and TargetFolderPath
     * details
     */
    @JsonProperty(value = "restoreFileSpecs")
    private List<RestoreFileSpecs> restoreFileSpecs;

    /*
     * Target File Share Details
     */
    @JsonProperty(value = "targetDetails")
    private TargetAfsRestoreInfo targetDetails;

    /**
     * Get the recoveryType property: Type of this recovery.
     *
     * @return the recoveryType value.
     */
    public RecoveryType recoveryType() {
        return this.recoveryType;
    }

    /**
     * Set the recoveryType property: Type of this recovery.
     *
     * @param recoveryType the recoveryType value to set.
     * @return the AzureFileShareRestoreRequest object itself.
     */
    public AzureFileShareRestoreRequest withRecoveryType(RecoveryType recoveryType) {
        this.recoveryType = recoveryType;
        return this;
    }

    /**
     * Get the sourceResourceId property: Source storage account ARM Id.
     *
     * @return the sourceResourceId value.
     */
    public String sourceResourceId() {
        return this.sourceResourceId;
    }

    /**
     * Set the sourceResourceId property: Source storage account ARM Id.
     *
     * @param sourceResourceId the sourceResourceId value to set.
     * @return the AzureFileShareRestoreRequest object itself.
     */
    public AzureFileShareRestoreRequest withSourceResourceId(String sourceResourceId) {
        this.sourceResourceId = sourceResourceId;
        return this;
    }

    /**
     * Get the copyOptions property: Options to resolve copy conflicts.
     *
     * @return the copyOptions value.
     */
    public CopyOptions copyOptions() {
        return this.copyOptions;
    }

    /**
     * Set the copyOptions property: Options to resolve copy conflicts.
     *
     * @param copyOptions the copyOptions value to set.
     * @return the AzureFileShareRestoreRequest object itself.
     */
    public AzureFileShareRestoreRequest withCopyOptions(CopyOptions copyOptions) {
        this.copyOptions = copyOptions;
        return this;
    }

    /**
     * Get the restoreRequestType property: Restore Type (FullShareRestore or ItemLevelRestore).
     *
     * @return the restoreRequestType value.
     */
    public RestoreRequestType restoreRequestType() {
        return this.restoreRequestType;
    }

    /**
     * Set the restoreRequestType property: Restore Type (FullShareRestore or ItemLevelRestore).
     *
     * @param restoreRequestType the restoreRequestType value to set.
     * @return the AzureFileShareRestoreRequest object itself.
     */
    public AzureFileShareRestoreRequest withRestoreRequestType(RestoreRequestType restoreRequestType) {
        this.restoreRequestType = restoreRequestType;
        return this;
    }

    /**
     * Get the restoreFileSpecs property: List of Source Files/Folders(which need to recover) and TargetFolderPath
     * details.
     *
     * @return the restoreFileSpecs value.
     */
    public List<RestoreFileSpecs> restoreFileSpecs() {
        return this.restoreFileSpecs;
    }

    /**
     * Set the restoreFileSpecs property: List of Source Files/Folders(which need to recover) and TargetFolderPath
     * details.
     *
     * @param restoreFileSpecs the restoreFileSpecs value to set.
     * @return the AzureFileShareRestoreRequest object itself.
     */
    public AzureFileShareRestoreRequest withRestoreFileSpecs(List<RestoreFileSpecs> restoreFileSpecs) {
        this.restoreFileSpecs = restoreFileSpecs;
        return this;
    }

    /**
     * Get the targetDetails property: Target File Share Details.
     *
     * @return the targetDetails value.
     */
    public TargetAfsRestoreInfo targetDetails() {
        return this.targetDetails;
    }

    /**
     * Set the targetDetails property: Target File Share Details.
     *
     * @param targetDetails the targetDetails value to set.
     * @return the AzureFileShareRestoreRequest object itself.
     */
    public AzureFileShareRestoreRequest withTargetDetails(TargetAfsRestoreInfo targetDetails) {
        this.targetDetails = targetDetails;
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
        if (restoreFileSpecs() != null) {
            restoreFileSpecs().forEach(e -> e.validate());
        }
        if (targetDetails() != null) {
            targetDetails().validate();
        }
    }
}
