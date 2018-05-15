package com.iotlambda.domain

object DeviceType extends Enumeration {
  type DeviceType = Value

  val TemperatureSensor, AirPressureSensor = Value
}
