package com.app.fixlab.models.persons;

import java.io.Serializable;

/**
 * Represents a Technician, which is a subclass of Person.
 * Technicians have additional attributes, such as their availability status.
 */
public class Technician extends Person implements Serializable {

    /**
     * Enum representing the availability status of a technician.
     */
    public enum Availability {
        AVAILABLE, UNAVAILABLE
    }

    private Availability availability;

    /**
     * Constructs a Technician object with the given parameters.
     *
     * @param dni          the unique identifier for the technician.
     * @param name         the name of the technician.
     * @param surname      the surname of the technician.
     * @param email        the email address of the technician.
     * @param phoneNumber  the phone number of the technician.
     * @param address      the address of the technician.
     * @param username     the username for the technician's account.
     * @param password     the password for the technician's account.
     * @param availability the availability status of the technician ("AVAILABLE" or "UNAVAILABLE").
     *                     The string is converted to the appropriate enum value.
     */
    public Technician(String dni, String name, String surname, String email, String phoneNumber, String address, String username, String password, String availability) {
        super(dni, name, surname, email, phoneNumber, address, username, password);
        this.availability = Availability.valueOf(availability.toUpperCase());
    }

    /**
     * Gets the availability status of the technician.
     *
     * @return the availability status.
     */
    public Availability getAvailability() {
        return availability;
    }

    /**
     * Sets the availability status of the technician.
     *
     * @param availability the new availability status.
     */
    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    /**
     * Returns a string representation of the technician, including their personal details
     * and availability status.
     *
     * @return the string representation of the technician.
     */
    @Override
    public String toString() {
        return super.toString() + "availability=" + availability +
                '}';
    }
}
