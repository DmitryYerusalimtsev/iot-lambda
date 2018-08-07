package com.iotlambda.analytics.speed.jobs.avgtelemetryvalue

private[speed] case class AvgByType(
                                     timestamp: String,
                                     deviceType: String,
                                     avgValue: Double
                                   )
