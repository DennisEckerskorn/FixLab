package com.app.fixlab.models.persons;

import java.io.Serializable;

/**
 * TECHNI CIAN CLASS: Child class of Person
 */
public class Technician extends Person implements Serializable {
    public enum Availability {AVAILABLE, UNAVAILABLE}

    private Availability availability;

    public Technician(String dni, String name, String surname, String email, String phoneNumber, String address, String username, String password, String availability) {
        super(dni, name, surname, email, phoneNumber, address, username, password);
        this.availability = Availability.valueOf(availability.toUpperCase());
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return super.toString() + "availability=" + availability +
                '}';
    }
}
