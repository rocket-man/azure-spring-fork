// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.appcontainers.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Container App registry information. */
@Fluent
public final class RegistryInfo {
    /*
     * registry server Url.
     */
    @JsonProperty(value = "registryUrl")
    private String registryUrl;

    /*
     * registry username.
     */
    @JsonProperty(value = "registryUserName")
    private String registryUsername;

    /*
     * registry secret.
     */
    @JsonProperty(value = "registryPassword")
    private String registryPassword;

    /**
     * Get the registryUrl property: registry server Url.
     *
     * @return the registryUrl value.
     */
    public String registryUrl() {
        return this.registryUrl;
    }

    /**
     * Set the registryUrl property: registry server Url.
     *
     * @param registryUrl the registryUrl value to set.
     * @return the RegistryInfo object itself.
     */
    public RegistryInfo withRegistryUrl(String registryUrl) {
        this.registryUrl = registryUrl;
        return this;
    }

    /**
     * Get the registryUsername property: registry username.
     *
     * @return the registryUsername value.
     */
    public String registryUsername() {
        return this.registryUsername;
    }

    /**
     * Set the registryUsername property: registry username.
     *
     * @param registryUsername the registryUsername value to set.
     * @return the RegistryInfo object itself.
     */
    public RegistryInfo withRegistryUsername(String registryUsername) {
        this.registryUsername = registryUsername;
        return this;
    }

    /**
     * Get the registryPassword property: registry secret.
     *
     * @return the registryPassword value.
     */
    public String registryPassword() {
        return this.registryPassword;
    }

    /**
     * Set the registryPassword property: registry secret.
     *
     * @param registryPassword the registryPassword value to set.
     * @return the RegistryInfo object itself.
     */
    public RegistryInfo withRegistryPassword(String registryPassword) {
        this.registryPassword = registryPassword;
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
