package com.iotlambda.generator.domain.telemetry;

public class Temperature extends Telemetry {
    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
