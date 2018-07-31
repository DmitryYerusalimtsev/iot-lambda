package com.iotlambda.analytics.common

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

abstract class AbstractSparkApplication {

  protected def getAppName: String
  protected val master: String = "local[*]"

  protected def initSparkConf(conf: SparkConf) {
    conf.setMaster(master)
    conf.setAppName(getAppName)
  }

  val spark: SparkSession = {
    val sparkConf = new SparkConf()
    initSparkConf(sparkConf)

    SparkSession
      .builder()
      .config(sparkConf)
      .getOrCreate()
  }

  protected lazy val sc: SparkContext = spark.sparkContext

  def main(args: Array[String]): Unit = run(args)

  def run(args: Array[String]): Unit = {
    try {
      init(args)
      execute()
    } catch {
      case e: Exception =>
        throw new RuntimeException("An error occurred while running the application", e)
    }
  }

  protected def init(args: Array[String]): Unit = {}

  protected def execute(): Unit
}
