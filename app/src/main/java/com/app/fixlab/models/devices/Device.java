package com.app.fixlab.models.devices;

import androidx.annotation.NonNull;

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
    private final DeviceStatus status;
    private final DeviceType type;

    /**
     * CONSTRUCTORS:
     **/
    public Device(String model, String serialNumber, String description, String brand, String status, String type) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.description = description;
        this.brand = brand;
        this.status = DeviceStatus.valueOf(status.toUpperCase());
        this.type = DeviceType.valueOf(type.toUpperCase());
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

    public DeviceStatus getStatus() {
        return status;
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
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
