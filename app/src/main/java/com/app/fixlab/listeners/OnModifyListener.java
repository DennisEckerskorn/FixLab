package com.app.fixlab.listeners;

import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Technician;

public interface OnModifyListener {
    void onClientModify(Client client);
    void onTechnicianModify(Technician technician);
    void onDeviceModify(Device device);
}
