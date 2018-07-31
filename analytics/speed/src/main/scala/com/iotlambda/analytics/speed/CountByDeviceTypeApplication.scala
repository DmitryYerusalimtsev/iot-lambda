package com.iotlambda.analytics.speed

import com.iotlambda.analytics.common.AbstractSparkApplication
import com.iotlambda.domain.DeviceTelemetry
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.types._

object CountByDeviceTypeApplication extends AbstractSparkApplication {

  val deviceSchema = StructType(
    Array(
      StructField("name", StringType, nullable = false),
      StructField("type", StringType, nullable = false),
      StructField("serialNumber", StringType, nullable = false)
    )
  )

  val telemetrySchema = StructType(
    Array(
      StructField("value", DoubleType, nullable = false)
    )
  )

  val schema = StructType(
    Array(
      StructField("device", deviceSchema, nullable = true),
      StructField("telemetry", telemetrySchema, nullable = true),
      StructField("timestamp", StringType, nullable = true)
    )
  )

  import spark.implicits._

  override protected def getAppName: String = "CountByDeviceType"

  override protected def execute(): Unit = {

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "iot-telemetry")
      .load()
      .select(from_json($"value".cast("string"), schema).as("value"))
      .select("value.*")

    df.printSchema()
    val ds = df.as[DeviceTelemetry]

    val query = ds.writeStream
      .outputMode(OutputMode.Append())
      .format("console")
      .start()

    query.awaitTermination()
  }
}