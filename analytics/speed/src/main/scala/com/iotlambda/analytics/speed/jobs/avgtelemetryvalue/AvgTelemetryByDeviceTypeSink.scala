package com.iotlambda.analytics.speed.jobs.avgtelemetryvalue

import java.sql.Timestamp

import com.iotlambda.analytics.speed.Cassandra
import org.apache.spark.sql.{ForeachWriter, SparkSession}
import org.joda.time.format.DateTimeFormat

private[avgtelemetryvalue] class AvgTelemetryByDeviceTypeSink(spark: SparkSession)
  extends ForeachWriter[AvgByType] with Cassandra {

  private val connector = getConnector(spark)

  override def open(partitionId: Long, version: Long): Boolean = true

  override def process(value: AvgByType): Unit = {
    connector.withSessionDo { session =>

      val secondDate = value.timestamp.split(',').last.dropRight(1).trim
      val timestamp = parseTimestamp(secondDate)

      session.execute(
        s"""INSERT INTO iot_lambda_speed.avg_telemetry_by_device_type (timestamp,deviceType,avgValue)
           | VALUES ('$timestamp', '${value.deviceType}', ${value.avgValue})""".stripMargin
      )
    }
  }

  override def close(errorOrNull: Throwable): Unit = {}

  private def parseTimestamp(value: String): String = {
    val formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
    val dt = formatter.parseDateTime(value)
    new Timestamp(dt.getMillis).toString
  }
}
