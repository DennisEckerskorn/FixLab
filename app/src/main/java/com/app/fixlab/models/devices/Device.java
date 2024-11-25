package com.app.fixlab.models.devices;

import com.app.fixlab.R;

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
    private final String type;
    private final String status;

    /**
     * CONSTRUCTORS:
     * //TODO: NO SE TOCA ESTE CONSTRUCTOR; CREATE OTRO JON:
     **/
    public Device(String model, String serialNumber, String description, String brand, String type, String status) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.description = description;
        this.brand = brand;
        this.type = type;
        this.status = status;

        /*
         this.condition = DeviceCondition.valueOf(condition.toUpperCase());
        this.type = DeviceType.valueOf(type.toUpperCase());
        this.status = DeviceStatus.RECIVED;
         */
    }
/*
    public void setStatus(DeviceStatus status) {
        this.status = status;
    }


 */
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
/*
    public DeviceStatus getStatus() {
        return status;
    }

 */
    /*
    public int getStatusString() {
        int status = -1;
        switch (this.getStatus()){
            case RECIVED:
                status = R.string.status_recived;
                break;
            case IN_REPARATION:
                status = R.string.status_in_reparation;
                break;
            case IN_REVISION:
                status = R.string.status_in_revision;
                break;
            case FIXED:
                status = R.string.status_fixed;
                break;
            case WAITING_FOR_PARTS:
                status = R.string.status_waiting_for_parts;
                break;
            default:
                break;
        }
        return status;
    }

     */
/*
    public DeviceType getType() {
        return type;
    }

 */
    /*
    public int getTypeString(){
        int type = -1;
        switch (this.getType()){
            case LAPTOP:
                type = R.drawable.device_type_laptop;
                break;
            case COMPUTER:
                type = R.drawable.device_type_computer;
                break;
            case SMARTPHONE:
                type = R.drawable.device_type_smartphone;
                break;
            case TABLET:
                type = R.drawable.device_type_tablet;
                break;
            case SMARTWATCH:
                type = R.drawable.device_type_smartwatch;
                break;
            case MONITOR:
                type = R.drawable.device_type_monitor;
                break;
            case PERIPHERAL:
                type = R.drawable.device_type_peripheral;
                break;
            case OTHER:
                type = R.drawable.device_type_other;
                break;
            default:
                break;
        }
        return type;
    }

     */

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
               // ", condition=" + condition +
                ", type=" + type +
                '}';
    }
}
