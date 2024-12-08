package com.app.fixlab.parsers;


import android.content.Context;
import android.util.Log;

import com.app.fixlab.managers.WorkshopManager;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.devices.DeviceCondition;
import com.app.fixlab.models.devices.DeviceType;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class TestSaveData extends WorkshopManager {
    private final List<Device> allDevices;
    private final List<Person> allTechnicians;
    private final List<Person> allClients;
    private final Context context;

    private final String DATA_FILE_NAME = "workshop_data.json";

    public TestSaveData(Context context) {
        super(context);
        allDevices = new ArrayList<>();
        allTechnicians = new ArrayList<>();
        allClients = new ArrayList<>();
        this.context = context;
        loadData();
    }

    // Sobrescribir addPerson para guardar automáticamente
    @Override
    public boolean addPerson(Person person) {
        boolean added = super.addPerson(person);
        if (added) {
            saveData();
        }
        return added;
    }

    // Sobrescribir addDevice para guardar automáticamente
    @Override
    public boolean addDevice(Device device) {
        boolean added = super.addDevice(device);
        if (added) {
            saveData();
        }
        return added;
    }

    // Método para guardar los datos en JSON
    public void saveData() {
        try {
            JSONObject data = new JSONObject();

            // Guardar técnicos
            JSONArray techniciansArray = new JSONArray();
            for (Person technician : allTechnicians) {
                techniciansArray.put(personToJson(technician));
            }
            data.put("technicians", techniciansArray);

            // Guardar clientes y dispositivos
            JSONArray clientsArray = new JSONArray();
            for (Person client : allClients) {
                JSONObject clientJson = personToJson(client);
                JSONArray devicesArray = new JSONArray();
                for (Device device : client.getDevices()) {
                    devicesArray.put(deviceToJson(device));
                }
                clientJson.put("devices", devicesArray);
                clientsArray.put(clientJson);
            }
            data.put("clients", clientsArray);

            // Escribir JSON al archivo
            File file = new File(context.getFilesDir(), DATA_FILE_NAME);
            FileWriter writer = new FileWriter(file);
            writer.write(data.toString());
            writer.close();
            Log.d("WorkshopManager", "Datos guardados en JSON");
        } catch (Exception e) {
            Log.e("WorkshopManager", "Error al guardar los datos", e);
        }
    }

    // Método para cargar los datos desde JSON
    public void loadData() {
        try {
            File file = new File(context.getFilesDir(), DATA_FILE_NAME);
            if (!file.exists()) {
                Log.d("WorkshopManager", "Archivo de datos no encontrado, iniciando vacío.");
                return;
            }

            FileReader reader = new FileReader(file);
            char[] buffer = new char[(int) file.length()];
            reader.read(buffer);
            reader.close();

            String jsonString = new String(buffer);
            JSONObject data = new JSONObject(jsonString);

            // Cargar técnicos
            JSONArray techniciansArray = data.getJSONArray("technicians");
            for (int i = 0; i < techniciansArray.length(); i++) {
                Person technician = jsonToPerson(techniciansArray.getJSONObject(i));
                addPerson(technician);
            }

            // Cargar clientes y dispositivos
            JSONArray clientsArray = data.getJSONArray("clients");
            for (int i = 0; i < clientsArray.length(); i++) {
                JSONObject clientJson = clientsArray.getJSONObject(i);
                Person client = jsonToPerson(clientJson);

                if (clientJson.has("devices")) {
                    JSONArray devicesArray = clientJson.getJSONArray("devices");
                    for (int j = 0; j < devicesArray.length(); j++) {
                        Device device = jsonToDevice(devicesArray.getJSONObject(j));
                        client.addDevice(device);
                    }
                }
                addPerson(client);
            }

            Log.d("WorkshopManager", "Datos cargados desde JSON");
        } catch (Exception e) {
            Log.e("WorkshopManager", "Error al cargar los datos", e);
        }
    }

    // Método para convertir un objeto Person a JSON
    private JSONObject personToJson(Person person) throws Exception {
        JSONObject json = new JSONObject();
        json.put("dni", person.getDni());
        json.put("name", person.getName());
        json.put("surname", person.getSurname());
        json.put("email", person.getEmail());
        json.put("phoneNumber", person.getPhoneNumber());
        json.put("address", person.getAddress());
        json.put("username", person.getUsername());
        json.put("password", person.getPassword());
        json.put("type", person instanceof Technician ? "technician" : "client");
        return json;
    }

    // Método para convertir un objeto Device a JSON
    private JSONObject deviceToJson(Device device) throws Exception {
        JSONObject json = new JSONObject();
        json.put("model", device.getModel());
        json.put("serialNumber", device.getSerialNumber());
        json.put("description", device.getDescription());
        json.put("brand", device.getBrand());
        json.put("type", device.getType().name());
        json.put("condition", device.getCondition().name());
        return json;
    }

    // Método para convertir JSON a objeto Person
    private Person jsonToPerson(JSONObject json) throws Exception {
        String dni = json.getString("dni");
        String name = json.getString("name");
        String surname = json.getString("surname");
        String email = json.getString("email");
        String phoneNumber = json.getString("phoneNumber");
        String address = json.getString("address");
        String username = json.getString("username");
        String password = json.getString("password");
        String type = json.getString("type");

        if (type.equals("technician")) {
            return new Technician(dni, name, surname, email, phoneNumber, address, username, password, "Available");
        } else {
            return new Client(dni, name, surname, email, phoneNumber, address, username, password);
        }
    }

    // Método para convertir JSON a objeto Device
    private Device jsonToDevice(JSONObject json) throws Exception {
        String model = json.getString("model");
        String serialNumber = json.getString("serialNumber");
        String description = json.getString("description");
        String brand = json.getString("brand");
        DeviceType type = DeviceType.valueOf(json.getString("type").toUpperCase());
        DeviceCondition condition = DeviceCondition.valueOf(json.getString("condition").toUpperCase());
        return new Device(model, serialNumber, description, brand, type, condition);
    }
}

