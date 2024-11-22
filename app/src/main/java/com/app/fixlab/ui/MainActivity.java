package com.app.fixlab.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.app.fixlab.R;
import com.app.fixlab.listeners.FragmentNavigationListener;
import com.app.fixlab.listeners.MenuActionListener;
import com.app.fixlab.listeners.OnClickListenerClients;
import com.app.fixlab.managers.WorkshopManager;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;
import com.app.fixlab.parsers.GeneralJSONParser;
import com.app.fixlab.ui.fragments.clientfragments.ClientDetailFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientListFragment;
import com.app.fixlab.ui.fragments.SplashFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentNavigationListener, OnClickListenerClients, ClientListFragment.IClientListFragmentListener, MenuActionListener, ClientDetailFragment.IClientDetailFragmentListener {
    private Person selectedClient;
    private WorkshopManager workshopManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            workshopManager = new WorkshopManager();
            loadData();
            selectedClient = null;
            navigateToFragment(new SplashFragment(), false);
        }

        toolbarSettings();
    }

    /**
     * NAVIGATION TO FRAGMENT: Navigates to a fragment
     *
     * @param fragment       Fragment to navigate to
     * @param addToBackStack Whether to add the fragment to the back stack, or not
     */
    @Override
    public void navigateToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.fcvMain, fragment);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    /**
     * LOAD DATA: Loads data of clients, devices and technicians from JSON files
     */
    private void loadData() {
        loadClientsAndDevices();
        loadTechnicians();
    }


    /**
     * LOAD CLIENTS AND DEVICES: Loads clients with their corresponging devices/s from JSON file
     */
    private void loadClientsAndDevices() {
        List<Person> clients = new ArrayList<>();
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

                if (clientsJSONObject.has("devices")) {
                    JSONArray devicesJSON = clientsJSONObject.getJSONArray("devices");
                    for (int j = 0; j < devicesJSON.length(); j++) {
                        JSONObject devicesJSONObject = devicesJSON.getJSONObject(j);
                        String model = devicesJSONObject.getString("model");
                        String serialNumber = devicesJSONObject.getString("serialNumber");
                        String description = devicesJSONObject.getString("description");
                        String brand = devicesJSONObject.getString("brand");
                        String status = devicesJSONObject.getString("status");
                        String type = devicesJSONObject.getString("type");

                        Device device = new Device(model, serialNumber, description, brand, status, type);
                        client.addDevice(device);
                    }
                }
                clients.add(client);
                workshopManager.addPerson(client);
            }
            Log.d("Client", clients.toString());
            Log.d("Device", clients.get(0).getDevices().toString());
            Toast.makeText(this, "Clients loaded: " + clients.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("Error loading clients and devices", e.toString());
        }
    }

    /**
     * LOAD TECHNICIANS: Loads technicians from JSON file
     */
    private void loadTechnicians() {
        List<Person> technicians = new ArrayList<>();
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
                workshopManager.addPerson(technician);
            }

            Log.d("Technician", technicians.toString());
            Toast.makeText(this, "Technicians loaded: " + technicians.size(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("Error loading technicians", e.toString());
        }
    }

    /**
     * TOOLBAR SETTINGS: Sets the toolbar
     */
    private void toolbarSettings() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    /**
     * ON CLICK: Sets the selected client
     *
     * @param client Client that was clicked
     */
    @Override
    public void onClick(Person client) {
        selectedClient = client;
        //Navegar al fragmento de detalle del cliente:
        Toast.makeText(this, "Client selected: " + client.getName(), Toast.LENGTH_SHORT).show();
        navigateToFragment(new ClientDetailFragment(), true);


    }


    /*
     * MENU ACTION LISTENERS:
     */

    /**
     * ON CLIENTS SELECTED: Navigates to the clients fragment
     */
    @Override
    public void onClientsSelected() {
        navigateToFragment(new ClientFragment(), true);
    }

    /**
     * ON DEVICES SELECTED: Navigates to the devices fragment
     */
    @Override
    public void onDevicesSelected() {

    }

    /**
     * ON TECHNICIANS SELECTED: Navigates to the technicians fragment
     */
    @Override
    public void onTechniciansSelected() {

    }

    /**
     * ON START REPARATION SELECTED: Navigates to the start reparation fragment
     */
    @Override
    public void onStartReparationSelected() {

    }

    /*
     * GETTERS AND SETTERS:
     */

    /**
     * GET CLIENTS: Returns a list of clients, or an empty list if clients is null
     *
     * @return List of clients
     */
    @Override
    public List<Person> getClients() {
        List<Person> clients = (List<Person>) workshopManager.getAllClients();
        return clients == null ? Collections.emptyList() : clients;
    }


    /**
     * GET Person (client) from ClientDetailFragment
     * @return
     */
    @Override
    public Person getClient() {
        return selectedClient;
    }
}