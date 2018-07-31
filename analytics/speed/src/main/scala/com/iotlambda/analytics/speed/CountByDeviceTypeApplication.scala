package com.iotlambda.analytics.speed

import com.iotlambda.analytics.common.AbstractSparkApplication
import com.iotlambda.domain.DeviceTelemetry
import org.apache.spark.sql.ForeachWriter
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.OutputMode

object CountByDeviceTypeApplication extends AbstractSparkApplication {

  val deviceSchema = StructType(
    Array(
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
      StructField("device", deviceSchema, nullable = false),
      StructField("telemetry", telemetrySchema, nullable = false),
      StructField("timestamp", StringType, nullable = false)
    )
  )

  import spark.implicits._
  //implicit val myObjEncoder = org.apache.spark.sql.Encoders.kryo[DeviceTelemetry]

  override protected def getAppName: String = "CountByDeviceType"

  override protected def execute(): Unit = {

    val df = spark
      .readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "iot-telemetry")
      .load()
      .select(from_json($"value".cast("string"), schema).alias("value"))
      .select("value.*")

    df.printSchema()
    val ds = df.as[DeviceTelemetry]

    val query = ds.writeStream
      .outputMode(OutputMode.Append())
      .format("console")
      .start()

    //ds.printSchema()

    //    val query = df.writeStream
    //      .outputMode("append")
    //      .format("console")
    //      .start()

    query.awaitTermination()

    //df.printSchema()
    //val ds = df.as[DeviceTelemetry]

    //ds.printSchema()
  }
}

object Test extends ForeachWriter[DeviceTelemetry] {
  override def open(partitionId: Long, version: Long): Boolean = {
    true
  }

  override def process(value: DeviceTelemetry): Unit = {
    println(value.telemetry.value)
  }

  override def close(errorOrNull: Throwable): Unit = {

  }
}