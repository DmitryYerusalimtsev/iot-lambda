package com.iotlambda.analytics.speed.countbydevice

import com.iotlambda.analytics.speed.AbstractByDeviceTypeApp
import org.apache.spark.sql.functions._

object CountByDeviceTypeApplication extends AbstractByDeviceTypeApp {

  import spark.implicits._

  override protected def getAppName: String = "CountByDeviceType"

  override protected def execute(): Unit = {

    val ds = readSourceDS()

    val countByType = ds
      .withWatermark("timestamp", "10 seconds")
      .groupBy(
        window($"timestamp", "1 minute", "30 seconds"),
        $"device.type")
      .count()
      .select($"type", $"count")
      .as[(String, Long)]
      .map({
        case (deviceType, count) => CountByType(deviceType, count)
      })

    writeToSink(countByType).awaitTermination()
  }
}