// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.mobilenetwork.models;

import com.azure.core.management.Region;
import com.azure.core.management.SystemData;
import com.azure.core.util.Context;
import com.azure.resourcemanager.mobilenetwork.fluent.models.ServiceInner;
import java.util.List;
import java.util.Map;

/** An immutable client-side representation of Service. */
public interface Service {
    /**
     * Gets the id property: Fully qualified resource Id for the resource.
     *
     * @return the id value.
     */
    String id();

    /**
     * Gets the name property: The name of the resource.
     *
     * @return the name value.
     */
    String name();

    /**
     * Gets the type property: The type of the resource.
     *
     * @return the type value.
     */
    String type();

    /**
     * Gets the location property: The geo-location where the resource lives.
     *
     * @return the location value.
     */
    String location();

    /**
     * Gets the tags property: Resource tags.
     *
     * @return the tags value.
     */
    Map<String, String> tags();

    /**
     * Gets the systemData property: Azure Resource Manager metadata containing createdBy and modifiedBy information.
     *
     * @return the systemData value.
     */
    SystemData systemData();

    /**
     * Gets the provisioningState property: The provisioning state of the service resource.
     *
     * @return the provisioningState value.
     */
    ProvisioningState provisioningState();

    /**
     * Gets the servicePrecedence property: A precedence value that is used to decide between services when identifying
     * the QoS values to use for a particular Sim. A lower value means a higher priority. This value should be unique
     * among all services configured in the Mobile Network.
     *
     * @return the servicePrecedence value.
     */
    int servicePrecedence();

    /**
     * Gets the serviceQosPolicy property: The QoS policy to use for packets matching this service. This can be
     * overridden for particular flows using the ruleQosPolicy field in a PccRuleConfiguration. If this field is null
     * then the UE's simPolicy will define the QoS settings.
     *
     * @return the serviceQosPolicy value.
     */
    QosPolicy serviceQosPolicy();

    /**
     * Gets the pccRules property: The set of PCC Rules that make up this service.
     *
     * @return the pccRules value.
     */
    List<PccRuleConfiguration> pccRules();

    /**
     * Gets the region of the resource.
     *
     * @return the region of the resource.
     */
    Region region();

    /**
     * Gets the name of the resource region.
     *
     * @return the name of the resource region.
     */
    String regionName();

    /**
     * Gets the inner com.azure.resourcemanager.mobilenetwork.fluent.models.ServiceInner object.
     *
     * @return the inner object.
     */
    ServiceInner innerModel();

    /** The entirety of the Service definition. */
    interface Definition
        extends DefinitionStages.Blank,
            DefinitionStages.WithLocation,
            DefinitionStages.WithParentResource,
            DefinitionStages.WithServicePrecedence,
            DefinitionStages.WithPccRules,
            DefinitionStages.WithCreate {
    }
    /** The Service definition stages. */
    interface DefinitionStages {
        /** The first stage of the Service definition. */
        interface Blank extends WithLocation {
        }
        /** The stage of the Service definition allowing to specify location. */
        interface WithLocation {
            /**
             * Specifies the region for the resource.
             *
             * @param location The geo-location where the resource lives.
             * @return the next definition stage.
             */
            WithParentResource withRegion(Region location);

            /**
             * Specifies the region for the resource.
             *
             * @param location The geo-location where the resource lives.
             * @return the next definition stage.
             */
            WithParentResource withRegion(String location);
        }
        /** The stage of the Service definition allowing to specify parent resource. */
        interface WithParentResource {
            /**
             * Specifies resourceGroupName, mobileNetworkName.
             *
             * @param resourceGroupName The name of the resource group. The name is case insensitive.
             * @param mobileNetworkName The name of the mobile network.
             * @return the next definition stage.
             */
            WithServicePrecedence withExistingMobileNetwork(String resourceGroupName, String mobileNetworkName);
        }
        /** The stage of the Service definition allowing to specify servicePrecedence. */
        interface WithServicePrecedence {
            /**
             * Specifies the servicePrecedence property: A precedence value that is used to decide between services when
             * identifying the QoS values to use for a particular Sim. A lower value means a higher priority. This value
             * should be unique among all services configured in the Mobile Network..
             *
             * @param servicePrecedence A precedence value that is used to decide between services when identifying the
             *     QoS values to use for a particular Sim. A lower value means a higher priority. This value should be
             *     unique among all services configured in the Mobile Network.
             * @return the next definition stage.
             */
            WithPccRules withServicePrecedence(int servicePrecedence);
        }
        /** The stage of the Service definition allowing to specify pccRules. */
        interface WithPccRules {
            /**
             * Specifies the pccRules property: The set of PCC Rules that make up this service..
             *
             * @param pccRules The set of PCC Rules that make up this service.
             * @return the next definition stage.
             */
            WithCreate withPccRules(List<PccRuleConfiguration> pccRules);
        }
        /**
         * The stage of the Service definition which contains all the minimum required properties for the resource to be
         * created, but also allows for any other optional properties to be specified.
         */
        interface WithCreate extends DefinitionStages.WithTags, DefinitionStages.WithServiceQosPolicy {
            /**
             * Executes the create request.
             *
             * @return the created resource.
             */
            Service create();

            /**
             * Executes the create request.
             *
             * @param context The context to associate with this operation.
             * @return the created resource.
             */
            Service create(Context context);
        }
        /** The stage of the Service definition allowing to specify tags. */
        interface WithTags {
            /**
             * Specifies the tags property: Resource tags..
             *
             * @param tags Resource tags.
             * @return the next definition stage.
             */
            WithCreate withTags(Map<String, String> tags);
        }
        /** The stage of the Service definition allowing to specify serviceQosPolicy. */
        interface WithServiceQosPolicy {
            /**
             * Specifies the serviceQosPolicy property: The QoS policy to use for packets matching this service. This
             * can be overridden for particular flows using the ruleQosPolicy field in a PccRuleConfiguration. If this
             * field is null then the UE's simPolicy will define the QoS settings..
             *
             * @param serviceQosPolicy The QoS policy to use for packets matching this service. This can be overridden
             *     for particular flows using the ruleQosPolicy field in a PccRuleConfiguration. If this field is null
             *     then the UE's simPolicy will define the QoS settings.
             * @return the next definition stage.
             */
            WithCreate withServiceQosPolicy(QosPolicy serviceQosPolicy);
        }
    }
    /**
     * Begins update for the Service resource.
     *
     * @return the stage of resource update.
     */
    Service.Update update();

    /** The template for Service update. */
    interface Update extends UpdateStages.WithTags {
        /**
         * Executes the update request.
         *
         * @return the updated resource.
         */
        Service apply();

        /**
         * Executes the update request.
         *
         * @param context The context to associate with this operation.
         * @return the updated resource.
         */
        Service apply(Context context);
    }
    /** The Service update stages. */
    interface UpdateStages {
        /** The stage of the Service update allowing to specify tags. */
        interface WithTags {
            /**
             * Specifies the tags property: Resource tags..
             *
             * @param tags Resource tags.
             * @return the next definition stage.
             */
            Update withTags(Map<String, String> tags);
        }
    }
    /**
     * Refreshes the resource to sync with Azure.
     *
     * @return the refreshed resource.
     */
    Service refresh();

    /**
     * Refreshes the resource to sync with Azure.
     *
     * @param context The context to associate with this operation.
     * @return the refreshed resource.
     */
    Service refresh(Context context);
}
