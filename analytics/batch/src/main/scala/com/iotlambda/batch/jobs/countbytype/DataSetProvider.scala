package com.iotlambda.batch.jobs.countbytype

import org.apache.spark.sql.Dataset

trait DataSetProvider[T] {
  def read(): Dataset[T]
}
