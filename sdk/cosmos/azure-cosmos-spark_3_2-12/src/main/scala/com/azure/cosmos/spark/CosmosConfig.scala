// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.cosmos.spark

import com.azure.cosmos.implementation.Strings
import com.azure.cosmos.implementation.routing.LocationHelper
import com.azure.cosmos.models.{CosmosChangeFeedRequestOptions, CosmosParameterizedQuery, FeedRange}
import com.azure.cosmos.spark.ChangeFeedModes.ChangeFeedMode
import com.azure.cosmos.spark.ChangeFeedStartFromModes.{ChangeFeedStartFromMode, PointInTime}
import com.azure.cosmos.spark.CosmosPatchOperationTypes.CosmosPatchOperationTypes
import com.azure.cosmos.spark.CosmosPredicates.{assertNotNullOrEmpty, requireNotNullOrEmpty}
import com.azure.cosmos.spark.ItemWriteStrategy.{ItemWriteStrategy, values}
import com.azure.cosmos.spark.PartitioningStrategies.PartitioningStrategy
import com.azure.cosmos.spark.SchemaConversionModes.SchemaConversionMode
import com.azure.cosmos.spark.SerializationDateTimeConversionModes.SerializationDateTimeConversionMode
import com.azure.cosmos.spark.SerializationInclusionModes.SerializationInclusionMode
import com.azure.cosmos.spark.diagnostics.{DiagnosticsProvider, FeedDiagnosticsProvider, SimpleDiagnosticsProvider}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.catalyst.util.CaseInsensitiveMap
import org.apache.spark.sql.connector.read.streaming.ReadLimit
import org.apache.spark.sql.types.{DataType, NumericType, StructType}

import java.net.{URI, URISyntaxException, URL}
import java.time.format.DateTimeFormatter
import java.time.{Duration, Instant}
import java.util.{Locale, ServiceLoader}
import scala.collection.concurrent.TrieMap
import scala.collection.immutable.{HashSet, Map}
import scala.collection.mutable

// scalastyle:off underscore.import
import scala.collection.JavaConverters._
// scalastyle:on underscore.import

// scalastyle:off multiple.string.literals
// scalastyle:off file.size.limit
// scalastyle:off number.of.types

private[spark] object CosmosConfigNames {
  val AccountEndpoint = "spark.cosmos.accountEndpoint"
  val AccountKey = "spark.cosmos.accountKey"
  val Database = "spark.cosmos.database"
  val Container = "spark.cosmos.container"
  val PreferredRegionsList = "spark.cosmos.preferredRegionsList"
  val PreferredRegions = "spark.cosmos.preferredRegions"
  val DisableTcpConnectionEndpointRediscovery = "spark.cosmos.disableTcpConnectionEndpointRediscovery"
  val ApplicationName = "spark.cosmos.applicationName"
  val UseGatewayMode = "spark.cosmos.useGatewayMode"
  val ReadCustomQuery = "spark.cosmos.read.customQuery"
  val ReadMaxItemCount = "spark.cosmos.read.maxItemCount"
  val ReadPrefetchBufferSize = "spark.cosmos.read.prefetchBufferSize"
  val ReadForceEventualConsistency = "spark.cosmos.read.forceEventualConsistency"
  val ReadSchemaConversionMode = "spark.cosmos.read.schemaConversionMode"
  val ReadInferSchemaSamplingSize = "spark.cosmos.read.inferSchema.samplingSize"
  val ReadInferSchemaEnabled = "spark.cosmos.read.inferSchema.enabled"
  val ReadInferSchemaIncludeSystemProperties = "spark.cosmos.read.inferSchema.includeSystemProperties"
  val ReadInferSchemaForceNullableProperties = "spark.cosmos.read.inferSchema.forceNullableProperties"
  val ReadInferSchemaIncludeTimestamp = "spark.cosmos.read.inferSchema.includeTimestamp"
  val ReadInferSchemaQuery = "spark.cosmos.read.inferSchema.query"
  val ReadPartitioningStrategy = "spark.cosmos.read.partitioning.strategy"
  val ReadPartitioningTargetedCount = "spark.cosmos.partitioning.targetedCount"
  val ReadPartitioningFeedRangeFilter = "spark.cosmos.partitioning.feedRangeFilter"
  val ViewsRepositoryPath = "spark.cosmos.views.repositoryPath"
  val DiagnosticsMode = "spark.cosmos.diagnostics"
  val ClientTelemetryEnabled = "spark.cosmos.clientTelemetry.enabled"
  val ClientTelemetryEndpoint = "spark.cosmos.clientTelemetry.endpoint"
  val WriteBulkEnabled = "spark.cosmos.write.bulk.enabled"
  val WriteBulkMaxPendingOperations = "spark.cosmos.write.bulk.maxPendingOperations"
  val WriteBulkMaxConcurrentPartitions = "spark.cosmos.write.bulk.maxConcurrentCosmosPartitions"
  val WritePointMaxConcurrency = "spark.cosmos.write.point.maxConcurrency"
  val WritePatchDefaultOperationType = "spark.cosmos.write.patch.defaultOperationType"
  val WritePatchColumnConfigs = "spark.cosmos.write.patch.columnConfigs"
  val WritePatchFilterPredicate = "spark.cosmos.write.patch.filter"
  val WriteStrategy = "spark.cosmos.write.strategy"
  val WriteMaxRetryCount = "spark.cosmos.write.maxRetryCount"
  val ChangeFeedStartFrom = "spark.cosmos.changeFeed.startFrom"
  val ChangeFeedMode = "spark.cosmos.changeFeed.mode"
  val ChangeFeedItemCountPerTriggerHint = "spark.cosmos.changeFeed.itemCountPerTriggerHint"
  val ThroughputControlEnabled = "spark.cosmos.throughputControl.enabled"
  val ThroughputControlName = "spark.cosmos.throughputControl.name"
  val ThroughputControlTargetThroughput = "spark.cosmos.throughputControl.targetThroughput"
  val ThroughputControlTargetThroughputThreshold = "spark.cosmos.throughputControl.targetThroughputThreshold"
  val ThroughputControlGlobalControlDatabase = "spark.cosmos.throughputControl.globalControl.database"
  val ThroughputControlGlobalControlContainer = "spark.cosmos.throughputControl.globalControl.container"
  val ThroughputControlGlobalControlRenewalIntervalInMS =
    "spark.cosmos.throughputControl.globalControl.renewIntervalInMS"
  val ThroughputControlGlobalControlExpireIntervalInMS =
    "spark.cosmos.throughputControl.globalControl.expireIntervalInMS"
  val SerializationInclusionMode =
    "spark.cosmos.serialization.inclusionMode"
  val SerializationDateTimeConversionMode =
    "spark.cosmos.serialization.dateTimeConversionMode"

  private val cosmosPrefix = "spark.cosmos."

  private val validConfigNames: Set[String] = HashSet[String](
    AccountEndpoint,
    AccountKey,
    Database,
    Container,
    PreferredRegionsList,
    PreferredRegions,
    DisableTcpConnectionEndpointRediscovery,
    ApplicationName,
    UseGatewayMode,
    ReadCustomQuery,
    ReadForceEventualConsistency,
    ReadSchemaConversionMode,
    ReadMaxItemCount,
    ReadPrefetchBufferSize,
    ReadInferSchemaSamplingSize,
    ReadInferSchemaEnabled,
    ReadInferSchemaIncludeSystemProperties,
    ReadInferSchemaForceNullableProperties,
    ReadInferSchemaIncludeTimestamp,
    ReadInferSchemaQuery,
    ReadPartitioningStrategy,
    ReadPartitioningTargetedCount,
    ReadPartitioningFeedRangeFilter,
    ViewsRepositoryPath,
    DiagnosticsMode,
    ClientTelemetryEnabled,
    ClientTelemetryEndpoint,
    WriteBulkEnabled,
    WriteBulkMaxPendingOperations,
    WriteBulkMaxConcurrentPartitions,
    WritePointMaxConcurrency,
    WritePatchDefaultOperationType,
    WritePatchColumnConfigs,
    WritePatchFilterPredicate,
    WriteStrategy,
    WriteMaxRetryCount,
    ChangeFeedStartFrom,
    ChangeFeedMode,
    ChangeFeedItemCountPerTriggerHint,
    ThroughputControlEnabled,
    ThroughputControlName,
    ThroughputControlTargetThroughput,
    ThroughputControlTargetThroughputThreshold,
    ThroughputControlGlobalControlDatabase,
    ThroughputControlGlobalControlContainer,
    ThroughputControlGlobalControlRenewalIntervalInMS,
    ThroughputControlGlobalControlExpireIntervalInMS,
    SerializationInclusionMode,
    SerializationDateTimeConversionMode
  )

  def validateConfigName(name: String): Unit = {
    if (name != null &&
      name.length > cosmosPrefix.length &&
      cosmosPrefix.equalsIgnoreCase(name.substring(0, cosmosPrefix.length))) {

      if (validConfigNames.find(n => name.equalsIgnoreCase(n)).isEmpty) {
        throw new IllegalArgumentException(
          s"The config property '$name' is invalid. No config setting with this name exists.")
      }
    }
  }
}

private object CosmosConfig {
  def getEffectiveConfig
  (
    databaseName: Option[String],
    containerName: Option[String],
    sparkConf: Option[SparkConf],
    // spark application configteams
    userProvidedOptions: Map[String, String] // user provided config
  ) : Map[String, String] = {
    var accountDataResolverCls = None : Option[AccountDataResolver]
    val serviceLoader = ServiceLoader.load(classOf[AccountDataResolver])
    val iterator = serviceLoader.iterator()
    if (iterator.hasNext()) {
        accountDataResolverCls = Some(iterator.next())
    }

    var effectiveUserConfig = CaseInsensitiveMap(userProvidedOptions)
    if (accountDataResolverCls.isDefined) {
        val accountDataConfig = accountDataResolverCls.get.getAccountDataConfig(effectiveUserConfig)
        effectiveUserConfig = CaseInsensitiveMap(accountDataConfig)
    }

    if (databaseName.isDefined) {
      effectiveUserConfig += (CosmosContainerConfig.DATABASE_NAME_KEY -> databaseName.get)
    }

    if (containerName.isDefined) {
      effectiveUserConfig += (CosmosContainerConfig.CONTAINER_NAME_KEY -> containerName.get)
    }

    val returnValue = sparkConf match {
      case Some(sparkConfig) => {
        val conf = sparkConf.get.clone()
        conf.setAll(effectiveUserConfig.toMap).getAll.toMap
      }
      case None => effectiveUserConfig.toMap
    }

    returnValue.foreach((configProperty) => CosmosConfigNames.validateConfigName(configProperty._1))

    returnValue
  }

  @throws[IllegalStateException] // if there is no active spark session
  def getEffectiveConfig
  (
    databaseName: Option[String],
    containerName: Option[String],
    userProvidedOptions: Map[String, String] = Map().empty
  ) : Map[String, String] = {

    val session = SparkSession.active

    // TODO: moderakh we should investigate how spark sql config should be merged:
    // TODO: session.conf.getAll, // spark sql runtime config
    getEffectiveConfig(
      databaseName,
      containerName,
      Some(session.sparkContext.getConf), // spark application config
      userProvidedOptions) // user provided config
  }

  def getEffectiveConfigIgnoringSessionConfig
  (
    databaseName: Option[String],
    containerName: Option[String],
    userProvidedOptions: Map[String, String] = Map().empty
  ) : Map[String, String] = {

    // TODO: moderakh we should investigate how spark sql config should be merged:
    // TODO: session.conf.getAll, // spark sql runtime config
    getEffectiveConfig(
      databaseName,
      containerName,
      None,
      userProvidedOptions) // user provided config
  }
}

private case class CosmosAccountConfig(endpoint: String,
                                       key: String,
                                       accountName: String,
                                       applicationName:
                                       Option[String],
                                       useGatewayMode: Boolean,
                                       disableTcpConnectionEndpointRediscovery: Boolean,
                                       preferredRegionsList: Option[Array[String]])

private object CosmosAccountConfig {
  private val CosmosAccountEndpointUri = CosmosConfigEntry[String](key = CosmosConfigNames.AccountEndpoint,
    mandatory = true,
    parseFromStringFunction = accountEndpointUri => {
      new URL(accountEndpointUri)
      accountEndpointUri
    },
    helpMessage = "Cosmos DB Account Endpoint Uri")

  private val CosmosKey = CosmosConfigEntry[String](key = CosmosConfigNames.AccountKey,
    mandatory = true,
    parseFromStringFunction = accountKey => accountKey,
    helpMessage = "Cosmos DB Account Key")

  private val CosmosAccountName = CosmosConfigEntry[String](key = CosmosConfigNames.AccountEndpoint,
    mandatory = true,
    parseFromStringFunction = accountEndpointUri => {
      val url = new URL(accountEndpointUri)
      val separatorIndex = url.getHost.indexOf('.')
      if (separatorIndex > 0) {
          url.getHost.substring(0, separatorIndex)
      } else {
        url.getHost
      }
    },
    helpMessage = "Cosmos DB Account Name")


  private val PreferredRegionRegex = "^[a-z0-9\\d]+(?: [a-z0-9\\d]+)*$".r
  private val PreferredRegionsList = CosmosConfigEntry[Array[String]](key = CosmosConfigNames.PreferredRegionsList,
    Option.apply(CosmosConfigNames.PreferredRegions),
    mandatory = false,
    parseFromStringFunction = preferredRegionsListAsString => {
      var trimmedInput = preferredRegionsListAsString.trim
      if (trimmedInput.startsWith("[") && trimmedInput.endsWith("]")) {
        trimmedInput = trimmedInput.substring(1, trimmedInput.length -1).trim
      }

      if (trimmedInput == "") {
        Array[String]()
      } else {
        trimmedInput.split(",")
          .toStream
          .map(preferredRegion => preferredRegion.toLowerCase(Locale.ROOT).trim)
          .map(preferredRegion => {
            if (!PreferredRegionRegex.findFirstIn(preferredRegion).isDefined) {
              throw new IllegalArgumentException(s"$preferredRegionsListAsString is invalid")
            }
            preferredRegion
          })
          .toArray
      }
    },
    helpMessage = "Preferred Region List")

  private val ApplicationName = CosmosConfigEntry[String](key = CosmosConfigNames.ApplicationName,
    mandatory = false,
    parseFromStringFunction = applicationName => applicationName,
    helpMessage = "Application name")

  private val UseGatewayMode = CosmosConfigEntry[Boolean](key = CosmosConfigNames.UseGatewayMode,
    mandatory = false,
    defaultValue = Some(false),
    parseFromStringFunction = useGatewayMode => useGatewayMode.toBoolean,
    helpMessage = "Use gateway mode for the client operations")

  private val DisableTcpConnectionEndpointRediscovery =
    CosmosConfigEntry[Boolean](
      key = CosmosConfigNames.DisableTcpConnectionEndpointRediscovery,
      mandatory = false,
      defaultValue = Some(false),
      parseFromStringFunction = disableEndpointRediscovery => disableEndpointRediscovery.toBoolean,
      helpMessage = "Disables TCP connection endpoint rediscovery. TCP connection endpoint " +
        "rediscovery should only be disabled when using custom domain names with private endpoints"
    )

  def parseCosmosAccountConfig(cfg: Map[String, String]): CosmosAccountConfig = {
    val endpointOpt = CosmosConfigEntry.parse(cfg, CosmosAccountEndpointUri)
    val key = CosmosConfigEntry.parse(cfg, CosmosKey)
    val accountName = CosmosConfigEntry.parse(cfg, CosmosAccountName)
    val applicationName = CosmosConfigEntry.parse(cfg, ApplicationName)
    val useGatewayMode = CosmosConfigEntry.parse(cfg, UseGatewayMode)
    val disableTcpConnectionEndpointRediscovery = CosmosConfigEntry.parse(cfg, DisableTcpConnectionEndpointRediscovery)
    val preferredRegionsListOpt = CosmosConfigEntry.parse(cfg, PreferredRegionsList)

    // parsing above already validated these assertions
    assert(endpointOpt.isDefined)
    assert(key.isDefined)
    assert(accountName.isDefined)

    if (preferredRegionsListOpt.isDefined) {
      // scalastyle:off null
      var uri : URI = null
      // scalastyle:on null
      try uri = new URI(endpointOpt.get)
      catch {
        case e: URISyntaxException =>
          throw new IllegalArgumentException("invalid serviceEndpoint", e)
      }

      val preferredRegions = preferredRegionsListOpt.get
      preferredRegions.toStream.foreach(preferredRegion => {
        try {
          // validates each preferred region
          LocationHelper.getLocationEndpoint(uri, preferredRegion)
        } catch {
          case e: Exception => throw new IllegalArgumentException(s"Invalid preferred region $preferredRegion")
        }
      })
    }

    CosmosAccountConfig(
      endpointOpt.get,
      key.get,
      accountName.get,
      applicationName,
      useGatewayMode.get,
      disableTcpConnectionEndpointRediscovery.get,
      preferredRegionsListOpt)
  }
}

private case class CosmosReadConfig(forceEventualConsistency: Boolean,
                                    schemaConversionMode: SchemaConversionMode,
                                    maxItemCount: Int,
                                    prefetchBufferSize: Int,
                                    customQuery: Option[CosmosParameterizedQuery])

private object SchemaConversionModes extends Enumeration {
  type SchemaConversionMode = Value

  val Relaxed: SchemaConversionModes.Value = Value("Relaxed")
  val Strict: SchemaConversionModes.Value = Value("Strict")
}

private object CosmosReadConfig {
  private val DefaultSchemaConversionMode: SchemaConversionMode = SchemaConversionModes.Relaxed
  private val DefaultMaxItemCount : Int = 1000

  private val ForceEventualConsistency = CosmosConfigEntry[Boolean](key = CosmosConfigNames.ReadForceEventualConsistency,
    mandatory = false,
    defaultValue = Some(true),
    parseFromStringFunction = value => value.toBoolean,
    helpMessage = "Makes the client use Eventual consistency for read operations")

  private val JsonSchemaConversion = CosmosConfigEntry[SchemaConversionMode](
    key = CosmosConfigNames.ReadSchemaConversionMode,
    mandatory = false,
    defaultValue = Some(DefaultSchemaConversionMode),
    parseFromStringFunction = value => CosmosConfigEntry.parseEnumeration(value, SchemaConversionModes),
    helpMessage = "The schema conversion behavior (`Relaxed`, `Strict`)." +
      " When reading json documents, if a document contains an attribute that does not map to the schema type," +
      " the user can decide whether to use a `null` value (Relaxed) or an exception (Strict).")

  private val CustomQuery = CosmosConfigEntry[CosmosParameterizedQuery](
    key = CosmosConfigNames.ReadCustomQuery,
    mandatory = false,
    defaultValue = None,
    parseFromStringFunction = queryText => CosmosParameterizedQuery(queryText, List.empty[String], List.empty[Any]),
    helpMessage = "When provided the custom query will be processed against the Cosmos endpoint instead " +
      "of dynamically generating the query via predicate push down. Usually it is recommended to rely " +
      "on Spark's predicate push down because that will allow to generate the most efficient set of filters " +
      "based on the query plan. But there are a couple of of predicates like aggregates (count, group by, avg, sum " +
      "etc.) that cannot be pushed down yet (at least in Spark 3.1) - so the custom query is a fallback to allow " +
      "them to be pushed into the query sent to Cosmos.")

  private val MaxItemCount = CosmosConfigEntry[Int](
    key = CosmosConfigNames.ReadMaxItemCount,
    mandatory = false,
    defaultValue = None,
    parseFromStringFunction = queryText => queryText.toInt,
    helpMessage = "The maximum number of documents returned in a single request. The default is 1000.")

  private val PrefetchBufferSize = CosmosConfigEntry[Int](
    key = CosmosConfigNames.ReadPrefetchBufferSize,
    mandatory = false,
    defaultValue = None,
    parseFromStringFunction = queryText => queryText.toInt,
    helpMessage = "The prefetch buffer size - this limits the number of pages (max. 5 MB per page) that are " +
      s"prefetched from the Cosmos DB Service. The default is `1` if the '${CosmosConfigNames.ReadMaxItemCount}' " +
      "parameter is specified and larger than `1000`, or `8` otherwise. If the provided value is not `1` internally " +
      "`reactor.util.concurrent.Queues` will round it to the maximum of 8 and the next power of two. " +
      "Examples: (1 -> 1), (2 -> 8), (3 -> 8), (8 -> 8), (9 -> 16), (31 -> 32), (33 -> 64) - " +
      "See `reactor.util.concurrent.Queues.get(int)` for more details. This means by the max. memory used for " +
      "buffering is 5 MB multiplied by the effective prefetch buffer size for each Executor/CPU-Core.")

  def parseCosmosReadConfig(cfg: Map[String, String]): CosmosReadConfig = {
    val forceEventualConsistency = CosmosConfigEntry.parse(cfg, ForceEventualConsistency)
    val jsonSchemaConversionMode = CosmosConfigEntry.parse(cfg, JsonSchemaConversion)
    val customQuery = CosmosConfigEntry.parse(cfg, CustomQuery)
    val maxItemCount = CosmosConfigEntry.parse(cfg, MaxItemCount)
    val prefetchBufferSize = CosmosConfigEntry.parse(cfg, PrefetchBufferSize)

    CosmosReadConfig(
      forceEventualConsistency.get,
      jsonSchemaConversionMode.get,
      maxItemCount.getOrElse(DefaultMaxItemCount),
      prefetchBufferSize.getOrElse(
        maxItemCount match {
          case Some(itemCountProvidedByUser) => if (itemCountProvidedByUser > DefaultMaxItemCount) {
              1
            } else {
              // Smallest possible number > 1 in Queues.get (2-7 will be rounded to 8)
              CosmosConstants.smallestPossibleReactorQueueSizeLargerThanOne
            }
          case None => 8
        }
      ),
      customQuery)
  }
}

private case class CosmosViewRepositoryConfig(metaDataPath: Option[String])

private object CosmosViewRepositoryConfig {
  val MetaDataPathKeyName = CosmosConfigNames.ViewsRepositoryPath
  val IsCosmosViewKeyName = "isCosmosView"
  private val MetaDataPath = CosmosConfigEntry[String](key = MetaDataPathKeyName,
    mandatory = false,
    defaultValue = None,
    parseFromStringFunction = value => value,
    helpMessage = "The path to the hive metadata store used to store the view definitions")

  private val IsCosmosView = CosmosConfigEntry[Boolean](key = "isCosmosView",
    mandatory = false,
    defaultValue = Some(false),
    parseFromStringFunction = value => value.toBoolean,
    helpMessage = "Identifies that a new Catalog table is getting added for a view - not as a physical container")

  def parseCosmosViewRepositoryConfig(cfg: Map[String, String]): CosmosViewRepositoryConfig = {
    val metaDataPath = CosmosConfigEntry.parse(cfg, MetaDataPath)

    CosmosViewRepositoryConfig(metaDataPath)
  }

  def isCosmosView(cfg: Map[String, String]): Boolean = {
    val isView = CosmosConfigEntry.parse(cfg, IsCosmosView).getOrElse(false)

    if (!isView &&
        CosmosConfigEntry.parse(cfg, CosmosContainerConfig.optionalContainerNameSupplier).isDefined) {

      throw new IllegalArgumentException(
        s"Table property '$IsCosmosViewKeyName' must be set to 'True' when defining a Cosmos view.")
    }

    isView
  }
}

private[cosmos] case class CosmosContainerConfig(database: String, container: String)

private[spark] case class DiagnosticsConfig
(
  mode: Option[String],
  isClientTelemetryEnabled: Boolean,
  clientTelemetryEndpoint: Option[String]
)

private[spark] object DiagnosticsConfig {
  private val diagnosticsMode = CosmosConfigEntry[String](key = CosmosConfigNames.DiagnosticsMode,
    mandatory = false,
    parseFromStringFunction = diagnostics => {
      if (diagnostics == "simple") {
        classOf[SimpleDiagnosticsProvider].getName
      } else if (diagnostics == "feed") {
        classOf[FeedDiagnosticsProvider].getName
      } else {
        // this is experimental and to be used by cosmos db dev engineers.
        Class.forName(diagnostics).asSubclass(classOf[DiagnosticsProvider]).getDeclaredConstructor()
        diagnostics
      }
    },
    helpMessage = "Cosmos DB Spark Diagnostics, supported values 'simple' and 'feed'")

  private val isClientTelemetryEnabled = CosmosConfigEntry[Boolean](key = CosmosConfigNames.ClientTelemetryEnabled,
    mandatory = false,
    defaultValue = Some(false),
    parseFromStringFunction = value => value.toBoolean,
    helpMessage = "Enables Client Telemetry - NOTE: This is a preview feature - and only " +
      "works with public endpoints right now")

  private val clientTelemetryEndpoint = CosmosConfigEntry[String](key = CosmosConfigNames.ClientTelemetryEndpoint,
    mandatory = false,
    defaultValue = None,
    parseFromStringFunction = value => value,
    helpMessage = "Enables Client Telemetry to be sent to the service endpoint provided - " +
      "NOTE: This is a preview feature - and only " +
      "works with public endpoints right now")

  def parseDiagnosticsConfig(cfg: Map[String, String]): DiagnosticsConfig = {
    val diagnosticsModeOpt = CosmosConfigEntry.parse(cfg, diagnosticsMode)
    val isClientTelemetryEnabledOpt = CosmosConfigEntry.parse(cfg, isClientTelemetryEnabled)
    val clientTelemetryEndpointOpt = CosmosConfigEntry.parse(cfg, clientTelemetryEndpoint)
    DiagnosticsConfig(
      diagnosticsModeOpt,
      isClientTelemetryEnabledOpt.getOrElse(false),
      clientTelemetryEndpointOpt)
  }
}


private object ItemWriteStrategy extends Enumeration {
  type ItemWriteStrategy = Value
  val ItemOverwrite, ItemAppend, ItemDelete, ItemDeleteIfNotModified, ItemOverwriteIfNotModified, ItemPatch = Value
}

private object CosmosPatchOperationTypes extends Enumeration {
  type CosmosPatchOperationTypes = Value

  val None = Value("none")
  val Add = Value("add")
  val Set = Value("set")
  val Replace = Value("replace")
  val Remove = Value("remove")
  val Increment = Value("increment")
}

private case class CosmosPatchColumnConfig(columnName: String,
                                           operationType: CosmosPatchOperationTypes,
                                           mappingPath: String)

private case class CosmosPatchConfigs(columnConfigsMap: TrieMap[String, CosmosPatchColumnConfig],
                                      filter: Option[String] = None)

private case class CosmosWriteConfig(itemWriteStrategy: ItemWriteStrategy,
                                     maxRetryCount: Int,
                                     bulkEnabled: Boolean,
                                     bulkMaxPendingOperations: Option[Int] = None,
                                     pointMaxConcurrency: Option[Int] = None,
                                     maxConcurrentCosmosPartitions: Option[Int] = None,
                                     patchConfigs: Option[CosmosPatchConfigs] = None)

private object CosmosWriteConfig {
  private val DefaultMaxRetryCount = 10
  private val DefaultPatchOperationType = CosmosPatchOperationTypes.Replace

  private val bulkEnabled = CosmosConfigEntry[Boolean](key = CosmosConfigNames.WriteBulkEnabled,
    defaultValue = Option.apply(true),
    mandatory = false,
    parseFromStringFunction = bulkEnabledAsString => bulkEnabledAsString.toBoolean,
    helpMessage = "Cosmos DB Item Write bulk enabled")

  private val bulkMaxPendingOperations = CosmosConfigEntry[Int](key = CosmosConfigNames.WriteBulkMaxPendingOperations,
    mandatory = false,
    parseFromStringFunction = bulkMaxConcurrencyAsString => bulkMaxConcurrencyAsString.toInt,
    helpMessage = s"Cosmos DB Item Write Max Pending Operations." +
      s" If not specified it will be determined based on the Spark executor VM Size")

  private val bulkMaxConcurrentPartitions = CosmosConfigEntry[Int](
    key = CosmosConfigNames.WriteBulkMaxConcurrentPartitions,
    mandatory = false,
    parseFromStringFunction = bulkMaxConcurrencyAsString => bulkMaxConcurrencyAsString.toInt,
    helpMessage = s"Cosmos DB Item Write Max Concurrent Cosmos Partitions." +
      s" If not specified it will be determined based on the number of the container's physical partitions -" +
      s" which would indicate every Spark partition is expected to have data from all Cosmos physical partitions." +
      s" If specified it indicates from at most how many Cosmos Physical Partitions each Spark partition contains" +
      s" data. So this config can be used to make bulk processing more efficient when input data in Spark has been" +
      s" repartitioned to balance to how many Cosmos partitions each Spark partition needs to write. This is mainly" +
      s" useful for very large containers (with hundreds of physical partitions).")

  private val pointWriteConcurrency = CosmosConfigEntry[Int](key = CosmosConfigNames.WritePointMaxConcurrency,
    mandatory = false,
    parseFromStringFunction = bulkMaxConcurrencyAsString => bulkMaxConcurrencyAsString.toInt,
    helpMessage = s"Cosmos DB Item Write Max concurrency." +
      s" If not specified it will be determined based on the Spark executor VM Size")

  private val itemWriteStrategy = CosmosConfigEntry[ItemWriteStrategy](key = CosmosConfigNames.WriteStrategy,
    defaultValue = Option.apply(ItemWriteStrategy.ItemOverwrite),
    mandatory = false,
    parseFromStringFunction = itemWriteStrategyAsString =>
      CosmosConfigEntry.parseEnumeration(itemWriteStrategyAsString, ItemWriteStrategy),
    helpMessage = "Cosmos DB Item write Strategy: `ItemOverwrite` (using upsert), `ItemAppend` (using create, " +
      "ignore pre-existing items i.e., Conflicts), `ItemDelete` (deletes based on id/pk of data frame), " +
      "`ItemDeleteIfNotModified` (deletes based on id/pk of data frame if etag hasn't changed since collecting " +
      "id/pk), `ItemOverwriteIfNotModified` (using create if etag is empty, update/replace with etag pre-condition " +
      "otherwise, if document was updated the pre-condition failure is ignored)")

  private val maxRetryCount = CosmosConfigEntry[Int](key = CosmosConfigNames.WriteMaxRetryCount,
    mandatory = false,
    defaultValue = Option.apply(DefaultMaxRetryCount),
    parseFromStringFunction = maxRetryAttempt => {
      val cnt = maxRetryAttempt.toInt
      if (cnt < 0) {
        throw new IllegalArgumentException(s"expected a non-negative number")
      }
      cnt
    },
    helpMessage = "Cosmos DB Write Max Retry Attempts on failure")

  private val patchDefaultOperationType = CosmosConfigEntry[CosmosPatchOperationTypes](key = CosmosConfigNames.WritePatchDefaultOperationType,
    mandatory = false,
    defaultValue = Option.apply(DefaultPatchOperationType),
    parseFromStringFunction = defaultOperationTypeString => CosmosConfigEntry.parseEnumeration(defaultOperationTypeString, CosmosPatchOperationTypes),
    helpMessage = "Default Cosmos DB patch operation type. By default using replace operation type. " +
     "Supported ones include none, add, set, replace, remove, increment." +
     "Choose none for no-op, for others please reference here for full context:" +
     "https://docs.microsoft.com/en-us/azure/cosmos-db/partial-document-update#supported-operations")

  private val patchColumnConfigs = CosmosConfigEntry[TrieMap[String, CosmosPatchColumnConfig]](key = CosmosConfigNames.WritePatchColumnConfigs,
    mandatory = false,
    parseFromStringFunction = columnConfigsString => parseUserDefinedPatchColumnConfigs(columnConfigsString),
    helpMessage = "Cosmos DB patch column configs. It can container multiple definitions matching the following patterns separated by comma." +
     "1. col(column).op(operationType) - each column can have its own operation type. Supported ones include none, add, set, replace, remove, increment. " +
     "Use none for no-op, for others, please reference here for full context: https://docs.microsoft.com/en-us/azure/cosmos-db/partial-document-update#supported-operations. " +
     "2. col(column).path(patchInCosmosdb).op(operationType) - compared to patten 1, the difference is it also let you define the mapped cosmosdb path.")

  private val patchFilterPredicate = CosmosConfigEntry[String](key = CosmosConfigNames.WritePatchFilterPredicate,
    mandatory = false,
    parseFromStringFunction = filterPredicateString => filterPredicateString,
    helpMessage = "Used for conditional patch. Please see examples here: " +
     "https://docs.microsoft.com/en-us/azure/cosmos-db/partial-document-update-getting-started#java")

  def parseUserDefinedPatchColumnConfigs(patchColumnConfigsString: String): TrieMap[String, CosmosPatchColumnConfig] = {
    val columnConfigMap = new TrieMap[String, CosmosPatchColumnConfig]

    if (patchColumnConfigsString.isEmpty) {
      columnConfigMap
    } else {
      var trimmedInput = patchColumnConfigsString.trim
      if (trimmedInput.startsWith("[") && trimmedInput.endsWith("]")) {
        trimmedInput = trimmedInput.substring(1, trimmedInput.length -1).trim
      }

      if (trimmedInput == "") {
        columnConfigMap
      } else {
        trimmedInput.split(",")
         .foreach(item => {
           val columnConfigString = item.trim

           if (!columnConfigString.isEmpty) {
             // Currently there are two patterns which are valid
             // 1. col(column).op(operationType)
             // 2. col(column).path(mappedPath).op(operationType)
             //
             // (?i) : The whole matching is case-insensitive
             // col[(](.*?)[)]: column name match
             // ([.]path[(](.*)[)])*: mapping path match, it is optional
             // [.]op[(](.*)[)]: patch operation mapping
             val operationConfigaRegx = """(?i)col[(](.*?)[)]([.]path[(](.*)[)])*[.]op[(](.*)[)]$""".r
             columnConfigString match {
               case operationConfigaRegx(columnName, _, path, operationTypeString) =>
                 assertNotNullOrEmpty(columnName, "columnName")
                 assertNotNullOrEmpty(operationTypeString, "operationTypeString")

                 // if customer defined the mapping path, then use it as it is, else by default use the columnName
                 var mappingPath = path
                 if (Strings.isNullOrWhiteSpace(mappingPath)) {
                   // if there is no path defined, by default use the column name
                   mappingPath = s"/$columnName"
                 }

                 val columnConfig =
                   CosmosPatchColumnConfig(
                     columnName = columnName,
                     operationType = CosmosConfigEntry.parseEnumeration(operationTypeString, CosmosPatchOperationTypes),
                     mappingPath = mappingPath)

                 columnConfigMap += (columnConfigMap.get(columnName) match {
                   case Some(_: CosmosPatchColumnConfig) => throw new IllegalStateException(s"Duplicate config for the same column $columnName")
                   case None => columnName -> columnConfig
                 })
             }
           }
         })

        columnConfigMap
      }
    }
  }

  def parseWriteConfig(cfg: Map[String, String], inputSchema: StructType): CosmosWriteConfig = {
    val itemWriteStrategyOpt = CosmosConfigEntry.parse(cfg, itemWriteStrategy)
    val maxRetryCountOpt = CosmosConfigEntry.parse(cfg, maxRetryCount)
    val bulkEnabledOpt = CosmosConfigEntry.parse(cfg, bulkEnabled)
    var patchConfigsOpt = Option.empty[CosmosPatchConfigs]

    assert(bulkEnabledOpt.isDefined)

    // parsing above already validated this
    assert(itemWriteStrategyOpt.isDefined)
    assert(maxRetryCountOpt.isDefined)
    assert(bulkEnabledOpt.isDefined)

    itemWriteStrategyOpt.get match {
      case ItemWriteStrategy.ItemPatch =>
        val patchColumnConfigMap = parsePatchColumnConfigs(cfg, inputSchema)
        val patchFilter = CosmosConfigEntry.parse(cfg, patchFilterPredicate)
        patchConfigsOpt = Some(CosmosPatchConfigs(patchColumnConfigMap, patchFilter))
      case _ =>
    }

    CosmosWriteConfig(
      itemWriteStrategyOpt.get,
      maxRetryCountOpt.get,
      bulkEnabled = bulkEnabledOpt.get,
      bulkMaxPendingOperations = CosmosConfigEntry.parse(cfg, bulkMaxPendingOperations),
      pointMaxConcurrency = CosmosConfigEntry.parse(cfg, pointWriteConcurrency),
      maxConcurrentCosmosPartitions = CosmosConfigEntry.parse(cfg, bulkMaxConcurrentPartitions),
      patchConfigs = patchConfigsOpt)
  }

  def parsePatchColumnConfigs(cfg: Map[String, String], inputSchema: StructType): TrieMap[String, CosmosPatchColumnConfig] = {
    val defaultPatchOperationType = CosmosConfigEntry.parse(cfg, patchDefaultOperationType)

    // Parse customer specified column configs, which will override the default config
    val userDefinedPatchColumnConfigMapOpt = CosmosConfigEntry.parse(cfg, patchColumnConfigs)
    val userDefinedPatchColumnConfigMap = userDefinedPatchColumnConfigMapOpt.getOrElse(new TrieMap[String, CosmosPatchColumnConfig])
    val aggregatedPatchColumnConfigMap = new TrieMap[String, CosmosPatchColumnConfig]

    // based on the schema, trying to find any user defined config or create one based on the default config
    inputSchema.fields.foreach(schemaField => {
      userDefinedPatchColumnConfigMap.get(schemaField.name) match {
        case Some(columnConfig) =>
          aggregatedPatchColumnConfigMap += schemaField.name -> validatePatchColumnConfig(columnConfig, schemaField.dataType)
          userDefinedPatchColumnConfigMap.remove(schemaField.name)
        case None =>
          // There is no customer specified column config, create one based on the default config
          val newColumnConfig = CosmosPatchColumnConfig(schemaField.name, defaultPatchOperationType.get, s"/${schemaField.name}")
          aggregatedPatchColumnConfigMap += schemaField.name -> validatePatchColumnConfig(newColumnConfig, schemaField.dataType)
      }
    })

    // Check any left entries in userDefinedPatchColumnConfigMap
    // If it is not empty, then it means there are column configs contains config for column does not exists in the schema
    // For add, set, replace and increment, throw exception
    userDefinedPatchColumnConfigMap.foreach(entry => {
      entry._2.operationType match {
        case CosmosPatchOperationTypes.None | CosmosPatchOperationTypes.Remove =>
          aggregatedPatchColumnConfigMap += entry._1 -> entry._2
        case _ =>
          throw new IllegalArgumentException(s"Invalid column config. Column ${entry._1} does not exist in schema")
      }
    })

    aggregatedPatchColumnConfigMap
  }

  def validatePatchColumnConfig(cosmosPatchColumnConfig: CosmosPatchColumnConfig, dataType: DataType): CosmosPatchColumnConfig = {
    cosmosPatchColumnConfig.operationType match {
      case CosmosPatchOperationTypes.Increment =>
        dataType match {
          case _: NumericType => cosmosPatchColumnConfig
          case _ => throw new IllegalArgumentException(s"Increment patch operation does not support for type $dataType")
        }
      case _ => cosmosPatchColumnConfig   // TODO: Confirm the valid criteria for remove patch operation (only allow for non-exist column?)
    }
  }
}

private object SerializationInclusionModes extends Enumeration {
  type SerializationInclusionMode = Value

  val Always: SerializationInclusionModes.Value = Value("Always")
  val NonEmpty: SerializationInclusionModes.Value = Value("NonEmpty")
  val NonNull: SerializationInclusionModes.Value = Value("NonNull")
  val NonDefault: SerializationInclusionModes.Value = Value("NonDefault")
}

private object SerializationDateTimeConversionModes extends Enumeration {
  type SerializationDateTimeConversionMode = Value

  val Default: SerializationDateTimeConversionModes.Value = Value("Default")
  val AlwaysEpochMilliseconds: SerializationDateTimeConversionModes.Value = Value("AlwaysEpochMilliseconds")
}

private case class CosmosSerializationConfig
(
  serializationInclusionMode: SerializationInclusionMode,
  serializationDateTimeConversionMode: SerializationDateTimeConversionMode
)

private object CosmosSerializationConfig {
  private val inclusionMode = CosmosConfigEntry[SerializationInclusionMode](
    key = CosmosConfigNames.SerializationInclusionMode,
    mandatory = false,
    defaultValue = Some(SerializationInclusionModes.Always),
    parseFromStringFunction = value => CosmosConfigEntry.parseEnumeration(value, SerializationInclusionModes),
    helpMessage = "The serialization inclusion mode (`Always`, `NonNull`, `NonEmpty` or `NonDefault`)." +
      " When serializing json documents this setting determines whether json properties will be emitted" +
      " for columns in the RDD that are null/empty. The default value is `Always`.")

  private val dateTimeConversionMode = CosmosConfigEntry[SerializationDateTimeConversionMode](
    key = CosmosConfigNames.SerializationDateTimeConversionMode,
    mandatory = false,
    defaultValue = Some(SerializationDateTimeConversionModes.Default),
    parseFromStringFunction = value => CosmosConfigEntry.parseEnumeration(value, SerializationDateTimeConversionModes),
    helpMessage = "The date/time conversion mode (`Default`, `AlwaysEpochMilliseconds`). " +
      "With `Default` the standard Spark 3.* behavior is used (`java.sql.Date`/`java.time.LocalDate` are converted " +
      "to EpochDay, `java.sql.Timestamp`/`java.time.Instant` are converted to MicrosecondsFromEpoch). With " +
      "`AlwaysEpochMilliseconds` the same behavior the Cosmos DB connector for Spark 2.4 used is applied - " +
      "`java.sql.Date`, `java.time.LocalDate`, `java.sql.Timestamp` and `java.time.Instant` are converted " +
      "to MillisecondsFromEpoch.")

  def parseSerializationConfig(cfg: Map[String, String]): CosmosSerializationConfig = {
    val inclusionModeOpt = CosmosConfigEntry.parse(cfg, inclusionMode)
    val dateTimeConversionModeOpt = CosmosConfigEntry.parse(cfg, dateTimeConversionMode)

    // parsing above already validated this
    assert(inclusionModeOpt.isDefined)
    assert(dateTimeConversionModeOpt.isDefined)

    CosmosSerializationConfig(
      serializationInclusionMode = inclusionModeOpt.get,
      serializationDateTimeConversionMode = dateTimeConversionModeOpt.get
    )
  }
}

private object CosmosContainerConfig {
  private[spark] val DATABASE_NAME_KEY = CosmosConfigNames.Database
  private[spark] val CONTAINER_NAME_KEY = CosmosConfigNames.Container

  private val databaseNameSupplier = CosmosConfigEntry[String](key = DATABASE_NAME_KEY,
    mandatory = true,
    parseFromStringFunction = database => database,
    helpMessage = "Cosmos DB database name")

  private val containerNameSupplier = CosmosConfigEntry[String](key = CONTAINER_NAME_KEY,
    mandatory = true,
    parseFromStringFunction = container => container,
    helpMessage = "Cosmos DB container name")

  val optionalContainerNameSupplier = CosmosConfigEntry[String](key = CONTAINER_NAME_KEY,
    mandatory = false,
    parseFromStringFunction = container => container,
    helpMessage = "Cosmos DB container name")

  def parseCosmosContainerConfig(cfg: Map[String, String]): CosmosContainerConfig = {
    this.parseCosmosContainerConfig(cfg, None, None)
  }

  def parseCosmosContainerConfig(
                                cfg: Map[String, String],
                                databaseName: Option[String],
                                containerName: Option[String]): CosmosContainerConfig = {

    val databaseOpt = databaseName.getOrElse(CosmosConfigEntry.parse(cfg, databaseNameSupplier).get)
    val containerOpt = containerName.getOrElse(CosmosConfigEntry.parse(cfg, containerNameSupplier).get)

    CosmosContainerConfig(databaseOpt, containerOpt)
  }
}

private case class CosmosSchemaInferenceConfig(inferSchemaSamplingSize: Int,
                                               inferSchemaEnabled: Boolean,
                                               includeSystemProperties: Boolean,
                                               includeTimestamp: Boolean,
                                               allowNullForInferredProperties: Boolean,
                                               inferSchemaQuery: Option[String])

private object CosmosSchemaInferenceConfig {
  private val DefaultSampleSize: Int = 1000

  private val inferSchemaSamplingSize = CosmosConfigEntry[Int](key = CosmosConfigNames.ReadInferSchemaSamplingSize,
    mandatory = false,
    defaultValue = Some(DefaultSampleSize),
    parseFromStringFunction = size => size.toInt,
    helpMessage = "Sampling size to use when inferring schema")

  private val inferSchemaEnabled = CosmosConfigEntry[Boolean](key = CosmosConfigNames.ReadInferSchemaEnabled,
    mandatory = false,
    defaultValue = Some(true),
    parseFromStringFunction = enabled => enabled.toBoolean,
    helpMessage = "Whether schema inference is enabled or should return raw json")

  private val inferSchemaIncludeSystemProperties = CosmosConfigEntry[Boolean](
    key = CosmosConfigNames.ReadInferSchemaIncludeSystemProperties,
    mandatory = false,
    defaultValue = Some(false),
    parseFromStringFunction = include => include.toBoolean,
    helpMessage = "Whether schema inference should include the system properties in the schema")

  private val inferSchemaForceNullableProperties = CosmosConfigEntry[Boolean](
    key = CosmosConfigNames.ReadInferSchemaForceNullableProperties,
    mandatory = false,
    defaultValue = Some(true),
    parseFromStringFunction = include => include.toBoolean,
    helpMessage = "Whether schema inference should enforce inferred properties to be nullable - even when no null-values are contained in the sample set")

  private val inferSchemaIncludeTimestamp = CosmosConfigEntry[Boolean](
    key = CosmosConfigNames.ReadInferSchemaIncludeTimestamp,
    mandatory = false,
    defaultValue = Some(false),
    parseFromStringFunction = include => include.toBoolean,
    helpMessage = "Whether schema inference should include the timestamp (_ts) property")

  private val inferSchemaQuery = CosmosConfigEntry[String](key = CosmosConfigNames.ReadInferSchemaQuery,
    mandatory = false,
    parseFromStringFunction = query => query,
    helpMessage = "When schema inference is enabled, used as custom query to infer it")

  def parseCosmosInferenceConfig(cfg: Map[String, String]): CosmosSchemaInferenceConfig = {
    val samplingSize = CosmosConfigEntry.parse(cfg, inferSchemaSamplingSize)
    val enabled = CosmosConfigEntry.parse(cfg, inferSchemaEnabled)
    val query = CosmosConfigEntry.parse(cfg, inferSchemaQuery)
    val includeSystemProperties = CosmosConfigEntry.parse(cfg, inferSchemaIncludeSystemProperties)
    val includeTimestamp = CosmosConfigEntry.parse(cfg, inferSchemaIncludeTimestamp)
    val allowNullForInferredProperties = CosmosConfigEntry.parse(cfg, inferSchemaForceNullableProperties)

    assert(samplingSize.isDefined)
    assert(enabled.isDefined)
    CosmosSchemaInferenceConfig(
      samplingSize.get,
      enabled.get,
      includeSystemProperties.get,
      includeTimestamp.get,
      allowNullForInferredProperties.get,
      query)
  }
}

private object PartitioningStrategies extends Enumeration {
  type PartitioningStrategy = Value

  val Restrictive: PartitioningStrategies.Value = Value("Restrictive")
  val Default: PartitioningStrategies.Value  = Value("Default")
  val Aggressive: PartitioningStrategies.Value  = Value("Aggressive")
  val Custom: PartitioningStrategies.Value  = Value("Custom")
}

private case class CosmosPartitioningConfig
(
  partitioningStrategy: PartitioningStrategy,
  targetedPartitionCount: Option[Int],
  feedRangeFiler: Option[Array[NormalizedRange]]
)

private object CosmosPartitioningConfig {
  private val DefaultPartitioningStrategy: PartitioningStrategy = PartitioningStrategies.Default

  private val targetedPartitionCount = CosmosConfigEntry[Int](
    key = CosmosConfigNames.ReadPartitioningTargetedCount,
    keySuffix = Option.apply("(if strategy is custom)"),
    mandatory = true,
    parseFromStringFunction = targetedCountText => targetedCountText.toInt,
    helpMessage = "The targeted Partition Count. This parameter is optional and ignored unless " +
      "strategy==Custom is used. In this case the Spark Connector won't dynamically calculate " +
      "number of partitions but stick with this value.")

  private val partitioningStrategy = CosmosConfigEntry[PartitioningStrategy](
    key = CosmosConfigNames.ReadPartitioningStrategy,
    defaultValue = Some(PartitioningStrategies.Default),
    mandatory = false,
    parseFromStringFunction = strategyNotYetParsed => CosmosConfigEntry.parseEnumeration(strategyNotYetParsed, PartitioningStrategies),
    helpMessage = "The partitioning strategy used (Default, Custom, Restrictive or Aggressive)")

  private val partitioningFeedRangeFilter = CosmosConfigEntry[Array[NormalizedRange]](
    key = CosmosConfigNames.ReadPartitioningFeedRangeFilter,
    defaultValue = None,
    mandatory = false,
    parseFromStringFunction = filter => {
      requireNotNullOrEmpty(filter, CosmosConfigNames.ReadPartitioningFeedRangeFilter)

      val epkRanges = mutable.Buffer[NormalizedRange]()
      val fragments = filter.split(",")
      for (fragment <- fragments) {
        val minAndMax = fragment.trim.split("-")
        epkRanges += (NormalizedRange(minAndMax(0), minAndMax(1)))
      }

      epkRanges.toArray
    },
    helpMessage = "The feed ranges this query should be scoped to")

  def parseCosmosPartitioningConfig(cfg: Map[String, String]): CosmosPartitioningConfig = {
    val partitioningStrategyParsed = CosmosConfigEntry
      .parse(cfg, partitioningStrategy)
      .getOrElse(DefaultPartitioningStrategy)

    val partitioningFeedRangeFilterParsed = CosmosConfigEntry
      .parse(cfg, partitioningFeedRangeFilter)

    val targetedPartitionCountParsed = if (partitioningStrategyParsed == PartitioningStrategies.Custom) {
      CosmosConfigEntry.parse(cfg, targetedPartitionCount)
    } else {
      None
    }

    CosmosPartitioningConfig(
      partitioningStrategyParsed,
      targetedPartitionCountParsed,
      partitioningFeedRangeFilterParsed
    )
  }
}

private object ChangeFeedModes extends Enumeration {
  type ChangeFeedMode = Value

  val Incremental: ChangeFeedModes.Value = Value("Incremental")
  val FullFidelity: ChangeFeedModes.Value = Value("FullFidelity")
}

private object ChangeFeedStartFromModes extends Enumeration {
  type ChangeFeedStartFromMode = Value

  val Beginning: ChangeFeedStartFromModes.Value = Value("Beginning")
  val Now: ChangeFeedStartFromModes.Value = Value("Now")
  val PointInTime: ChangeFeedStartFromModes.Value = Value("PointInTime")
}

private case class CosmosChangeFeedConfig
(
  changeFeedMode: ChangeFeedMode,
  startFrom: ChangeFeedStartFromMode,
  startFromPointInTime: Option[Instant],
  maxItemCountPerTrigger: Option[Long]
) {

  def toRequestOptions(feedRange: FeedRange): CosmosChangeFeedRequestOptions = {
    val options = this.startFrom match {
      case ChangeFeedStartFromModes.Now =>
        CosmosChangeFeedRequestOptions.createForProcessingFromNow(feedRange)
      case ChangeFeedStartFromModes.Beginning =>
        CosmosChangeFeedRequestOptions.createForProcessingFromBeginning(feedRange)
      case ChangeFeedStartFromModes.PointInTime =>
        CosmosChangeFeedRequestOptions.createForProcessingFromPointInTime(this.startFromPointInTime.get, feedRange)
    }

    this.changeFeedMode match {
      case ChangeFeedModes.Incremental => options
      case ChangeFeedModes.FullFidelity => options.fullFidelity()
    }
  }

  def toReadLimit: ReadLimit = {
    this.maxItemCountPerTrigger match {
      case Some(maxItemCount) => ReadLimit.maxRows(maxItemCount)
      case None => ReadLimit.allAvailable()
    }
  }
}

private object CosmosChangeFeedConfig {
  private val DefaultChangeFeedMode: ChangeFeedMode = ChangeFeedModes.Incremental
  private val DefaultStartFromMode: ChangeFeedStartFromMode = ChangeFeedStartFromModes.Beginning

  private val startFrom = CosmosConfigEntry[ChangeFeedStartFromMode](
    key = CosmosConfigNames.ChangeFeedStartFrom,
    mandatory = false,
    defaultValue = Some(ChangeFeedStartFromModes.Beginning),
    parseFromStringFunction = startFromNotYetValidated => validateStartFromMode(startFromNotYetValidated),
    helpMessage = "ChangeFeed Start from settings (Now, Beginning  or a certain point in " +
      "time (UTC) for example 2020-02-10T14:15:03) - the default value is 'Beginning'.")

  private val startFromPointInTime = CosmosConfigEntry[Instant](
    key = CosmosConfigNames.ChangeFeedStartFrom,
    keySuffix = Option.apply("(for point in time)"),
    mandatory = true,
    parseFromStringFunction = startFrom => Instant.from(DateTimeFormatter
      .ISO_INSTANT
      .parse(startFrom.trim)),
    helpMessage = "ChangeFeed Start from settings (Now, Beginning  or a certain point in " +
      "time (UTC) for example 2020-02-10T14:15:03Z) - the default value is 'Beginning'.")

  private val changeFeedMode = CosmosConfigEntry[ChangeFeedMode](
    key = CosmosConfigNames.ChangeFeedMode,
    mandatory = false,
    defaultValue = Some(ChangeFeedModes.Incremental),
    parseFromStringFunction = changeFeedModeString => CosmosConfigEntry.parseEnumeration(changeFeedModeString, ChangeFeedModes),
    helpMessage = "ChangeFeed mode (Incremental or FullFidelity)")

  private val maxItemCountPerTriggerHint = CosmosConfigEntry[Long](
    key = CosmosConfigNames.ChangeFeedItemCountPerTriggerHint,
    mandatory = false,
    parseFromStringFunction = maxItemCount => maxItemCount.toInt,
    helpMessage = "Approximate maximum number of items read from change feed for each trigger")

  private def validateStartFromMode(startFrom: String): ChangeFeedStartFromMode = {
    Option(startFrom).fold(DefaultStartFromMode)(sf => {
      val trimmed = sf.trim

      if (trimmed.equalsIgnoreCase(ChangeFeedStartFromModes.Beginning.toString)) {
        ChangeFeedStartFromModes.Beginning
      } else if (trimmed.equalsIgnoreCase(ChangeFeedStartFromModes.Now.toString)) {
        ChangeFeedStartFromModes.Now
      } else {
        ChangeFeedStartFromModes.PointInTime
      }
    })
  }

  def parseCosmosChangeFeedConfig(cfg: Map[String, String]): CosmosChangeFeedConfig = {
    val changeFeedModeParsed = CosmosConfigEntry.parse(cfg, changeFeedMode)
    val startFromModeParsed = CosmosConfigEntry.parse(cfg, startFrom)
    val maxItemCountPerTriggerHintParsed = CosmosConfigEntry.parse(cfg, maxItemCountPerTriggerHint)
    val startFromPointInTimeParsed = startFromModeParsed match {
      case Some(PointInTime) => CosmosConfigEntry.parse(cfg, startFromPointInTime)
      case _ => None
    }

    CosmosChangeFeedConfig(
      changeFeedModeParsed.getOrElse(DefaultChangeFeedMode),
      startFromModeParsed.getOrElse(DefaultStartFromMode),
      startFromPointInTimeParsed,
      maxItemCountPerTriggerHintParsed)
  }
}

private case class CosmosThroughputControlConfig(groupName: String,
                                                 targetThroughput: Option[Int],
                                                 targetThroughputThreshold: Option[Double],
                                                 globalControlDatabase: String,
                                                 globalControlContainer: String,
                                                 globalControlRenewInterval: Option[Duration],
                                                 globalControlExpireInterval: Option[Duration])

private object CosmosThroughputControlConfig {
    private val throughputControlEnabledSupplier = CosmosConfigEntry[Boolean](
        key = CosmosConfigNames.ThroughputControlEnabled,
        mandatory = false,
        defaultValue = Some(false),
        parseFromStringFunction = enableThroughputControl => enableThroughputControl.toBoolean,
        helpMessage = "A flag to indicate whether throughput control is enabled.")

    private val groupNameSupplier = CosmosConfigEntry[String](
        key = CosmosConfigNames.ThroughputControlName,
        mandatory = false,
        parseFromStringFunction = groupName => groupName,
        helpMessage = "Throughput control group name. " +
            "Since customer is allowed to create many groups for a container, the name should be unique.")

    private val targetThroughputSupplier = CosmosConfigEntry[Int](
        key = CosmosConfigNames.ThroughputControlTargetThroughput,
        mandatory = false,
        parseFromStringFunction = targetThroughput => targetThroughput.toInt,
        helpMessage = "Throughput control group target throughput. The value should be larger than 0.")

    private val targetThroughputThresholdSupplier = CosmosConfigEntry[Double](
        key = CosmosConfigNames.ThroughputControlTargetThroughputThreshold,
        mandatory = false,
        parseFromStringFunction = targetThroughput => targetThroughput.toDouble,
        helpMessage = "Throughput control group target throughput threshold. The value should be between (0,1]. ")

    private val globalControlDatabaseSupplier = CosmosConfigEntry[String](
        key = CosmosConfigNames.ThroughputControlGlobalControlDatabase,
        mandatory = false,
        parseFromStringFunction = globalControlDatabase => globalControlDatabase,
        helpMessage = "Database which will be used for throughput global control.")

    private val globalControlContainerSupplier = CosmosConfigEntry[String](
        key = CosmosConfigNames.ThroughputControlGlobalControlContainer,
        mandatory = false,
        parseFromStringFunction = globalControlContainer => globalControlContainer,
        helpMessage = "Container which will be used for throughput global control.")

    private val globalControlItemRenewIntervalSupplier = CosmosConfigEntry[Duration](
        key = CosmosConfigNames.ThroughputControlGlobalControlRenewalIntervalInMS,
        mandatory = false,
        parseFromStringFunction = renewIntervalInMilliseconds => Duration.ofMillis(renewIntervalInMilliseconds.toInt),
        helpMessage = "This controls how often the client is going to update the throughput usage of itself " +
            "and adjust its own throughput share based on the throughput usage of other clients. " +
            "Default is 5s, the allowed min value is 5s.")

    private val globalControlItemExpireIntervalSupplier = CosmosConfigEntry[Duration](
        key = CosmosConfigNames.ThroughputControlGlobalControlExpireIntervalInMS,
        mandatory = false,
        parseFromStringFunction = expireIntervalInMilliseconds => Duration.ofMillis(expireIntervalInMilliseconds.toInt),
        helpMessage = "This controls how quickly we will detect the client has been offline " +
            "and hence allow its throughput share to be taken by other clients. " +
            "Default is 11s, the allowed min value is 2 * renewIntervalInMS + 1")

    def parseThroughputControlConfig(cfg: Map[String, String]): Option[CosmosThroughputControlConfig] = {
        val throughputControlEnabled = CosmosConfigEntry.parse(cfg, throughputControlEnabledSupplier).get

        if (throughputControlEnabled) {
            val groupName = CosmosConfigEntry.parse(cfg, groupNameSupplier)
            val targetThroughput = CosmosConfigEntry.parse(cfg, targetThroughputSupplier)
            val targetThroughputThreshold = CosmosConfigEntry.parse(cfg, targetThroughputThresholdSupplier)
            val globalControlDatabase = CosmosConfigEntry.parse(cfg, globalControlDatabaseSupplier)
            val globalControlContainer = CosmosConfigEntry.parse(cfg, globalControlContainerSupplier)
            val globalControlItemRenewInterval = CosmosConfigEntry.parse(cfg, globalControlItemRenewIntervalSupplier)
            val globalControlItemExpireInterval = CosmosConfigEntry.parse(cfg, globalControlItemExpireIntervalSupplier)

            assert(groupName.isDefined)
            assert(globalControlDatabase.isDefined)
            assert(globalControlContainer.isDefined)

            Some(CosmosThroughputControlConfig(
                groupName.get,
                targetThroughput,
                targetThroughputThreshold,
                globalControlDatabase.get,
                globalControlContainer.get,
                globalControlItemRenewInterval,
                globalControlItemExpireInterval))
        } else {
            None
        }
    }
}


private case class CosmosConfigEntry[T](key: String,
                                        keyAlias: Option[String] = Option.empty,
                                        mandatory: Boolean,
                                        defaultValue: Option[T] = Option.empty,
                                        parseFromStringFunction: String => T,
                                        helpMessage: String,
                                        keySuffix: Option[String] = None) {

  CosmosConfigEntry.configEntriesDefinitions.put(key + keySuffix.getOrElse(""), this)
  CosmosConfigEntry.configEntriesDefinitions.put(keyAlias + keySuffix.getOrElse(""), this)

  def parse(paramAsString: String) : T = {
    try {
      parseFromStringFunction(paramAsString)
    } catch {
      case e: Exception => throw new RuntimeException(
        s"invalid configuration for $key:$paramAsString. Config description: $helpMessage",  e)
    }
  }
}

// TODO: moderakh how to merge user config with SparkConf application config?
private object CosmosConfigEntry {
  def parseEnumeration[T <: Enumeration](enumValueAsString: String, enumeration: T): T#Value = {
    require(enumValueAsString != null)
    enumeration.values.find(_.toString.toLowerCase == enumValueAsString.toLowerCase()).getOrElse(
      throw new IllegalArgumentException(s"$enumValueAsString valid value, valid values are ${values}"))
  }

  private val configEntriesDefinitions = new java.util.HashMap[String, CosmosConfigEntry[_]]()

  def allConfigNames(): Seq[String] = {
    configEntriesDefinitions.keySet().asScala.toSeq
  }

  def parse[T](configuration: Map[String, String], configEntry: CosmosConfigEntry[T]): Option[T] = {
    // we are doing this here per config parsing for now
    val loweredCaseConfiguration = configuration
      .map { case (key, value) => (key.toLowerCase(Locale.ROOT), value) }

    var opt = loweredCaseConfiguration.get(configEntry.key.toLowerCase(Locale.ROOT))
    val optAlias = if (configEntry.keyAlias.isDefined) loweredCaseConfiguration.get(configEntry.keyAlias.get.toLowerCase(Locale.ROOT)) else Option.empty

    if (opt.isDefined && optAlias.isDefined) {
      throw new RuntimeException(s"specified multiple conflicting options [${configEntry.key}] and [${configEntry.keyAlias.get}]. Only one should be specified")
    }

    if (opt.isEmpty) {
      opt = optAlias
    }

    if (opt.isDefined) {
      Option.apply(configEntry.parse(opt.get))
    }
    else {
      if (configEntry.mandatory) {
        throw new RuntimeException(
          s"mandatory option ${configEntry.key} is missing. Config description: ${configEntry.helpMessage}")
      } else {
        configEntry.defaultValue.orElse(Option.empty)
      }
    }
  }
}
// scalastyle:on multiple.string.literals
// scalastyle:on file.size.limit
// scalastyle:on number.of.types
