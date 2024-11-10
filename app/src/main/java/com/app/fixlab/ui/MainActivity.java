package com.app.fixlab.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.fixlab.R;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;
import com.app.fixlab.parsers.GeneralJSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<Person> clients;
    private List<Person> technicians;
    private List<Device> devices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadData();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * LOAD DATA: Loads data of clients, devices and technicians from JSON files
     */
    private void loadData() {
        loadClients();
        loadDevices();
        loadTechnicians();
    }

    /**
     * LOAD CLIENTS: Loads clients from JSON file
     */
    private void loadClients() {
        clients = new ArrayList<>();
        try {
            JSONArray clientsJSON = GeneralJSONParser.readJSONArray(this, R.raw.random_clients);
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
                clients.add(client);

            }
            Log.d("Client", clients.toString());
            Toast.makeText(this, "Clients loaded: " + clients.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("Error loading clients and devices", e.getMessage());
        }
    }

    /**
     * LOAD DEVICES: Loads devices from JSON file
     */
    private void loadDevices() {
        devices = new ArrayList<>();
        try {
            JSONArray devicesJSONs = GeneralJSONParser.readJSONArray(this, R.raw.random_devices);
            for (int i = 0; i < devicesJSONs.length(); i++) {
                JSONObject devicesJSONObject = devicesJSONs.getJSONObject(i);
                String model = devicesJSONObject.getString("model");
                String serialNumber = devicesJSONObject.getString("serialNumber");
                String description = devicesJSONObject.getString("description");
                String brand = devicesJSONObject.getString("brand");
                String status = devicesJSONObject.getString("status");
                String type = devicesJSONObject.getString("type");

                Device device = new Device(model, serialNumber, description, brand, status, type);
                devices.add(device);
            }

            Log.d("Device", devices.toString());
            Toast.makeText(this, "Devices loaded: " + devices.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("Error loading devices", e.getMessage());
        }
    }

    /**
     * LOAD TECHNICIANS: Loads technicians from JSON file
     */
    private void loadTechnicians() {
        technicians = new ArrayList<>();
        try {
            JSONArray techniciansJSON = GeneralJSONParser.readJSONArray(this, R.raw.random_technicians);
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
            Toast.makeText(this, "Technicians loaded: " + technicians.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("Error loading technicians", e.getMessage());
        }
    }


}