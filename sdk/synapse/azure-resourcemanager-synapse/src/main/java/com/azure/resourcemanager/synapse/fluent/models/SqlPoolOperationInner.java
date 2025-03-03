// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.synapse.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.management.ProxyResource;
import com.azure.resourcemanager.synapse.models.ManagementOperationState;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/** A Sql pool operation. */
@Fluent
public final class SqlPoolOperationInner extends ProxyResource {
    /*
     * Resource properties.
     */
    @JsonProperty(value = "properties")
    private SqlPoolOperationProperties innerProperties;

    /**
     * Get the innerProperties property: Resource properties.
     *
     * @return the innerProperties value.
     */
    private SqlPoolOperationProperties innerProperties() {
        return this.innerProperties;
    }

    /**
     * Get the databaseName property: The name of the Sql pool the operation is being performed on.
     *
     * @return the databaseName value.
     */
    public String databaseName() {
        return this.innerProperties() == null ? null : this.innerProperties().databaseName();
    }

    /**
     * Get the operation property: The name of operation.
     *
     * @return the operation value.
     */
    public String operation() {
        return this.innerProperties() == null ? null : this.innerProperties().operation();
    }

    /**
     * Get the operationFriendlyName property: The friendly name of operation.
     *
     * @return the operationFriendlyName value.
     */
    public String operationFriendlyName() {
        return this.innerProperties() == null ? null : this.innerProperties().operationFriendlyName();
    }

    /**
     * Get the percentComplete property: The percentage of the operation completed.
     *
     * @return the percentComplete value.
     */
    public Integer percentComplete() {
        return this.innerProperties() == null ? null : this.innerProperties().percentComplete();
    }

    /**
     * Get the serverName property: The name of the server.
     *
     * @return the serverName value.
     */
    public String serverName() {
        return this.innerProperties() == null ? null : this.innerProperties().serverName();
    }

    /**
     * Get the startTime property: The operation start time.
     *
     * @return the startTime value.
     */
    public OffsetDateTime startTime() {
        return this.innerProperties() == null ? null : this.innerProperties().startTime();
    }

    /**
     * Get the state property: The operation state.
     *
     * @return the state value.
     */
    public ManagementOperationState state() {
        return this.innerProperties() == null ? null : this.innerProperties().state();
    }

    /**
     * Get the errorCode property: The operation error code.
     *
     * @return the errorCode value.
     */
    public Integer errorCode() {
        return this.innerProperties() == null ? null : this.innerProperties().errorCode();
    }

    /**
     * Get the errorDescription property: The operation error description.
     *
     * @return the errorDescription value.
     */
    public String errorDescription() {
        return this.innerProperties() == null ? null : this.innerProperties().errorDescription();
    }

    /**
     * Get the errorSeverity property: The operation error severity.
     *
     * @return the errorSeverity value.
     */
    public Integer errorSeverity() {
        return this.innerProperties() == null ? null : this.innerProperties().errorSeverity();
    }

    /**
     * Get the isUserError property: Whether or not the error is a user error.
     *
     * @return the isUserError value.
     */
    public Boolean isUserError() {
        return this.innerProperties() == null ? null : this.innerProperties().isUserError();
    }

    /**
     * Get the estimatedCompletionTime property: The estimated completion time of the operation.
     *
     * @return the estimatedCompletionTime value.
     */
    public OffsetDateTime estimatedCompletionTime() {
        return this.innerProperties() == null ? null : this.innerProperties().estimatedCompletionTime();
    }

    /**
     * Get the description property: The operation description.
     *
     * @return the description value.
     */
    public String description() {
        return this.innerProperties() == null ? null : this.innerProperties().description();
    }

    /**
     * Get the isCancellable property: Whether the operation can be cancelled.
     *
     * @return the isCancellable value.
     */
    public Boolean isCancellable() {
        return this.innerProperties() == null ? null : this.innerProperties().isCancellable();
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
