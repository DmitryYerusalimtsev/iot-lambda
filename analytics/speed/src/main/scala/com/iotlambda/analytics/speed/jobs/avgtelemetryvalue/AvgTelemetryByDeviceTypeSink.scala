package com.iotlambda.analytics.speed.jobs.avgtelemetryvalue

import com.iotlambda.analytics.speed.Cassandra
import org.apache.spark.sql.{ForeachWriter, SparkSession}

private[avgtelemetryvalue] class AvgTelemetryByDeviceTypeSink(spark: SparkSession)
  extends ForeachWriter[AvgByType] with Cassandra {

  private val connector = getConnector(spark)

  override def open(partitionId: Long, version: Long): Boolean = true

  override def process(value: AvgByType): Unit = {
    connector.withSessionDo { session =>
      session.execute(
        s"""INSERT INTO iot_lambda_speed.avg_telemetry_by_device_type (timestamp,deviceType,avgValue)
           | VALUES ('${value.timestamp}', '${value.deviceType}', ${value.avgValue})""".stripMargin
      )
    }
  }

  override def close(errorOrNull: Throwable): Unit = {}
}
