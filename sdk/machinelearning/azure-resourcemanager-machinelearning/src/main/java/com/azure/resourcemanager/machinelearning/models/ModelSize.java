// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.machinelearning.models;

import com.azure.core.util.ExpandableStringEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Collection;

/** Defines values for ModelSize. */
public final class ModelSize extends ExpandableStringEnum<ModelSize> {
    /** Static value None for ModelSize. */
    public static final ModelSize NONE = fromString("None");

    /** Static value Small for ModelSize. */
    public static final ModelSize SMALL = fromString("Small");

    /** Static value Medium for ModelSize. */
    public static final ModelSize MEDIUM = fromString("Medium");

    /** Static value Large for ModelSize. */
    public static final ModelSize LARGE = fromString("Large");

    /** Static value ExtraLarge for ModelSize. */
    public static final ModelSize EXTRA_LARGE = fromString("ExtraLarge");

    /**
     * Creates or finds a ModelSize from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding ModelSize.
     */
    @JsonCreator
    public static ModelSize fromString(String name) {
        return fromString(name, ModelSize.class);
    }

    /**
     * Gets known ModelSize values.
     *
     * @return known ModelSize values.
     */
    public static Collection<ModelSize> values() {
        return values(ModelSize.class);
    }
}
