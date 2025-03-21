// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.batch.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/** KeyVault configuration when using an encryption KeySource of Microsoft.KeyVault. */
@Fluent
public final class KeyVaultProperties {
    /*
     * Full path to the versioned secret. Example
     * https://mykeyvault.vault.azure.net/keys/testkey/6e34a81fef704045975661e297a4c053.
     * To be usable the following prerequisites must be met:
     *
     * The Batch Account has a System Assigned identity
     * The account identity has been granted Key/Get, Key/Unwrap and Key/Wrap
     * permissions
     * The KeyVault has soft-delete and purge protection enabled
     */
    @JsonProperty(value = "keyIdentifier")
    private String keyIdentifier;

    /**
     * Get the keyIdentifier property: Full path to the versioned secret. Example
     * https://mykeyvault.vault.azure.net/keys/testkey/6e34a81fef704045975661e297a4c053. To be usable the following
     * prerequisites must be met:
     *
     * <p>The Batch Account has a System Assigned identity The account identity has been granted Key/Get, Key/Unwrap and
     * Key/Wrap permissions The KeyVault has soft-delete and purge protection enabled.
     *
     * @return the keyIdentifier value.
     */
    public String keyIdentifier() {
        return this.keyIdentifier;
    }

    /**
     * Set the keyIdentifier property: Full path to the versioned secret. Example
     * https://mykeyvault.vault.azure.net/keys/testkey/6e34a81fef704045975661e297a4c053. To be usable the following
     * prerequisites must be met:
     *
     * <p>The Batch Account has a System Assigned identity The account identity has been granted Key/Get, Key/Unwrap and
     * Key/Wrap permissions The KeyVault has soft-delete and purge protection enabled.
     *
     * @param keyIdentifier the keyIdentifier value to set.
     * @return the KeyVaultProperties object itself.
     */
    public KeyVaultProperties withKeyIdentifier(String keyIdentifier) {
        this.keyIdentifier = keyIdentifier;
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
