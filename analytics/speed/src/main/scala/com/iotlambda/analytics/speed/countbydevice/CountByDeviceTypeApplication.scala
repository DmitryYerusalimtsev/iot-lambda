package com.iotlambda.analytics.speed.countbydevice

import com.iotlambda.analytics.common.AbstractSparkApplication
import com.iotlambda.analytics.speed.Schema
import com.iotlambda.domain.DeviceTelemetry
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.{OutputMode, StreamingQuery}

object CountByDeviceTypeApplication extends AbstractSparkApplication {

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

    countByType.printSchema()

    writeToSink(countByType).awaitTermination()
  }

  private def readSourceDS(): Dataset[DeviceTelemetry] = {
    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "iot-telemetry")
      .load()
      .select(from_json($"value".cast("string"), Schema()).as("value"), $"timestamp")
      .select($"value.device", $"value.telemetry", $"timestamp")

    df.as[DeviceTelemetry]
  }

  private def writeToSink(ds: Dataset[CountByType]): StreamingQuery = {
    ds.writeStream
      .outputMode(OutputMode.Append())
      .format("console")
      //.trigger(Trigger.Continuous("1 second"))
      .start()
  }
}
