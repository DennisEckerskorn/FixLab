package com.app.fixlab.parsers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.app.fixlab.R;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.devices.DeviceCondition;
import com.app.fixlab.models.devices.DeviceType;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private DataManager() {

    }
    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return instance;
    }


    /**
     * LOAD TECHNICIANS: Loads technicians from JSON file
     */
    public List<Person> loadTechnicians(Context context) {
        List<Person> technicians = new ArrayList<>();
        try {
            JSONArray techniciansJSON = GeneralJSONParser.readJSONArray(context, R.raw.random_technicians);
            for (int i = 0; i < techniciansJSON.length(); i++) {
                JSONObject techniciansJSONObject = techniciansJSON.getJSONObject(i);
                String dni = techniciansJSONObject.getString("dni");
                String name = techniciansJSONObject.getString("name");
                String surname = techniciansJSONObject.getString("surname");
                String email = techniciansJSONObject.getString("email");
                String phoneNumber = techniciansJSONObject.getString("phoneNumber");
                String address = techniciansJSONObject.getString("address");
                String username = techniciansJSONObject.getString("username");
                String password = techniciansJSONObject.getString("password");
                String availability = techniciansJSONObject.getString("availability");

                Person technician = new Technician(dni, name, surname, email, phoneNumber, address, username, password, availability);
                technicians.add(technician);
            }

            Log.d("Technician", technicians.toString());
        } catch (Exception e) {
            Log.d("Error loading technicians", e.toString());
        }
        return technicians;
    }

    public List<Person> loadClientsAndDevices(Context context) {
        List<Person> clients = new ArrayList<>();
        try {
            JSONArray clientsJSON = GeneralJSONParser.readJSONArray(context, R.raw.random_clients);
            for (int i = 0; i < clientsJSON.length(); i++) {
                JSONObject clientsJSONObject = clientsJSON.getJSONObject(i);
                String dni = clientsJSONObject.getString("dni");
                String name = clientsJSONObject.getString("name");
                String surname = clientsJSONObject.getString("surname");
                String email = clientsJSONObject.getString("email");
                String phoneNumber = clientsJSONObject.getString("phoneNumber");
                String address = clientsJSONObject.getString("address");
                String username = clientsJSONObject.getString("username");
                String password = clientsJSONObject.getString("password");

                Person client = new Client(dni, name, surname, email, phoneNumber, address, username, password);

                if (clientsJSONObject.has("devices")) {
                    JSONArray devicesJSON = clientsJSONObject.getJSONArray("devices");
                    for (int j = 0; j < devicesJSON.length(); j++) {
                        JSONObject devicesJSONObject = devicesJSON.getJSONObject(j);
                        String model = devicesJSONObject.getString("model");
                        String serialNumber = devicesJSONObject.getString("serialNumber");
                        String description = devicesJSONObject.getString("description");
                        String brand = devicesJSONObject.getString("brand");
                        DeviceType type;
                        DeviceCondition condition;
                        try {
                            type = DeviceType.valueOf(devicesJSONObject.getString("type").toUpperCase());
                            condition = DeviceCondition.valueOf(devicesJSONObject.getString("condition").toUpperCase());
                        } catch (IllegalArgumentException e) {
                            // Manejo de error si el valor de "type" no es válido
                            System.err.println("Tipo de dispositivo no válido: " + devicesJSONObject.getString("type"));
                            System.err.println("Condición de dispositivo no válida: " + devicesJSONObject.getString("condition"));
                            condition = DeviceCondition.IN_GOOD_CONDITION;
                            type = DeviceType.OTHER; // Valor por defecto en caso de error
                        }
                        Device device = new Device(model, serialNumber, description, brand, type, condition);
                        client.addDevice(device);
                    }
                }
                clients.add(client);
            }
            Log.d("Client", clients.toString());
            Log.d("Device", clients.get(0).getDevices().toString());
        } catch (Exception e) {
            Log.d("Error loading clients and devices", e.toString());
        }
        return clients;
    }

}
