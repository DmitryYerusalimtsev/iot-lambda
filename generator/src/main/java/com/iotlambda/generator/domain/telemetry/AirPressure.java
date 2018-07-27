package com.iotlambda.generator.domain.telemetry;

public class AirPressure extends Telemetry {
    public AirPressure(Double value) {
        this.value = value;
    }

    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
