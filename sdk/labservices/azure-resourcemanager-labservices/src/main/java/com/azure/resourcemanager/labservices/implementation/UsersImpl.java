// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.labservices.implementation;

import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.resourcemanager.labservices.fluent.UsersClient;
import com.azure.resourcemanager.labservices.fluent.models.UserInner;
import com.azure.resourcemanager.labservices.models.InviteBody;
import com.azure.resourcemanager.labservices.models.User;
import com.azure.resourcemanager.labservices.models.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;

public final class UsersImpl implements Users {
    @JsonIgnore private final ClientLogger logger = new ClientLogger(UsersImpl.class);

    private final UsersClient innerClient;

    private final com.azure.resourcemanager.labservices.LabServicesManager serviceManager;

    public UsersImpl(UsersClient innerClient, com.azure.resourcemanager.labservices.LabServicesManager serviceManager) {
        this.innerClient = innerClient;
        this.serviceManager = serviceManager;
    }

    public PagedIterable<User> listByLab(String resourceGroupName, String labName) {
        PagedIterable<UserInner> inner = this.serviceClient().listByLab(resourceGroupName, labName);
        return Utils.mapPage(inner, inner1 -> new UserImpl(inner1, this.manager()));
    }

    public PagedIterable<User> listByLab(String resourceGroupName, String labName, String filter, Context context) {
        PagedIterable<UserInner> inner = this.serviceClient().listByLab(resourceGroupName, labName, filter, context);
        return Utils.mapPage(inner, inner1 -> new UserImpl(inner1, this.manager()));
    }

    public User get(String resourceGroupName, String labName, String username) {
        UserInner inner = this.serviceClient().get(resourceGroupName, labName, username);
        if (inner != null) {
            return new UserImpl(inner, this.manager());
        } else {
            return null;
        }
    }

    public Response<User> getWithResponse(String resourceGroupName, String labName, String username, Context context) {
        Response<UserInner> inner = this.serviceClient().getWithResponse(resourceGroupName, labName, username, context);
        if (inner != null) {
            return new SimpleResponse<>(
                inner.getRequest(),
                inner.getStatusCode(),
                inner.getHeaders(),
                new UserImpl(inner.getValue(), this.manager()));
        } else {
            return null;
        }
    }

    public void delete(String resourceGroupName, String labName, String username) {
        this.serviceClient().delete(resourceGroupName, labName, username);
    }

    public void delete(String resourceGroupName, String labName, String username, Context context) {
        this.serviceClient().delete(resourceGroupName, labName, username, context);
    }

    public void invite(String resourceGroupName, String labName, String username, InviteBody body) {
        this.serviceClient().invite(resourceGroupName, labName, username, body);
    }

    public void invite(String resourceGroupName, String labName, String username, InviteBody body, Context context) {
        this.serviceClient().invite(resourceGroupName, labName, username, body, context);
    }

    public User getById(String id) {
        String resourceGroupName = Utils.getValueFromIdByName(id, "resourceGroups");
        if (resourceGroupName == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'resourceGroups'.", id)));
        }
        String labName = Utils.getValueFromIdByName(id, "labs");
        if (labName == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'labs'.", id)));
        }
        String username = Utils.getValueFromIdByName(id, "users");
        if (username == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'users'.", id)));
        }
        return this.getWithResponse(resourceGroupName, labName, username, Context.NONE).getValue();
    }

    public Response<User> getByIdWithResponse(String id, Context context) {
        String resourceGroupName = Utils.getValueFromIdByName(id, "resourceGroups");
        if (resourceGroupName == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'resourceGroups'.", id)));
        }
        String labName = Utils.getValueFromIdByName(id, "labs");
        if (labName == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'labs'.", id)));
        }
        String username = Utils.getValueFromIdByName(id, "users");
        if (username == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'users'.", id)));
        }
        return this.getWithResponse(resourceGroupName, labName, username, context);
    }

    public void deleteById(String id) {
        String resourceGroupName = Utils.getValueFromIdByName(id, "resourceGroups");
        if (resourceGroupName == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'resourceGroups'.", id)));
        }
        String labName = Utils.getValueFromIdByName(id, "labs");
        if (labName == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'labs'.", id)));
        }
        String username = Utils.getValueFromIdByName(id, "users");
        if (username == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'users'.", id)));
        }
        this.delete(resourceGroupName, labName, username, Context.NONE);
    }

    public void deleteByIdWithResponse(String id, Context context) {
        String resourceGroupName = Utils.getValueFromIdByName(id, "resourceGroups");
        if (resourceGroupName == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String
                            .format("The resource ID '%s' is not valid. Missing path segment 'resourceGroups'.", id)));
        }
        String labName = Utils.getValueFromIdByName(id, "labs");
        if (labName == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'labs'.", id)));
        }
        String username = Utils.getValueFromIdByName(id, "users");
        if (username == null) {
            throw logger
                .logExceptionAsError(
                    new IllegalArgumentException(
                        String.format("The resource ID '%s' is not valid. Missing path segment 'users'.", id)));
        }
        this.delete(resourceGroupName, labName, username, context);
    }

    private UsersClient serviceClient() {
        return this.innerClient;
    }

    private com.azure.resourcemanager.labservices.LabServicesManager manager() {
        return this.serviceManager;
    }

    public UserImpl define(String name) {
        return new UserImpl(name, this.manager());
    }
}
