package com.iotlambda.analytics.speed

import com.iotlambda.analytics.common.AbstractSparkApplication
import com.iotlambda.domain.DeviceTelemetry
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions.from_json
import org.apache.spark.sql.streaming.{OutputMode, StreamingQuery}

abstract class AbstractByDeviceTypeApp extends AbstractSparkApplication {

  protected def readSourceDS(): Dataset[DeviceTelemetry] = {
    import spark.implicits._

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

  protected def writeToSink[T](ds: Dataset[T]): StreamingQuery = {
    ds.writeStream
      .outputMode(OutputMode.Append())
      .format("console")
      //.trigger(Trigger.Continuous("1 second"))
      .start()
  }

}
