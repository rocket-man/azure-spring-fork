// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.ai.textanalytics.implementation.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/** An object representing the task definition for a Key Phrase Extraction task. */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "kind")
@JsonTypeName("KeyPhraseExtraction")
@Fluent
public final class KeyPhraseLROTask extends AnalyzeTextLROTask {
    /*
     * Supported parameters for a Key Phrase Extraction task.
     */
    @JsonProperty(value = "parameters")
    private KeyPhraseTaskParameters parameters;

    /**
     * Get the parameters property: Supported parameters for a Key Phrase Extraction task.
     *
     * @return the parameters value.
     */
    public KeyPhraseTaskParameters getParameters() {
        return this.parameters;
    }

    /**
     * Set the parameters property: Supported parameters for a Key Phrase Extraction task.
     *
     * @param parameters the parameters value to set.
     * @return the KeyPhraseLROTask object itself.
     */
    public KeyPhraseLROTask setParameters(KeyPhraseTaskParameters parameters) {
        this.parameters = parameters;
        return this;
    }
}
