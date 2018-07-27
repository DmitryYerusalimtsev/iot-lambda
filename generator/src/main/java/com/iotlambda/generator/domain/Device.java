package com.iotlambda.generator.domain;

public class Device {
    private String name;
    private DeviceType type;
    private String serialNumber;

    public Device(String name, DeviceType type, String serialNumber) {
        this.name = name;
        this.type = type;
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
