package com.app.fixlab.models.devices;

import java.util.Objects;

/**
 * DEVICE CLASS: Father class of Laptop, Computer, Smartphone, Tablet, Smartwatch, Monitor, Peripheral and Other
 */
public class Device {
    private final static int id = 0;
    private final String model;
    private final String serialNumber;
    private final String description;
    private final String brand;
    private final DeviceCondition condition;
    private final DeviceType type;
    private DeviceStatus status;

    /**
     * CONSTRUCTORS:
     **/
    public Device(String model, String serialNumber, String description, String brand, String condition, String type) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.description = description;
        this.brand = brand;
        this.condition = DeviceCondition.valueOf(condition.toUpperCase());
        this.type = DeviceType.valueOf(type.toUpperCase());
        this.status = DeviceStatus.RECIVED;
    }

    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    /**
     * GETTERS:
     **/

    public String getModel() {
        return model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public String getBrand() {
        return brand;
    }

    public DeviceCondition getStatus() {
        return condition;
    }

    public DeviceType getType() {
        return type;
    }

    /**
     * EQUALS AND HASHCODE:
     **/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(serialNumber, device.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serialNumber);
    }

    /**
    TOSTRING:
     **/

    @Override
    public String toString() {
        return "Device{" +
                "model='" + model + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", condition=" + condition +
                ", type=" + type +
                '}';
    }
}
