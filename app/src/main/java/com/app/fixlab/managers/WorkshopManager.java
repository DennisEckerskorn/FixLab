package com.app.fixlab.managers;

import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;

import java.util.ArrayList;
import java.util.List;

public class WorkshopManager {
    private final List<Device> allDevices;
    private final List<Person> allTechnicians;
    private final List<Person> allClients;

    public WorkshopManager() {
        allDevices = new ArrayList<>();
        allTechnicians = new ArrayList<>();
        allClients = new ArrayList<>();
    }

    /**
     * @param person can be a instance of Person
     * @return true if added successfully
     */
    public boolean addPerson(Person person) {
        if (person instanceof Technician) {
            allTechnicians.add(person);
        } else if (person instanceof Client) {
            allClients.add(person);
        }
        allDevices.addAll(person.getDevices());
        return true;
    }

    /**
     * @param person can be a instance of Person
     * @return true if removed successfully or false if not exists
     */
    public boolean removePerson(Person person) {
        if (person instanceof Technician && allTechnicians.contains(person)) {
            allTechnicians.remove(person);
            allDevices.remove(person.getDevices());
            return true;
        } else if (person instanceof Client && allClients.contains(person)) {
            allClients.remove(person);
            allDevices.remove(person.getDevices());
            return true;
        }
        return false;
    }

    /**
     * @param device can be a instance of Device
     * @return true if added successfully
     */
    public boolean addDevice(Device device) {
        allDevices.add(device);
        return true;
    }
    /**
     * @param device can be a instance of Device
     * @return true if removed successfully or false if not exists
     */
    public boolean removeDevice(Device device) {
        if (allDevices.contains(device)) {
            allDevices.remove(device);
            return true;
        }
        return false;
    }

    /**
     * @param name String name of client
     * @return all clients by that name or null if there is no similar names
     */
    public List<Person> getClientsByName(String name) {
        List<Person> results = new ArrayList<>();

        for (int i = 0; i < allClients.size(); i++) {
            if (allClients.get(i).getName().equals(name)) {
                results.add(allClients.get(i));
            }
        }
        if (results.isEmpty()) {
            return null;
        }
        return results;
    }

    /**
     * @param dni String dni of client
     * @return all clients by that name or null if dni doesnt exist
     */

    public Person getClientsByDNI(String dni) {
        Person result = null;
        for (int i = 0; i < allClients.size(); i++) {
            if (allClients.get(i).getDni().equals(dni)) {
                result = allClients.get(i);
            }
        }
        return result;
    }

    /**
     * @param device device for search
     * @return first Client with the same device or null if the device doesnt exist
     */
    public Person getClientByDevice(Device device) {
        Person client = null;
        for (int i = 0; i < allClients.size(); i++) {
            for (int j = 0; j < allClients.get(i).getDevices().size(); j++) {
                //si el dispositivo device es igual al dispositivo(j) del cliente (i) devuelve
                if (device.equals(allClients.get(i).getDevices().get(j))) {
                    client = allClients.get(i);
                }
            }
        }
        return client;
    }

    /**
     * @param model String that contains the same model name
     * @return list of clients that have the exact model name or null if model doesnt exist
     */
    public List<Person> getClientsByDeviceModel(String model) {
        List<Device> devices = new ArrayList<>();
        for (int i = 0; i < allDevices.size(); i++) {
            if (model.equals(allDevices.get(i).getModel())) {
                devices.add(allDevices.get(i));
            }
        }
        List<Person> result = new ArrayList<>();
        for (int i = 0; i < devices.size(); i++) {
            result.add(getClientByDevice(devices.get(i)));
        }
        if (result.isEmpty())
            return null;
        return result;
    }

    /**
     * @param serialNumber String that contains the same serial number
     * @return first client that have the exact model serial number or null if model doesnt exist
     */
    public Person getClientByDeviceSerialNumber(String serialNumber) {
        Device device = null;
        for (int i = 0; i < allDevices.size(); i++) {
            if (serialNumber.equals(allDevices.get(i).getSerialNumber())) {
                device = allDevices.get(i);
            }
        }
        if (device != null) {
            return getClientByDevice(device);
        }
        return null;
    }

    public List<Device> getAllDevices() {
        return allDevices;
    }

    public List<Person> getAllClients() {
        return allClients;
    }

    public List<Person> getAllTechnicians() {
        return allTechnicians;
    }

    /**
     * Method to obtain all devices assigned to each client.
     *
     * @param client Person client
     * @return List<Device> list of devices of each client.
     */
    public List<Device> getDeviceOffClient(Person client) {
        if (client instanceof Client) {
            return client.getDevices();
        }
        return new ArrayList<>();
    }
}
