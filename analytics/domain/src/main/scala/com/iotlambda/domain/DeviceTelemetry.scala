package com.iotlambda.domain

import com.iotlambda.domain.telemetry.Telemetry
import org.joda.time.DateTime

case class DeviceTelemetry(
                            device: Device,
                            telemetry: Telemetry,
                            timestamp: DateTime)
