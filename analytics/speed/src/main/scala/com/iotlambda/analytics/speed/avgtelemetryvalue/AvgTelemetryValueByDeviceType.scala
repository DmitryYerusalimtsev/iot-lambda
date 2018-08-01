package com.iotlambda.analytics.speed.avgtelemetryvalue

import com.iotlambda.analytics.speed.AbstractByDeviceTypeApp
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
      .select($"type", $"avg_value")
      .as[(String, Double)]
      .map({
        case (deviceType, avg) => AvgByType(deviceType, avg)
      })

    writeToSink(countByType).awaitTermination()
  }
}
