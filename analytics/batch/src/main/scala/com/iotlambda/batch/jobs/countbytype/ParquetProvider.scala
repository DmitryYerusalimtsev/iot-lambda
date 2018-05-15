package com.iotlambda.batch.jobs.countbytype

import org.apache.spark.sql.{Dataset, SparkSession}

final class ParquetProvider[T](path: String, spark: SparkSession) extends DataSetProvider[T] {
  override def read(): Dataset[T] = {
    spark.read.parquet(path).as[T]
  }
}
