package com.iotlambda.domain

import com.iotlambda.domain.DeviceType.DeviceType

case class Device(name: String,
                  deviceType: DeviceType,
                  serialNumber: String)
