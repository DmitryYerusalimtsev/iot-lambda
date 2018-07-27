package com.iotlambda.generator.domain.telemetry;

public class Temperature extends Telemetry {
    private Double value;

    public Temperature(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
