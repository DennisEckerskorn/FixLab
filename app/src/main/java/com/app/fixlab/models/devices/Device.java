package com.app.fixlab.models.devices;

import com.app.fixlab.R;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a generic device with attributes such as model, serial number, description,
 * brand, type, condition, and status.
 * It serves as the base class for specific types of devices such as laptops, computers,
 * smartphones, tablets, etc.
 */
public class Device implements Serializable {

    private final String model;
    private final String serialNumber;
    private final String description;
    private final String brand;
    private DeviceStatus status;
    private final DeviceType type;
    private final DeviceCondition condition;

    /**
     * Constructor for the Device class.
     *
     * @param model       the model of the device.
     * @param serialNumber the unique serial number of the device.
     * @param description a brief description of the device.
     * @param brand       the brand of the device.
     * @param type        the type of the device (e.g., Laptop, Smartphone).
     * @param condition   the condition of the device (e.g., New, Used).
     */
    public Device(String model, String serialNumber, String description, String brand, DeviceType type, DeviceCondition condition) {
        this.model = model;
        this.serialNumber = serialNumber;
        this.description = description;
        this.brand = brand;
        this.type = type;
        this.condition = condition;
        this.status = DeviceStatus.PENDING;
    }

    /**
     * Sets the status of the device.
     *
     * @param status the new status of the device.
     */
    public void setStatus(DeviceStatus status) {
        this.status = status;
    }

    // GETTERS:

    /**
     * Gets the model of the device.
     *
     * @return the model.
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the serial number of the device.
     *
     * @return the serial number.
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Gets the description of the device.
     *
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the brand of the device.
     *
     * @return the brand.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Gets the current status of the device.
     *
     * @return the status.
     */
    public DeviceStatus getStatus() {
        return status;
    }

    /**
     * Converts the device's condition to a string resource ID.
     *
     * @return the string resource ID for the condition.
     */
    public int getConditionString() {
        int condition = -1;
        switch (this.getCondition()) {
            case NEW -> condition = R.string.condition_new;
            case USED -> condition = R.string.condition_used;
            case REPAIRED -> condition = R.string.condition_repaired;
            case WORKING -> condition = R.string.condition_working;
            case IN_GOOD_CONDITION -> condition = R.string.condition_good_condition;
            case DEFECTIVE -> condition = R.string.condition_defective;
            case SCRATCHED -> condition = R.string.condition_scratched;
            case DESTROYED -> condition = R.string.condition_destroyed;
        }
        return condition;
    }
    /**
     * Converts the device's status to a string resource ID.
     *
     * @return the string resource ID for the status.
     */
    public int getStatusString() {
        int status = -1;
        switch (this.getStatus()) {
            case PENDING:
                status = R.string.status_pending;
                break;
            case COMPLETED:
                status = R.string.status_completed;
                break;
            case IN_PROGRESS:
                status = R.string.status_in_progress;
                break;
            case NEEDS_PARTS:
                status = R.string.status_waiting_for_parts;
                break;
            case UNREPAIRABLE:
                status = R.string.status_unreparable;
                break;
            default:
                break;
        }
        return status;
    }

    /**
     * Gets the type of the device.
     *
     * @return the type.
     */
    public DeviceType getType() {
        return type;
    }

    /**
     * Converts the device's type to an image resource ID.
     *
     * @return the image resource ID for the type.
     */
    public int getTypeString() {
        int type = -1;
        switch (this.getType()) {
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

    /**
     * Gets the condition of the device.
     *
     * @return the condition.
     */
    public DeviceCondition getCondition() {
        return condition;
    }

    // EQUALS AND HASHCODE:

    /**
     * Checks if this device is equal to another object based on its serial number.
     *
     * @param o the object to compare.
     * @return true if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return Objects.equals(serialNumber, device.serialNumber);
    }

    /**
     * Generates a hash code based on the serial number.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(serialNumber);
    }

    // TOSTRING:

    /**
     * Returns a string representation of the device.
     *
     * @return the string representation.
     */
    @Override
    public String toString() {
        return "Device{" +
                "model='" + model + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", description='" + description + '\'' +
                ", brand='" + brand + '\'' +
                ", type=" + type +
                '}';
    }
}
