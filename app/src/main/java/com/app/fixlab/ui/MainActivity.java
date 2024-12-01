package com.app.fixlab.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.fixlab.R;
import com.app.fixlab.listeners.MenuActionListener;
import com.app.fixlab.listeners.OnClickListenerClients;
import com.app.fixlab.listeners.OnClickListenerDevices;
import com.app.fixlab.listeners.OnClickListenerTechnicianRepairSelection;
import com.app.fixlab.listeners.OnClickListenerTechnicians;
import com.app.fixlab.listeners.OnClickRepairTechnician;
import com.app.fixlab.listeners.OnSaveAddClient;
import com.app.fixlab.listeners.OnSaveAddDevice;
import com.app.fixlab.listeners.OnSaveAddTechnician;
import com.app.fixlab.listeners.OnSplashDelayFinished;
import com.app.fixlab.managers.WorkshopManager;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.devices.DeviceCondition;
import com.app.fixlab.models.devices.DeviceType;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;
import com.app.fixlab.parsers.GeneralJSONParser;
import com.app.fixlab.ui.fragments.MainMenuFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientDetailFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientListFragment;
import com.app.fixlab.ui.fragments.SplashFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceDetailFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceListFragment;
import com.app.fixlab.ui.fragments.repairfragments.TechnicianSelectionFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianDetailFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianListFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListenerClients, ClientListFragment.IClientListFragmentListener,
        MenuActionListener, ClientDetailFragment.IClientDetailFragmentListener, OnSaveAddClient, OnClickListenerTechnicians,
        TechnicianDetailFragment.ITechniciantDetailFragmentListener, TechnicianListFragment.ITechnicianListFragmentListener, OnSaveAddTechnician, OnSplashDelayFinished,
        TechnicianSelectionFragment.ITechnicianSelectionFragmentListener, OnClickListenerTechnicianRepairSelection, OnClickRepairTechnician, OnClickListenerDevices, DeviceListFragment.IDeviceListFragmentListener, DeviceDetailFragment.IDeviceDetailFragmentListener, OnSaveAddDevice {
    private Person selectedClient;
    private Person selectedTechnician;
    private WorkshopManager workshopManager;
    private FragmentManager fragmentManager;
    private static final long SPLASH_SCREEN_DELAY = 3000;
    private static final String CLIENT_KEY = "SELECTED_CLIENT";
    private static final String TECHNICIAN_KEY = "SELECTED_TECHNICIAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            workshopManager = new WorkshopManager();
            loadData();
            selectedClient = null;
            selectedTechnician = null;
            navigateToFragment(new SplashFragment(), false);
        } else {
            selectedClient = (Person) savedInstanceState.getSerializable(CLIENT_KEY);
            selectedTechnician = (Person) savedInstanceState.getSerializable(TECHNICIAN_KEY);
        }

        toolbarSettings();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CLIENT_KEY, (Serializable) selectedClient);
        outState.putSerializable(TECHNICIAN_KEY, (Serializable) selectedTechnician);
    }

    /**
     * NAVIGATION TO FRAGMENT: Navigates to a fragment
     *
     * @param fragment       Fragment to navigate to
     * @param addToBackStack Whether to add the fragment to the back stack, or not
     */
    public void navigateToFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction()
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

    /**
     * ON CLICK: Sets the selected client
     *
     * @param technician Technician that was clicked
     */
    @Override
    public void onTechniciansClick(Person technician) {
        selectedTechnician = technician;
        // Toast para feedback visual
        Toast.makeText(this, "Technician selected: " + technician.getName(), Toast.LENGTH_SHORT).show();
        // Navegar al fragmento de detalle del técnico
        navigateToFragment(new TechnicianDetailFragment(), true);
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
        navigateToFragment(new DeviceFragment(), true);
    }

    /**
     * ON TECHNICIANS SELECTED: Navigates to the technicians fragment
     */
    @Override
    public void onTechniciansSelected() {
        navigateToFragment(new TechnicianFragment(), true);
    }

    /**
     * ON START REPARATION SELECTED: Navigates to the start reparation fragment
     */
    @Override
    public void onStartReparationSelected() {
        navigateToFragment(new TechnicianSelectionFragment(), true);
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
        List<Person> clients = workshopManager.getAllClients();
        return clients == null ? Collections.emptyList() : clients;
    }


    /**
     * GET Person (client) from ClientDetailFragment
     *
     * @return
     */
    @Override
    public Person getClient() {
        return selectedClient;
    }

    /**
     * GET Technicians: Returns a list of clients, or an empty list if clients is null
     *
     * @return List of technicians
     */
    @Override
    public List<Person> getTechnicians() {
        List<Person> technicians = workshopManager.getAllTechnicians();
        return technicians == null ? Collections.emptyList() : technicians;
    }

    /**
     * GET Person (technician) from TechnicianDetailFragment
     *
     * @return Person (technician)
     */
    @Override
    public Person getTechnician() {
        return selectedTechnician;
    }

    //REPAIR SECTION:

    /**
     * GET TECHNICIANS FOR SELECTION: Returns a list of technicians, or an empty list if technicians is null
     *
     * @return List of technicians
     */
    @Override
    public List<Person> getTechniciansForSelection() {
        List<Person> technicians = workshopManager.getAllTechnicians();
        return technicians == null ? Collections.emptyList() : technicians;
    }

    @Override
    public void onRepairTechniciansClick(Person technician) {
        Toast.makeText(this, "Technician selected: " + technician.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickRepairTechnician(Person technician) {

    }

    /**
     * ON SAVE ADD CLIENT: Saves the client
     */
    @Override
    public void onSaveAddClient(Client client) {
        if (client != null) {
            workshopManager.addPerson(client);
            Toast.makeText(this, "Client saved: " + client.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Client not saved", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * OnSaveAddTechnician: Saves the technician
     */
    @Override
    public void onSaveAddTechnician(Technician technician) {
        if (technician != null) {
            workshopManager.addPerson(technician);
            Toast.makeText(this, "Technician saved: " + technician.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Technician not saved", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ON SPLASH DELAY FINISHED: Navigates to the main menu fragment
     */
    @Override
    public void onSPlashDelayFinished() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            navigateToFragment(new MainMenuFragment(), false);
        }, SPLASH_SCREEN_DELAY);
    }

    @Override
    public void onClick(Device device) {

    }

    @Override
    public Device getDevice() {
        return null;
    }

    @Override
    public List<Device> getDevices() {
        return workshopManager.getAllDevices();
    }

    @Override
    public void onSaveAddDevice(Device device) {
    }
}