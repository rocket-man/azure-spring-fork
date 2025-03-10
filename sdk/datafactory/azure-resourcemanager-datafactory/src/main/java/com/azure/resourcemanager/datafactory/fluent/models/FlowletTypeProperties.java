// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.datafactory.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.resourcemanager.datafactory.models.DataFlowSink;
import com.azure.resourcemanager.datafactory.models.DataFlowSource;
import com.azure.resourcemanager.datafactory.models.Transformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** Flowlet type properties. */
@Fluent
public final class FlowletTypeProperties {
    /*
     * List of sources in Flowlet.
     */
    @JsonProperty(value = "sources")
    private List<DataFlowSource> sources;

    /*
     * List of sinks in Flowlet.
     */
    @JsonProperty(value = "sinks")
    private List<DataFlowSink> sinks;

    /*
     * List of transformations in Flowlet.
     */
    @JsonProperty(value = "transformations")
    private List<Transformation> transformations;

    /*
     * Flowlet script.
     */
    @JsonProperty(value = "script")
    private String script;

    /*
     * Flowlet script lines.
     */
    @JsonProperty(value = "scriptLines")
    private List<String> scriptLines;

    /**
     * Get the sources property: List of sources in Flowlet.
     *
     * @return the sources value.
     */
    public List<DataFlowSource> sources() {
        return this.sources;
    }

    /**
     * Set the sources property: List of sources in Flowlet.
     *
     * @param sources the sources value to set.
     * @return the FlowletTypeProperties object itself.
     */
    public FlowletTypeProperties withSources(List<DataFlowSource> sources) {
        this.sources = sources;
        return this;
    }

    /**
     * Get the sinks property: List of sinks in Flowlet.
     *
     * @return the sinks value.
     */
    public List<DataFlowSink> sinks() {
        return this.sinks;
    }

    /**
     * Set the sinks property: List of sinks in Flowlet.
     *
     * @param sinks the sinks value to set.
     * @return the FlowletTypeProperties object itself.
     */
    public FlowletTypeProperties withSinks(List<DataFlowSink> sinks) {
        this.sinks = sinks;
        return this;
    }

    /**
     * Get the transformations property: List of transformations in Flowlet.
     *
     * @return the transformations value.
     */
    public List<Transformation> transformations() {
        return this.transformations;
    }

    /**
     * Set the transformations property: List of transformations in Flowlet.
     *
     * @param transformations the transformations value to set.
     * @return the FlowletTypeProperties object itself.
     */
    public FlowletTypeProperties withTransformations(List<Transformation> transformations) {
        this.transformations = transformations;
        return this;
    }

    /**
     * Get the script property: Flowlet script.
     *
     * @return the script value.
     */
    public String script() {
        return this.script;
    }

    /**
     * Set the script property: Flowlet script.
     *
     * @param script the script value to set.
     * @return the FlowletTypeProperties object itself.
     */
    public FlowletTypeProperties withScript(String script) {
        this.script = script;
        return this;
    }

    /**
     * Get the scriptLines property: Flowlet script lines.
     *
     * @return the scriptLines value.
     */
    public List<String> scriptLines() {
        return this.scriptLines;
    }

    /**
     * Set the scriptLines property: Flowlet script lines.
     *
     * @param scriptLines the scriptLines value to set.
     * @return the FlowletTypeProperties object itself.
     */
    public FlowletTypeProperties withScriptLines(List<String> scriptLines) {
        this.scriptLines = scriptLines;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (sources() != null) {
            sources().forEach(e -> e.validate());
        }
        if (sinks() != null) {
            sinks().forEach(e -> e.validate());
        }
        if (transformations() != null) {
            transformations().forEach(e -> e.validate());
        }
    }
}
