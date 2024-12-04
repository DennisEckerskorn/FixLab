package com.app.fixlab.listeners;

import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Person;

public interface IOnItemRepairClickListener<T> {
    void onItemClickRepair(T item);

    void onRepairedDeviceClick(Device device);

    void OnRepairCompleted();
}
