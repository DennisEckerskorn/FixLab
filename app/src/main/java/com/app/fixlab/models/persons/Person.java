package com.app.fixlab.models.persons;

import com.app.fixlab.models.devices.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * PERSON CLASS: Father class of Client and Technician
 **/
public abstract class Person {
    private static int nextID = 0;
    private final int id;
    private final String dni;
    private final String name;
    private final String surname;
    private final String email;
    private final String phoneNumber;
    private final String address;
    private final String username;
    private final String password;
    private final List<Device> devices; //Client and technician have devices assigned

    /**
     * CONSTRUCTORS:
     **/

    public Person(String dni, String name, String surname, String email, String phoneNumber, String address, String username, String password) {
        this.id = ++nextID;
        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.username = username;
        this.password = password;
        this.devices = new ArrayList<>();
    }

    /**
     METHODS TO SHARE WITH SUB-CLASSES:
     **/

    /**
     * Adds a device to the list of devices assigned to the person
     *
     * @param device The device to be added
     */
    public void addDevice(Device device) {
        devices.add(device);
    }

    /**
     * Removes a device from the list of devices assigned to the person
     *
     * @param device The device to be removed
     */
    public void removeDevice(Device device) {
        devices.remove(device);
    }
    /**
     * Removes all devices from the list of devices assigned to the person
     */
    public void removeDevices() {
        devices.clear();
    }

    /**
     * Updates a device in the list of devices assigned to the person
     *
     * @param device The device to be updated
     */
    public void updateDevice(Device device) {
        devices.set(devices.indexOf(device), device);
    }

    /**
     * GETTERS:
     **/

    public List<Device> getDevices() {
        return devices;
    }

    public int getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * EQUALS AND HASHCODE:
     **/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(dni, person.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }
    /*
    TOSTRING:
     */

    //TODO: NEEDS ADAPTING
    @Override
    public String toString() {
        return "Person{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", devices=" + devices +
                '}';
    }

}
