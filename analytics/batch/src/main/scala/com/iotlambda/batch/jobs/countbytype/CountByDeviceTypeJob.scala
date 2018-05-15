package com.iotlambda.batch.jobs.countbytype

import com.iotlambda.domain.DeviceTelemetry
import org.apache.spark.sql.SparkSession

final class CountByDeviceTypeJob(telemetryProvider: DataSetProvider[DeviceTelemetry]) {

  private val spark = SparkSession
    .builder()
    .appName("Count by device type")
    //.config("spark.some.config.option", "some-value")
    .getOrCreate()

  import spark.implicits._

  def process(): Unit = {
    val telemetryDS = telemetryProvider.read()

    telemetryDS.groupBy($"")
  }
}
