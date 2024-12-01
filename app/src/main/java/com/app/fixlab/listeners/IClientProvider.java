package com.app.fixlab.listeners;

import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Person;

import java.util.List;

public interface IClientProvider {
    List<Person> getClients();
}

