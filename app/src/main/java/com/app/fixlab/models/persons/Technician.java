package com.app.fixlab.models.persons;

/**
 * TECHNICIAN CLASS: Child class of Person
 */
public class Technician extends Person {
    public enum Availability {AVAILABLE, UNAVAILABLE}

    private final Availability availability;

    public Technician(String dni, String name, String surname, String email, String phoneNumber, String address, String username, String password, String availability) {
        super(dni, name, surname, email, phoneNumber, address, username, password);
        this.availability = Availability.valueOf(availability.toUpperCase());
    }

    public Availability getAvailability() {
        return availability;
    }

    @Override
    public String toString() {
        return super.toString() + "availability=" + availability +
                '}';
    }
}
