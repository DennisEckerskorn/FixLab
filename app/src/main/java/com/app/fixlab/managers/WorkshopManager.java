package com.app.fixlab.managers;

import android.content.Context;

import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;
import com.app.fixlab.models.repair.Repair;
import com.app.fixlab.parsers.DataManager;

import java.util.ArrayList;
import java.util.List;

public class WorkshopManager {
    private final List<Device> allDevices;
    private final List<Person> allTechnicians;
    private final List<Person> allClients;
    private final Context context;
    private final DataManager dataManager;
    private final List<Repair> allRepairs;

    public WorkshopManager(Context context) {
        dataManager = DataManager.getInstance();
        allDevices = new ArrayList<>();
        allTechnicians = new ArrayList<>();
        allClients = new ArrayList<>();
        allRepairs = new ArrayList<>();
        this.context = context;
    }

    /**
     * Loads all clients and their associated data.
     *
     * @return the number of clients loaded.
     */
    public int loadClients() {
        return this.addPersons(dataManager.loadTechnicians(context));
    }

    /**
     * Loads all technicians and their associated data.
     *
     * @return the number of technicians loaded.
     */
    public int loadTechnicians() {
        return this.addPersons(dataManager.loadClientsAndDevices(context));
    }

    /**
     * Adds a list of persons to the manager.
     *
     * @param persons list of persons to add.
     * @return the number of persons successfully added.
     */
    public int addPersons(List<Person> persons) {
        int count = 0;
        for (int i = 0; i < persons.size(); i++) {
            if (addPerson(persons.get(i)))
                count++;
        }
        return count;
    }

    /**
     * Adds a single person (client or technician) and their devices.
     *
     * @param person the person to add.
     * @return true if the person was successfully added.
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
     * Removes a person (client or technician) and their devices.
     *
     * @param person the person to remove.
     * @return true if the person was successfully removed, false otherwise.
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
     * Adds a single device.
     *
     * @param device the device to add.
     * @return true if the device was successfully added.
     */
    public boolean addDevice(Device device) {
        allDevices.add(device);
        return true;
    }

    /**
     * Adds a repair record to the manager.
     *
     * @param repair the repair record to add.
     */
    public void addRepair(Repair repair) {
        if (repair != null) {
            allRepairs.add(repair);
        }
    }

    /**
     * Removes a device and updates its associated client.
     *
     * @param device the device to remove.
     * @return true if the device was successfully removed, false otherwise.
     */
    public boolean removeDevice(Device device) {
        if (allDevices.contains(device) && this.getClientByDevice(device) != null) {
            Person client = this.getClientByDevice(device);
            allDevices.remove(device);
            client.removeDevice(device);
            return true;
        }
        return false;
    }

    /**
     * Finds clients by their name.
     *
     * @param name the name of the client to search for.
     * @return a list of clients with the specified name or null if none exist.
     */
    public List<Person> getClientsByName(String name) {
        List<Person> results = new ArrayList<>();
        for (int i = 0; i < allClients.size(); i++) {
            if (allClients.get(i).getName().equals(name)) {
                results.add(allClients.get(i));
            }
        }
        return results.isEmpty() ? null : results;
    }

    /**
     * Finds a client by their DNI.
     *
     * @param dni the DNI of the client to search for.
     * @return the client with the specified DNI or null if not found.
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
     * Finds the client associated with a specific device.
     *
     * @param device the device to search for.
     * @return the client who owns the device or null if not found.
     */
    public Person getClientByDevice(Device device) {
        for (int i = 0; i < allClients.size(); i++) {
            for (int j = 0; j < allClients.get(i).getDevices().size(); j++) {
                if (device.equals(allClients.get(i).getDevices().get(j))) {
                    return allClients.get(i);
                }
            }
        }
        return null;
    }

    /**
     * Finds clients by the model of their device.
     *
     * @param model the device model to search for.
     * @return a list of clients with devices of the specified model or null if none exist.
     */
    public List<Person> getClientsByDeviceModel(String model) {
        List<Device> devices = new ArrayList<>();
        for (int i = 0; i < allDevices.size(); i++) {
            if (model.equals(allDevices.get(i).getModel())) {
                devices.add(allDevices.get(i));
            }
        }
        List<Person> result = new ArrayList<>();
        for (Device device : devices) {
            result.add(getClientByDevice(device));
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Finds the client associated with a device by its serial number.
     *
     * @param serialNumber the serial number of the device to search for.
     * @return the client who owns the device or null if not found.
     */
    public Person getClientByDeviceSerialNumber(String serialNumber) {
        for (int i = 0; i < allDevices.size(); i++) {
            if (serialNumber.equals(allDevices.get(i).getSerialNumber())) {
                return getClientByDevice(allDevices.get(i));
            }
        }
        return null;
    }

    /**
     * Updates the details of an existing person.
     *
     * @param oldPerson the existing person.
     * @param newPerson the updated person.
     * @return true if the person was successfully updated, false otherwise.
     */
    public boolean updatePerson(Person oldPerson, Person newPerson) {
        if (oldPerson instanceof Client && newPerson instanceof Client) {
            newPerson.getDevices().addAll(oldPerson.getDevices());
            int index = allClients.indexOf(oldPerson);
            allClients.set(index, newPerson);
            updateDevices(oldPerson.getDevices(), newPerson.getDevices());
            return true;
        } else if (oldPerson instanceof Technician && newPerson instanceof Technician) {
            newPerson.getDevices().addAll(oldPerson.getDevices());
            int index = allTechnicians.indexOf(oldPerson);
            allTechnicians.set(index, newPerson);
            return true;
        }
        return false;
    }

    /**
     * Updates a list of devices with new data.
     *
     * @param oldDevices the existing list of devices.
     * @param newDevices the updated list of devices.
     */
    public void updateDevices(List<Device> oldDevices, List<Device> newDevices) {
        for (int i = 0; i < oldDevices.size(); i++) {
            for (int j = 0; j < newDevices.size(); j++) {
                if (oldDevices.get(i).equals(newDevices.get(j))) {
                    int index = allDevices.indexOf(oldDevices.get(i));
                    allDevices.set(index, newDevices.get(j));
                }
            }
        }
    }

    /**
     * Updates the data of a single device.
     *
     * @param device the updated device.
     * @return true if the device was successfully updated.
     */
    public boolean updateDevice(Device device) {
        int index = allDevices.indexOf(device);
        allDevices.set(index, device);
        return true;
    }

    /**
     * Updates a specific device in a client's device list.
     *
     * @param selectedDevice the current device.
     * @param updatedDevice  the updated device.
     * @param clientDevices  the list of the client's devices.
     */
    public void updateDeviceInClient(Device selectedDevice, Device updatedDevice, List<Device> clientDevices) {
        for (int i = 0; i < clientDevices.size(); i++) {
            Device currentDevice = clientDevices.get(i);
            if (currentDevice != null && currentDevice.getModel() != null &&
                    currentDevice.getModel().equals(selectedDevice.getModel())) {
                clientDevices.set(i, updatedDevice);
                break;
            }
        }
    }

    /**
     * Retrieves all devices.
     *
     * @return a list of all devices.
     */
    public List<Device> getAllDevices() {
        return allDevices;
    }

    /**
     * Retrieves all clients.
     *
     * @return a list of all clients.
     */
    public List<Person> getAllClients() {
        return allClients;
    }

    /**
     * Retrieves all technicians.
     *
     * @return a list of all technicians.
     */
    public List<Person> getAllTechnicians() {
        return allTechnicians;
    }

    /**
     * Retrieves all repair records.
     *
     * @return a list of all repair records.
     */
    public List<Repair> getAllRepairs() {
        return allRepairs;
    }
}
