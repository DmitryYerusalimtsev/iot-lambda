package com.iotlambda.analytics.speed

import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}

private[speed] object Schema {
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

  def apply(): StructType = schema
}
