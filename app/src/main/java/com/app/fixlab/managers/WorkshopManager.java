package com.app.fixlab.managers;

import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;

import java.util.ArrayList;
import java.util.List;

public class WorkshopManager {
    private List<Device> allDevices;
    private List<Device> allClientDevices;
    private List<Device> allTechnicianDevices;
    private List<Person> allPersons;
    private List<Technician> allTechnicians;
    private List<Client> allClients;

    public WorkshopManager() {
        allDevices = new ArrayList<>();
        allClientDevices = new ArrayList<>();
        allTechnicianDevices = new ArrayList<>();
        allPersons = new ArrayList<>();
        allTechnicians = new ArrayList<>();
        allClients = new ArrayList<>();
    }

    /**
     *
     * @param person can be a instance of Person
     * @return true if added successfully
     */
    public boolean addPerson(Person person){
        allPersons.add(person);
        allDevices.addAll(person.getDevices());
        if (person instanceof Technician){
            allTechnicians.add((Technician) person);
            allTechnicianDevices.addAll(person.getDevices());
        } else if(person instanceof Client) {
            allClients.add((Client) person);
            allClientDevices.addAll(person.getDevices());
        }
        return true;
    }

    /**
     *
     * @param person can be a instance of Person
     * @return true if removed successfully or false if not exists
     */
    public boolean removePerson(Person person) {
        if (person instanceof Technician && allPersons.contains(person) && allTechnicians.contains(person)) {
            allTechnicians.remove(person);
            allPersons.remove(person);
            allDevices.remove(person.getDevices());
            allTechnicianDevices.remove(person.getDevices());
            return true;
        } else if (person instanceof Client && allPersons.contains(person) && allClients.contains(person)) {
            allPersons.remove(person);
            allDevices.remove(person.getDevices());
            allClients.remove(person);
            allDevices.remove(person.getDevices());
            return true;
        }
        return false;
    }

    /**
     *
     * @param name String name of client
     * @return all clients by that name
     */
    public List<Client> getClientsByName(String name){
        List<Client> results = new ArrayList<>();
        for (int i = 0; i < allClients.size() ; i++) {
            if(allClients.get(i).getName().equals(name)){
                results.add(allClients.get(i));
            }
        }
        if (results.isEmpty()){
            return null;
        }
        return results;
    }
}
