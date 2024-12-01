package com.app.fixlab.listeners;

import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;

import java.util.List;

public interface IdataProvider {
    List<Person> getClientData();
    List<Person> getTechnicianData();
    List<Device> getDeviceData();
    List<Device> getDeviceOfClient();
    Person getClient();
    Person getTechnician();
    Device getDevice();
}
