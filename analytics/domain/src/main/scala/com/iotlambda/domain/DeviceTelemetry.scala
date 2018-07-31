package com.iotlambda.domain

import org.joda.time.DateTime

case class DeviceTelemetry(
                            device: Device,
                            telemetry: Telemetry,
                            timestamp: String)
