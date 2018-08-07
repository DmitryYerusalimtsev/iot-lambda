package com.iotlambda.analytics.common

import org.apache.commons.io.IOUtils

trait FileReader {
  def getFileContent(file: String): String = {
    val classLoader = getClass.getClassLoader
    IOUtils.toString(classLoader.getResourceAsStream(file))
  }
}
