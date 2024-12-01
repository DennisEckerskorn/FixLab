package com.app.fixlab.listeners;

import com.app.fixlab.models.devices.Device;

import java.util.List;

public interface IDeviceProvider {
    List<Device> getDevices();
    List<Device> getDevicesOfClient();
}
