package com.app.fixlab.managers;

import android.content.Context;

import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;
import com.app.fixlab.parsers.DataManager;

import java.util.ArrayList;
import java.util.List;

public class WorkshopManager {
    private final List<Device> allDevices;
    private final List<Person> allTechnicians;
    private final List<Person> allClients;
    private final Context context;
    private final DataManager dataManager;
    public WorkshopManager(Context context) {
        dataManager = DataManager.getInstance();
        allDevices = new ArrayList<>();
        allTechnicians = new ArrayList<>();
        allClients = new ArrayList<>();
        this.context = context;
    }

    public int loadClients(){
        return this.addPersons(dataManager.loadTechnicians(context));
    }
    public int loadTechnicians(){
        return this.addPersons(dataManager.loadClientsAndDevices(context));
    }
    /**
     * @param persons array of persons to add
     * @return number of persons added successfully
     */
    public int addPersons(List<Person> persons) {
        int count = 0;
        for (int i = 0; i < persons.size(); i++) {
            if(addPerson(persons.get(i)))
                count++;
        }
        return count;
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

    /**
     * Actualiza los datos de una persona existente manteniendo la misma referencia en la lista correspondiente.
     * @param oldPerson La persona existente que se actualizará.
     * @param newPerson La nueva persona con los datos actualizados.
     * @return true si la persona se actualizó correctamente, false en caso contrario.
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

    // Actualiza los datos dispositivos de una persona existente manteniendo la misma referencia en la lista correspondiente.
    public void updateDevices(List<Device> oldDevices, List<Device> newDevices){
        for (int i = 0; i < oldDevices.size(); i++) {
            for (int j = 0; j < newDevices.size(); j++) {
                if (oldDevices.get(i).equals(newDevices.get(j))){
                    int index = allDevices.indexOf(oldDevices.get(i));
                    allDevices.set(index, newDevices.get(j));
                }
            }
        }
    }

    // Actualiza los datos de un dispositivo existente manteniendo la misma referencia en la lista correspondiente.
    public boolean updateDevice(Device oldDevice, Device newDevice) {
        int index = allDevices.indexOf(oldDevice);
        allDevices.set(index, newDevice);
        return true;
    }

    /**
     * Metodo para buscar el dispositivo en el cliente y actualizarlo
     * @param updatedDevice el dispositivo actualizado
     * @param clientDevices la lista de dispositivos del cliente
     */
    public void updateDeviceInClient(Device selectedDevice ,Device updatedDevice, List<Device> clientDevices) {
        // Buscar el dispositivo en el cliente y actualizarlo
        for (int i = 0; i < clientDevices.size(); i++) {
            Device currentDevice = clientDevices.get(i);
            if (currentDevice != null && currentDevice.getModel() != null &&
                    currentDevice.getModel().equals(selectedDevice.getModel())) {
                clientDevices.set(i, updatedDevice);
                break;
            }
        }
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
    public void saveData(){

    }
}
