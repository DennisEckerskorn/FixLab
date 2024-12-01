package com.app.fixlab.listeners;

import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Person;

import java.util.List;

/**
 * Interface which can be used for all Clients, Technicians or Devices clicks
 * @param <T> Person or Device
 */
public interface IonItemClickListenerGeneric<T> {
    void onItemClick(T item);
}

