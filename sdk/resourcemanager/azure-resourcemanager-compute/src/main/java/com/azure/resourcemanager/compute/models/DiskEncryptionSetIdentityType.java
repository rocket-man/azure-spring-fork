// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.compute.models;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collection;

/** Defines values for DiskEncryptionSetIdentityType. */
public final class DiskEncryptionSetIdentityType extends ExpandableStringEnum<DiskEncryptionSetIdentityType> {
    /** Static value SystemAssigned for DiskEncryptionSetIdentityType. */
    public static final DiskEncryptionSetIdentityType SYSTEM_ASSIGNED = fromString("SystemAssigned");

    /** Static value UserAssigned for DiskEncryptionSetIdentityType. */
    public static final DiskEncryptionSetIdentityType USER_ASSIGNED = fromString("UserAssigned");

    /** Static value SystemAssigned, UserAssigned for DiskEncryptionSetIdentityType. */
    public static final DiskEncryptionSetIdentityType SYSTEM_ASSIGNED_USER_ASSIGNED =
        fromString("SystemAssigned, UserAssigned");

    /** Static value None for DiskEncryptionSetIdentityType. */
    public static final DiskEncryptionSetIdentityType NONE = fromString("None");

    /**
     * Creates or finds a DiskEncryptionSetIdentityType from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding DiskEncryptionSetIdentityType.
     */
    @JsonCreator
    public static DiskEncryptionSetIdentityType fromString(String name) {
        return fromString(name, DiskEncryptionSetIdentityType.class);
    }

    /**
     * Gets known DiskEncryptionSetIdentityType values.
     *
     * @return known DiskEncryptionSetIdentityType values.
     */
    public static Collection<DiskEncryptionSetIdentityType> values() {
        return values(DiskEncryptionSetIdentityType.class);
    }
}
