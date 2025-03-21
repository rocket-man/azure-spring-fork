// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.recoveryservicesbackup.models;

import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;

/** Resource collection API of ProtectionPolicies. */
public interface ProtectionPolicies {
    /**
     * Provides the details of the backup policies associated to Recovery Services Vault. This is an asynchronous
     * operation. Status of the operation can be fetched using GetPolicyOperationResult API.
     *
     * @param vaultName The name of the recovery services vault.
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param policyName Backup policy information to be fetched.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return base class for backup policy.
     */
    ProtectionPolicyResource get(String vaultName, String resourceGroupName, String policyName);

    /**
     * Provides the details of the backup policies associated to Recovery Services Vault. This is an asynchronous
     * operation. Status of the operation can be fetched using GetPolicyOperationResult API.
     *
     * @param vaultName The name of the recovery services vault.
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param policyName Backup policy information to be fetched.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return base class for backup policy along with {@link Response}.
     */
    Response<ProtectionPolicyResource> getWithResponse(
        String vaultName, String resourceGroupName, String policyName, Context context);

    /**
     * Deletes specified backup policy from your Recovery Services Vault. This is an asynchronous operation. Status of
     * the operation can be fetched using GetProtectionPolicyOperationResult API.
     *
     * @param vaultName The name of the recovery services vault.
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param policyName Backup policy to be deleted.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void delete(String vaultName, String resourceGroupName, String policyName);

    /**
     * Deletes specified backup policy from your Recovery Services Vault. This is an asynchronous operation. Status of
     * the operation can be fetched using GetProtectionPolicyOperationResult API.
     *
     * @param vaultName The name of the recovery services vault.
     * @param resourceGroupName The name of the resource group where the recovery services vault is present.
     * @param policyName Backup policy to be deleted.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void delete(String vaultName, String resourceGroupName, String policyName, Context context);

    /**
     * Provides the details of the backup policies associated to Recovery Services Vault. This is an asynchronous
     * operation. Status of the operation can be fetched using GetPolicyOperationResult API.
     *
     * @param id the resource ID.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return base class for backup policy along with {@link Response}.
     */
    ProtectionPolicyResource getById(String id);

    /**
     * Provides the details of the backup policies associated to Recovery Services Vault. This is an asynchronous
     * operation. Status of the operation can be fetched using GetPolicyOperationResult API.
     *
     * @param id the resource ID.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return base class for backup policy along with {@link Response}.
     */
    Response<ProtectionPolicyResource> getByIdWithResponse(String id, Context context);

    /**
     * Deletes specified backup policy from your Recovery Services Vault. This is an asynchronous operation. Status of
     * the operation can be fetched using GetProtectionPolicyOperationResult API.
     *
     * @param id the resource ID.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void deleteById(String id);

    /**
     * Deletes specified backup policy from your Recovery Services Vault. This is an asynchronous operation. Status of
     * the operation can be fetched using GetProtectionPolicyOperationResult API.
     *
     * @param id the resource ID.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    void deleteByIdWithResponse(String id, Context context);

    /**
     * Begins definition for a new ProtectionPolicyResource resource.
     *
     * @param name resource name.
     * @return the first stage of the new ProtectionPolicyResource definition.
     */
    ProtectionPolicyResource.DefinitionStages.Blank define(String name);
}
