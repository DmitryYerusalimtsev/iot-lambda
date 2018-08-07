package com.iotlambda.analytics.speed

import com.datastax.driver.core.{ResultSet, Session}
import com.datastax.spark.connector.cql.CassandraConnector
import com.iotlambda.analytics.common.FileReader
import org.apache.spark.sql.SparkSession

private[speed] trait Cassandra extends FileReader {
  def getConnector(spark: SparkSession): CassandraConnector = {
    val connector = CassandraConnector(spark.sparkContext.getConf)
    connector
  }

  def createKeySpaceAndTable(session: Session, keyspaceScript: String, tableScript: String): ResultSet = {
    session.execute(getFileContent(keyspaceScript))
    session.execute(getFileContent(tableScript))
  }
}
