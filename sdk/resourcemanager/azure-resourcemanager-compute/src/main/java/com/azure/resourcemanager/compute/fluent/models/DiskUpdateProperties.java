// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.compute.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.resourcemanager.compute.models.DataAccessAuthMode;
import com.azure.resourcemanager.compute.models.Encryption;
import com.azure.resourcemanager.compute.models.EncryptionSettingsCollection;
import com.azure.resourcemanager.compute.models.NetworkAccessPolicy;
import com.azure.resourcemanager.compute.models.OperatingSystemTypes;
import com.azure.resourcemanager.compute.models.PropertyUpdatesInProgress;
import com.azure.resourcemanager.compute.models.PublicNetworkAccess;
import com.azure.resourcemanager.compute.models.PurchasePlanAutoGenerated;
import com.azure.resourcemanager.compute.models.SupportedCapabilities;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Disk resource update properties. */
@Fluent
public final class DiskUpdateProperties {
    /*
     * the Operating System type.
     */
    @JsonProperty(value = "osType")
    private OperatingSystemTypes osType;

    /*
     * If creationData.createOption is Empty, this field is mandatory and it
     * indicates the size of the disk to create. If this field is present for
     * updates or creation with other options, it indicates a resize. Resizes
     * are only allowed if the disk is not attached to a running VM, and can
     * only increase the disk's size.
     */
    @JsonProperty(value = "diskSizeGB")
    private Integer diskSizeGB;

    /*
     * Encryption settings collection used be Azure Disk Encryption, can
     * contain multiple encryption settings per disk or snapshot.
     */
    @JsonProperty(value = "encryptionSettingsCollection")
    private EncryptionSettingsCollection encryptionSettingsCollection;

    /*
     * The number of IOPS allowed for this disk; only settable for UltraSSD
     * disks. One operation can transfer between 4k and 256k bytes.
     */
    @JsonProperty(value = "diskIOPSReadWrite")
    private Long diskIopsReadWrite;

    /*
     * The bandwidth allowed for this disk; only settable for UltraSSD disks.
     * MBps means millions of bytes per second - MB here uses the ISO notation,
     * of powers of 10.
     */
    @JsonProperty(value = "diskMBpsReadWrite")
    private Long diskMBpsReadWrite;

    /*
     * The total number of IOPS that will be allowed across all VMs mounting
     * the shared disk as ReadOnly. One operation can transfer between 4k and
     * 256k bytes.
     */
    @JsonProperty(value = "diskIOPSReadOnly")
    private Long diskIopsReadOnly;

    /*
     * The total throughput (MBps) that will be allowed across all VMs mounting
     * the shared disk as ReadOnly. MBps means millions of bytes per second -
     * MB here uses the ISO notation, of powers of 10.
     */
    @JsonProperty(value = "diskMBpsReadOnly")
    private Long diskMBpsReadOnly;

    /*
     * The maximum number of VMs that can attach to the disk at the same time.
     * Value greater than one indicates a disk that can be mounted on multiple
     * VMs at the same time.
     */
    @JsonProperty(value = "maxShares")
    private Integer maxShares;

    /*
     * Encryption property can be used to encrypt data at rest with customer
     * managed keys or platform managed keys.
     */
    @JsonProperty(value = "encryption")
    private Encryption encryption;

    /*
     * Policy for accessing the disk via network.
     */
    @JsonProperty(value = "networkAccessPolicy")
    private NetworkAccessPolicy networkAccessPolicy;

    /*
     * ARM id of the DiskAccess resource for using private endpoints on disks.
     */
    @JsonProperty(value = "diskAccessId")
    private String diskAccessId;

    /*
     * Performance tier of the disk (e.g, P4, S10) as described here:
     * https://azure.microsoft.com/en-us/pricing/details/managed-disks/. Does
     * not apply to Ultra disks.
     */
    @JsonProperty(value = "tier")
    private String tier;

    /*
     * Set to true to enable bursting beyond the provisioned performance target
     * of the disk. Bursting is disabled by default. Does not apply to Ultra
     * disks.
     */
    @JsonProperty(value = "burstingEnabled")
    private Boolean burstingEnabled;

    /*
     * Purchase plan information to be added on the OS disk
     */
    @JsonProperty(value = "purchasePlan")
    private PurchasePlanAutoGenerated purchasePlan;

    /*
     * List of supported capabilities to be added on the OS disk.
     */
    @JsonProperty(value = "supportedCapabilities")
    private SupportedCapabilities supportedCapabilities;

    /*
     * Properties of the disk for which update is pending.
     */
    @JsonProperty(value = "propertyUpdatesInProgress", access = JsonProperty.Access.WRITE_ONLY)
    private PropertyUpdatesInProgress propertyUpdatesInProgress;

    /*
     * Indicates the OS on a disk supports hibernation.
     */
    @JsonProperty(value = "supportsHibernation")
    private Boolean supportsHibernation;

    /*
     * Policy for controlling export on the disk.
     */
    @JsonProperty(value = "publicNetworkAccess")
    private PublicNetworkAccess publicNetworkAccess;

    /*
     * Additional authentication requirements when exporting or uploading to a
     * disk or snapshot.
     */
    @JsonProperty(value = "dataAccessAuthMode")
    private DataAccessAuthMode dataAccessAuthMode;

    /**
     * Get the osType property: the Operating System type.
     *
     * @return the osType value.
     */
    public OperatingSystemTypes osType() {
        return this.osType;
    }

    /**
     * Set the osType property: the Operating System type.
     *
     * @param osType the osType value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withOsType(OperatingSystemTypes osType) {
        this.osType = osType;
        return this;
    }

    /**
     * Get the diskSizeGB property: If creationData.createOption is Empty, this field is mandatory and it indicates the
     * size of the disk to create. If this field is present for updates or creation with other options, it indicates a
     * resize. Resizes are only allowed if the disk is not attached to a running VM, and can only increase the disk's
     * size.
     *
     * @return the diskSizeGB value.
     */
    public Integer diskSizeGB() {
        return this.diskSizeGB;
    }

    /**
     * Set the diskSizeGB property: If creationData.createOption is Empty, this field is mandatory and it indicates the
     * size of the disk to create. If this field is present for updates or creation with other options, it indicates a
     * resize. Resizes are only allowed if the disk is not attached to a running VM, and can only increase the disk's
     * size.
     *
     * @param diskSizeGB the diskSizeGB value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withDiskSizeGB(Integer diskSizeGB) {
        this.diskSizeGB = diskSizeGB;
        return this;
    }

    /**
     * Get the encryptionSettingsCollection property: Encryption settings collection used be Azure Disk Encryption, can
     * contain multiple encryption settings per disk or snapshot.
     *
     * @return the encryptionSettingsCollection value.
     */
    public EncryptionSettingsCollection encryptionSettingsCollection() {
        return this.encryptionSettingsCollection;
    }

    /**
     * Set the encryptionSettingsCollection property: Encryption settings collection used be Azure Disk Encryption, can
     * contain multiple encryption settings per disk or snapshot.
     *
     * @param encryptionSettingsCollection the encryptionSettingsCollection value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withEncryptionSettingsCollection(
        EncryptionSettingsCollection encryptionSettingsCollection) {
        this.encryptionSettingsCollection = encryptionSettingsCollection;
        return this;
    }

    /**
     * Get the diskIopsReadWrite property: The number of IOPS allowed for this disk; only settable for UltraSSD disks.
     * One operation can transfer between 4k and 256k bytes.
     *
     * @return the diskIopsReadWrite value.
     */
    public Long diskIopsReadWrite() {
        return this.diskIopsReadWrite;
    }

    /**
     * Set the diskIopsReadWrite property: The number of IOPS allowed for this disk; only settable for UltraSSD disks.
     * One operation can transfer between 4k and 256k bytes.
     *
     * @param diskIopsReadWrite the diskIopsReadWrite value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withDiskIopsReadWrite(Long diskIopsReadWrite) {
        this.diskIopsReadWrite = diskIopsReadWrite;
        return this;
    }

    /**
     * Get the diskMBpsReadWrite property: The bandwidth allowed for this disk; only settable for UltraSSD disks. MBps
     * means millions of bytes per second - MB here uses the ISO notation, of powers of 10.
     *
     * @return the diskMBpsReadWrite value.
     */
    public Long diskMBpsReadWrite() {
        return this.diskMBpsReadWrite;
    }

    /**
     * Set the diskMBpsReadWrite property: The bandwidth allowed for this disk; only settable for UltraSSD disks. MBps
     * means millions of bytes per second - MB here uses the ISO notation, of powers of 10.
     *
     * @param diskMBpsReadWrite the diskMBpsReadWrite value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withDiskMBpsReadWrite(Long diskMBpsReadWrite) {
        this.diskMBpsReadWrite = diskMBpsReadWrite;
        return this;
    }

    /**
     * Get the diskIopsReadOnly property: The total number of IOPS that will be allowed across all VMs mounting the
     * shared disk as ReadOnly. One operation can transfer between 4k and 256k bytes.
     *
     * @return the diskIopsReadOnly value.
     */
    public Long diskIopsReadOnly() {
        return this.diskIopsReadOnly;
    }

    /**
     * Set the diskIopsReadOnly property: The total number of IOPS that will be allowed across all VMs mounting the
     * shared disk as ReadOnly. One operation can transfer between 4k and 256k bytes.
     *
     * @param diskIopsReadOnly the diskIopsReadOnly value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withDiskIopsReadOnly(Long diskIopsReadOnly) {
        this.diskIopsReadOnly = diskIopsReadOnly;
        return this;
    }

    /**
     * Get the diskMBpsReadOnly property: The total throughput (MBps) that will be allowed across all VMs mounting the
     * shared disk as ReadOnly. MBps means millions of bytes per second - MB here uses the ISO notation, of powers of
     * 10.
     *
     * @return the diskMBpsReadOnly value.
     */
    public Long diskMBpsReadOnly() {
        return this.diskMBpsReadOnly;
    }

    /**
     * Set the diskMBpsReadOnly property: The total throughput (MBps) that will be allowed across all VMs mounting the
     * shared disk as ReadOnly. MBps means millions of bytes per second - MB here uses the ISO notation, of powers of
     * 10.
     *
     * @param diskMBpsReadOnly the diskMBpsReadOnly value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withDiskMBpsReadOnly(Long diskMBpsReadOnly) {
        this.diskMBpsReadOnly = diskMBpsReadOnly;
        return this;
    }

    /**
     * Get the maxShares property: The maximum number of VMs that can attach to the disk at the same time. Value greater
     * than one indicates a disk that can be mounted on multiple VMs at the same time.
     *
     * @return the maxShares value.
     */
    public Integer maxShares() {
        return this.maxShares;
    }

    /**
     * Set the maxShares property: The maximum number of VMs that can attach to the disk at the same time. Value greater
     * than one indicates a disk that can be mounted on multiple VMs at the same time.
     *
     * @param maxShares the maxShares value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withMaxShares(Integer maxShares) {
        this.maxShares = maxShares;
        return this;
    }

    /**
     * Get the encryption property: Encryption property can be used to encrypt data at rest with customer managed keys
     * or platform managed keys.
     *
     * @return the encryption value.
     */
    public Encryption encryption() {
        return this.encryption;
    }

    /**
     * Set the encryption property: Encryption property can be used to encrypt data at rest with customer managed keys
     * or platform managed keys.
     *
     * @param encryption the encryption value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withEncryption(Encryption encryption) {
        this.encryption = encryption;
        return this;
    }

    /**
     * Get the networkAccessPolicy property: Policy for accessing the disk via network.
     *
     * @return the networkAccessPolicy value.
     */
    public NetworkAccessPolicy networkAccessPolicy() {
        return this.networkAccessPolicy;
    }

    /**
     * Set the networkAccessPolicy property: Policy for accessing the disk via network.
     *
     * @param networkAccessPolicy the networkAccessPolicy value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withNetworkAccessPolicy(NetworkAccessPolicy networkAccessPolicy) {
        this.networkAccessPolicy = networkAccessPolicy;
        return this;
    }

    /**
     * Get the diskAccessId property: ARM id of the DiskAccess resource for using private endpoints on disks.
     *
     * @return the diskAccessId value.
     */
    public String diskAccessId() {
        return this.diskAccessId;
    }

    /**
     * Set the diskAccessId property: ARM id of the DiskAccess resource for using private endpoints on disks.
     *
     * @param diskAccessId the diskAccessId value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withDiskAccessId(String diskAccessId) {
        this.diskAccessId = diskAccessId;
        return this;
    }

    /**
     * Get the tier property: Performance tier of the disk (e.g, P4, S10) as described here:
     * https://azure.microsoft.com/en-us/pricing/details/managed-disks/. Does not apply to Ultra disks.
     *
     * @return the tier value.
     */
    public String tier() {
        return this.tier;
    }

    /**
     * Set the tier property: Performance tier of the disk (e.g, P4, S10) as described here:
     * https://azure.microsoft.com/en-us/pricing/details/managed-disks/. Does not apply to Ultra disks.
     *
     * @param tier the tier value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withTier(String tier) {
        this.tier = tier;
        return this;
    }

    /**
     * Get the burstingEnabled property: Set to true to enable bursting beyond the provisioned performance target of the
     * disk. Bursting is disabled by default. Does not apply to Ultra disks.
     *
     * @return the burstingEnabled value.
     */
    public Boolean burstingEnabled() {
        return this.burstingEnabled;
    }

    /**
     * Set the burstingEnabled property: Set to true to enable bursting beyond the provisioned performance target of the
     * disk. Bursting is disabled by default. Does not apply to Ultra disks.
     *
     * @param burstingEnabled the burstingEnabled value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withBurstingEnabled(Boolean burstingEnabled) {
        this.burstingEnabled = burstingEnabled;
        return this;
    }

    /**
     * Get the purchasePlan property: Purchase plan information to be added on the OS disk.
     *
     * @return the purchasePlan value.
     */
    public PurchasePlanAutoGenerated purchasePlan() {
        return this.purchasePlan;
    }

    /**
     * Set the purchasePlan property: Purchase plan information to be added on the OS disk.
     *
     * @param purchasePlan the purchasePlan value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withPurchasePlan(PurchasePlanAutoGenerated purchasePlan) {
        this.purchasePlan = purchasePlan;
        return this;
    }

    /**
     * Get the supportedCapabilities property: List of supported capabilities to be added on the OS disk.
     *
     * @return the supportedCapabilities value.
     */
    public SupportedCapabilities supportedCapabilities() {
        return this.supportedCapabilities;
    }

    /**
     * Set the supportedCapabilities property: List of supported capabilities to be added on the OS disk.
     *
     * @param supportedCapabilities the supportedCapabilities value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withSupportedCapabilities(SupportedCapabilities supportedCapabilities) {
        this.supportedCapabilities = supportedCapabilities;
        return this;
    }

    /**
     * Get the propertyUpdatesInProgress property: Properties of the disk for which update is pending.
     *
     * @return the propertyUpdatesInProgress value.
     */
    public PropertyUpdatesInProgress propertyUpdatesInProgress() {
        return this.propertyUpdatesInProgress;
    }

    /**
     * Get the supportsHibernation property: Indicates the OS on a disk supports hibernation.
     *
     * @return the supportsHibernation value.
     */
    public Boolean supportsHibernation() {
        return this.supportsHibernation;
    }

    /**
     * Set the supportsHibernation property: Indicates the OS on a disk supports hibernation.
     *
     * @param supportsHibernation the supportsHibernation value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withSupportsHibernation(Boolean supportsHibernation) {
        this.supportsHibernation = supportsHibernation;
        return this;
    }

    /**
     * Get the publicNetworkAccess property: Policy for controlling export on the disk.
     *
     * @return the publicNetworkAccess value.
     */
    public PublicNetworkAccess publicNetworkAccess() {
        return this.publicNetworkAccess;
    }

    /**
     * Set the publicNetworkAccess property: Policy for controlling export on the disk.
     *
     * @param publicNetworkAccess the publicNetworkAccess value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withPublicNetworkAccess(PublicNetworkAccess publicNetworkAccess) {
        this.publicNetworkAccess = publicNetworkAccess;
        return this;
    }

    /**
     * Get the dataAccessAuthMode property: Additional authentication requirements when exporting or uploading to a disk
     * or snapshot.
     *
     * @return the dataAccessAuthMode value.
     */
    public DataAccessAuthMode dataAccessAuthMode() {
        return this.dataAccessAuthMode;
    }

    /**
     * Set the dataAccessAuthMode property: Additional authentication requirements when exporting or uploading to a disk
     * or snapshot.
     *
     * @param dataAccessAuthMode the dataAccessAuthMode value to set.
     * @return the DiskUpdateProperties object itself.
     */
    public DiskUpdateProperties withDataAccessAuthMode(DataAccessAuthMode dataAccessAuthMode) {
        this.dataAccessAuthMode = dataAccessAuthMode;
        return this;
    }

    /**
     * Validates the instance.
     *
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (encryptionSettingsCollection() != null) {
            encryptionSettingsCollection().validate();
        }
        if (encryption() != null) {
            encryption().validate();
        }
        if (purchasePlan() != null) {
            purchasePlan().validate();
        }
        if (supportedCapabilities() != null) {
            supportedCapabilities().validate();
        }
        if (propertyUpdatesInProgress() != null) {
            propertyUpdatesInProgress().validate();
        }
    }
}
