package com.iotlambda.analytics.speed.jobs.avgtelemetryvalue

import com.iotlambda.analytics.speed.jobs.AbstractByDeviceTypeApp
import org.apache.spark.sql.functions._

object AvgTelemetryValueByDeviceType extends AbstractByDeviceTypeApp {

  import spark.implicits._

  override protected def getAppName: String = "AvgTelemetryValueByDeviceType"

  override protected def execute(): Unit = {
    val ds = readSourceDS()

    val countByType = ds
      .withWatermark("timestamp", "10 seconds")
      .groupBy(
        window($"timestamp", "1 minute", "30 seconds"),
        $"device.type")
      .agg(
        avg($"telemetry.value").as("avg_value")
      )
      .select($"timestamp", $"type", $"avg_value")
      .as[(String, String, Double)]
      .map({
        case (timestamp, deviceType, avg) => AvgByType(timestamp, deviceType, avg)
      })

    writeToSink(countByType, new AvgTelemetryByDeviceTypeSink(spark)).awaitTermination()
  }
}
