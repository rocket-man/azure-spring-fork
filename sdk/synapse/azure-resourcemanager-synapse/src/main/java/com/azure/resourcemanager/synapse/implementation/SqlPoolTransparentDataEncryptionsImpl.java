// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.synapse.implementation;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.synapse.fluent.SqlPoolTransparentDataEncryptionsClient;
import com.azure.resourcemanager.synapse.fluent.models.TransparentDataEncryptionInner;
import com.azure.resourcemanager.synapse.models.SqlPoolTransparentDataEncryptions;
import com.azure.resourcemanager.synapse.models.TransparentDataEncryption;
import com.azure.resourcemanager.synapse.models.TransparentDataEncryptionName;

public final class SqlPoolTransparentDataEncryptionsImpl implements SqlPoolTransparentDataEncryptions {
    private static final ClientLogger LOGGER = new ClientLogger(SqlPoolTransparentDataEncryptionsImpl.class);

    private final SqlPoolTransparentDataEncryptionsClient innerClient;

    private final com.azure.resourcemanager.synapse.SynapseManager serviceManager;

    public SqlPoolTransparentDataEncryptionsImpl(
        SqlPoolTransparentDataEncryptionsClient innerClient,
        com.azure.resourcemanager.synapse.SynapseManager serviceManager) {
        this.innerClient = innerClient;
        this.serviceManager = serviceManager;
    }

    public TransparentDataEncryption get(
        String resourceGroupName,
        String workspaceName,
        String sqlPoolName,
        TransparentDataEncryptionName transparentDataEncryptionName) {
        TransparentDataEncryptionInner inner =
            this.serviceClient().get(resourceGroupName, workspaceName, sqlPoolName, transparentDataEncryptionName);
        if (inner != null) {
            return new TransparentDataEncryptionImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public Response<TransparentDataEncryption> getWithResponse(
        String resourceGroupName,
        String workspaceName,
        String sqlPoolName,
        TransparentDataEncryptionName transparentDataEncryptionName,
        Context context) {
        Response<TransparentDataEncryptionInner> inner =
            this
                .serviceClient()
                .getWithResponse(resourceGroupName, workspaceName, sqlPoolName, transparentDataEncryptionName, context);
        if (inner != null) {
            return new SimpleResponse<>(
                inner.getRequest(),
                inner.getStatusCode(),
                inner.getHeaders(),
                new TransparentDataEncryptionImpl(inner.getValue(), this.manager()));
        } else {
            return null;
        }
    }

    public PagedIterable<TransparentDataEncryption> list(
        String resourceGroupName, String workspaceName, String sqlPoolName) {
        PagedIterable<TransparentDataEncryptionInner> inner =
            this.serviceClient().list(resourceGroupName, workspaceName, sqlPoolName);
        return Utils.mapPage(inner, inner1 -> new TransparentDataEncryptionImpl(inner1, this.manager()));
    }

    public PagedIterable<TransparentDataEncryption> list(
        String resourceGroupName, String workspaceName, String sqlPoolName, Context context) {
        PagedIterable<TransparentDataEncryptionInner> inner =
            this.serviceClient().list(resourceGroupName, workspaceName, sqlPoolName, context);
        return Utils.mapPage(inner, inner1 -> new TransparentDataEncryptionImpl(inner1, this.manager()));
    }

    public TransparentDataEncryption getById(String id) {
        String resourceGroupName = Utils.getValueFromIdByName(id, "resourceGroups");
        if (resourceGroupName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'resourceGroups'.", id)));
        }
        String workspaceName = Utils.getValueFromIdByName(id, "workspaces");
        if (workspaceName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'workspaces'.", id)));
        }
        String sqlPoolName = Utils.getValueFromIdByName(id, "sqlPools");
        if (sqlPoolName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'sqlPools'.", id)));
        }
        TransparentDataEncryptionName transparentDataEncryptionName =
            TransparentDataEncryptionName.fromString(Utils.getValueFromIdByName(id, "transparentDataEncryption"));
        if (transparentDataEncryptionName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format(
                                "The resource ID '%s' is not valid. Missing path segment 'transparentDataEncryption'.",
                                id)));
        }
        return this
            .getWithResponse(resourceGroupName, workspaceName, sqlPoolName, transparentDataEncryptionName, Context.NONE)
            .getValue();
    }

    public Response<TransparentDataEncryption> getByIdWithResponse(String id, Context context) {
        String resourceGroupName = Utils.getValueFromIdByName(id, "resourceGroups");
        if (resourceGroupName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'resourceGroups'.", id)));
        }
        String workspaceName = Utils.getValueFromIdByName(id, "workspaces");
        if (workspaceName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'workspaces'.", id)));
        }
        String sqlPoolName = Utils.getValueFromIdByName(id, "sqlPools");
        if (sqlPoolName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'sqlPools'.", id)));
        }
        TransparentDataEncryptionName transparentDataEncryptionName =
            TransparentDataEncryptionName.fromString(Utils.getValueFromIdByName(id, "transparentDataEncryption"));
        if (transparentDataEncryptionName == null) {
            throw LOGGER
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format(
                                "The resource ID '%s' is not valid. Missing path segment 'transparentDataEncryption'.",
                                id)));
        }
        return this
            .getWithResponse(resourceGroupName, workspaceName, sqlPoolName, transparentDataEncryptionName, context);
    }

    private SqlPoolTransparentDataEncryptionsClient serviceClient() {
        return this.innerClient;
    }

    private com.azure.resourcemanager.synapse.SynapseManager manager() {
        return this.serviceManager;
    }

    public TransparentDataEncryptionImpl define(TransparentDataEncryptionName name) {
        return new TransparentDataEncryptionImpl(name, this.manager());
    }
}
