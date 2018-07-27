package com.iotlambda.generator.domain;

import com.iotlambda.generator.domain.telemetry.Telemetry;
import org.joda.time.DateTime;

public class DeviceTelemetry {
    private Device device;
    private Telemetry telemetry;
    private DateTime timestamp;

    public DeviceTelemetry(
            Device device,
            Telemetry telemetry,
            DateTime timestamp) {
        this.device = device;
        this.telemetry = telemetry;
        this.timestamp = timestamp;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }
}
