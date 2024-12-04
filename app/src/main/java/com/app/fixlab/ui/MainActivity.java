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
import com.app.fixlab.listeners.IOnItemRepairClickListener;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.listeners.MenuActionListener;
import com.app.fixlab.listeners.OnCheckedChangeListener;
import com.app.fixlab.listeners.OnDeviceClickListener;
import com.app.fixlab.listeners.OnModifyListener;
import com.app.fixlab.listeners.OnSaveAddClient;
import com.app.fixlab.listeners.OnSaveAddDevice;
import com.app.fixlab.listeners.OnSaveAddTechnician;
import com.app.fixlab.listeners.OnSaveDiagnosis;
import com.app.fixlab.listeners.OnSplashDelayFinished;
import com.app.fixlab.managers.WorkshopManager;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.devices.DeviceCondition;
import com.app.fixlab.models.devices.DeviceStatus;
import com.app.fixlab.models.devices.DeviceType;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;
import com.app.fixlab.models.repair.Diagnosis;
import com.app.fixlab.models.repair.Repair;
import com.app.fixlab.parsers.GeneralJSONParser;
import com.app.fixlab.ui.fragments.MainMenuFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientDetailFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientFragment;
import com.app.fixlab.ui.fragments.SplashFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceDetailFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceFragment;
import com.app.fixlab.ui.fragments.repairfragments.DiagnosisFragment;
import com.app.fixlab.ui.fragments.repairfragments.RepairSummaryFragment;
import com.app.fixlab.ui.fragments.repairfragments.RepairedDeviceListFragment;
import com.app.fixlab.ui.fragments.repairfragments.TechnicianSelectionFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianDetailFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IonItemClickListenerGeneric<Person>, IdataProvider, IOnItemRepairClickListener, OnDeviceClickListener,
        MenuActionListener, OnSaveAddClient, OnSaveAddTechnician, OnSplashDelayFinished,
        OnSaveAddDevice, OnModifyListener, OnCheckedChangeListener, OnSaveDiagnosis {
    private Person selectedClient;
    private Person selectedTechnician;
    private Device selectedDevice;
    private Repair currentRepair;
    private WorkshopManager workshopManager;
    private FragmentManager fragmentManager;
    private static final long SPLASH_SCREEN_DELAY = 3000;
    private static final String CLIENT_KEY = "SELECTED_CLIENT";
    private static final String TECHNICIAN_KEY = "SELECTED_TECHNICIAN";
    private static final String DEVICE_KEY = "SELECTED_DEVICE";
    private static final String REPAIR_KEY = "SELECTED_REPAIR";


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
            selectedDevice = null;
            navigateToFragment(new SplashFragment(), false);
        } else {
            selectedClient = (Person) savedInstanceState.getSerializable(CLIENT_KEY);
            selectedTechnician = (Person) savedInstanceState.getSerializable(TECHNICIAN_KEY);
            selectedDevice = (Device) savedInstanceState.getSerializable(DEVICE_KEY);
            currentRepair = (Repair) savedInstanceState.getSerializable(REPAIR_KEY);
        }

        toolbarSettings();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CLIENT_KEY, (Serializable) selectedClient);
        outState.putSerializable(TECHNICIAN_KEY, (Serializable) selectedTechnician);
        outState.putSerializable(DEVICE_KEY, (Serializable) selectedDevice);
        outState.putSerializable(REPAIR_KEY, (Serializable) currentRepair);
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
     * GET CLIENT DATA: Returns the clients of the workshop
     *
     * @return List<Person> clients of the workshop
     */
    @Override
    public List<Person> getClientData() {
        List<Person> clients = workshopManager.getAllClients();
        return clients == null ? Collections.emptyList() : clients;
    }

    /**
     * GET TECHNICIAN DATA: Returns the technicians of the workshop
     *
     * @return List<Person> technicians of the workshop
     */
    @Override
    public List<Person> getTechnicianData() {
        List<Person> technicians = workshopManager.getAllTechnicians();
        return technicians == null ? Collections.emptyList() : technicians;
    }

    /**
     * GET DEVICE DATA: Returns the devices of the workshop
     *
     * @return List<Device> devices of the workshop
     */
    @Override
    public List<Device> getDeviceData() {
        List<Device> devices = workshopManager.getAllDevices();
        return devices == null ? Collections.emptyList() : devices;
    }

    /**
     * GET DEVICE OF CLIENT: Returns the devices of the selected client
     *
     * @return List<Device> devices of the selected client
     */
    @Override
    public List<Device> getDeviceOfClient() {
        if (selectedClient != null) {
            List<Device> devices = selectedClient.getDevices();
            return devices == null ? Collections.emptyList() : devices;
        }
        return Collections.emptyList();
    }


    /**
     * GET CLIENT: Returns the selected client
     *
     * @return Client selected
     */
    @Override
    public Person getClient() {
        return selectedClient;
    }

    /**
     * GET TECHNICIAN: Returns the selected technician
     *
     * @return Technician selected
     */
    @Override
    public Person getTechnician() {
        return selectedTechnician;
    }

    /**
     * GET DEVICE: Returns the selected device
     *
     * @return Device selected
     */
    @Override
    public Device getDevice() {
        return selectedDevice;
    }

    /**
     * GET REPAIR: Returns the current repair
     *
     * @return Repair current
     */
    @Override
    public Repair getRepair() {
        return currentRepair;
    }

    /*
     * LISTENERS:
     */


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

    /**
     * ON SAVE ADD DEVICE: Saves the device
     *
     * @param device Device to save
     */
    @Override
    public void onSaveAddDevice(Device device) {
        if (device != null) {
            //TODO: FALTA IMPLEMENTAR EN WORKSHOPMANAGER
            // workshopManager.addDevice(device);
            Toast.makeText(this, "Device saved: " + device.getModel(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * ON ITEM CLICK: Handles the click on an item
     *
     * @param item Item that was clicked
     */
    @Override
    public void onItemClick(Person item) {
        if (item instanceof Client) {
            selectedClient = item;
            ClientDetailFragment clientDetailFragment = new ClientDetailFragment();
            navigateToFragment(clientDetailFragment, true);
            Toast.makeText(this, "Client selected: " + selectedClient.getName(), Toast.LENGTH_SHORT).show();
        } else if (item instanceof Technician) {
            selectedTechnician = item;
            Toast.makeText(this, "Technician selected: " + selectedTechnician.getName(), Toast.LENGTH_SHORT).show();
            navigateToFragment(new TechnicianDetailFragment(), true);
        }
    }

    /**
     * ON DEVICE CLICK: Handles the click on a device
     *
     * @param device Device that was clicked
     */
    @Override
    public void onDeviceClick(Device device) {
        selectedDevice = device;
        Toast.makeText(this, "Device selected: " + device.getModel(), Toast.LENGTH_SHORT).show();
        navigateToFragment(new DeviceDetailFragment(), true);
    }

    /**
     * ON ITEM REPAIR CLICK: Handles the click on an item
     *
     * @param item Item that was clicked
     */
    @Override
    public void onItemClickRepair(Object item) {
        if (item instanceof Technician) {
            selectedTechnician = (Person) item;
            Repair repair = new Repair(selectedTechnician, null, null);
            this.currentRepair = repair;
            navigateToFragment(new RepairedDeviceListFragment(), true);
            Toast.makeText(this, "Technician selected: " + selectedTechnician.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Technician not selected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ON REPAIRED DEVICE CLICK: Handles the click on a repaired device
     *
     * @param device Device that was repaired
     */
    @Override
    public void onRepairedDeviceClick(Device device) {
        Toast.makeText(this, "Device repaired: " + device.getModel(), Toast.LENGTH_SHORT).show();
        selectedDevice = device;
        selectedClient = workshopManager.getClientByDevice(selectedDevice);

        if (currentRepair != null) {
            currentRepair.setDevice(selectedDevice);
            currentRepair.setClient((Client) selectedClient);
            currentRepair.setStatus(Repair.RepairStatus.PENDING);
            Toast.makeText(this, "Device selected for repair: " + device.getModel() + "current repair: " + currentRepair.getStatus(), Toast.LENGTH_SHORT).show();
            navigateToFragment(new DiagnosisFragment(), true);
        } else {
            Toast.makeText(this, "No device selected", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * ON CHECKED CHANGE: Handles the change of a checked item
     *
     * @param item      Item that was checked
     * @param isChecked Whether the item is checked or not
     */
    @Override
    public void onCheckedChange(Diagnosis.DiagnosisCheckItem item, boolean isChecked) {
        if (currentRepair != null && currentRepair.getDiagnosis() != null) {
            currentRepair.getDiagnosis().setCheckItem(item, isChecked);

            if (currentRepair.getDiagnosis().isAllCheckItemsCompleted()) {
                currentRepair.setStatus(Repair.RepairStatus.DIAGNOSED);
                Toast.makeText(this, "Diagnosis completed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * ON SAVE DIAGNOSIS: Saves the diagnosis
     *
     * @param diagnosis Diagnosis to save
     */
    @Override
    public void onSaveDiagnosis(Diagnosis diagnosis) {
        if (currentRepair != null && currentRepair.getDiagnosis() != null) {
            currentRepair.setDiagnosis(diagnosis);

            if (currentRepair.getDevice() != null) {
                currentRepair.getDevice().setStatus(DeviceStatus.IN_REPAIR);
            }

            currentRepair.setStatus(Repair.RepairStatus.IN_PROGRESS);

            Toast.makeText(this, "Diagnosis saved successfully", Toast.LENGTH_SHORT).show();
            navigateToFragment(new RepairSummaryFragment(), true);
        } else {
            Toast.makeText(this, "Diagnosis not saved", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO: Marcar dispositivo como COMPLETED y que se cambie en Devices
    @Override
    public void OnRepairCompleted() {
        if (currentRepair != null) {
            currentRepair.setStatus(Repair.RepairStatus.COMPLETED);
            currentRepair.getDevice().setStatus(DeviceStatus.COMPLETED);
            Toast.makeText(this, "Reparación completada", Toast.LENGTH_SHORT).show();
            navigateToFragment(new MainMenuFragment(), false);
        } else {
            Toast.makeText(this, "No se puede completar la reparación", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ON CLIENT MODIFY: Handles the modification of a client
     *
     * @param updatedClient Client to modify
     */
    @Override
    public void onClientModify(Client updatedClient) {
        if (updatedClient != null) {
            // Actualizar el cliente en el WorkshopManager
            workshopManager.removePerson(selectedClient);
            workshopManager.addPerson(updatedClient);

            // Añadir los dispositivos del cliente anterior al cliente actualizado
            updatedClient.getDevices().addAll(selectedClient.getDevices());

            // Actualizar el cliente seleccionado
            selectedClient = updatedClient;

            Toast.makeText(this, "Client modified: " + updatedClient.getName(), Toast.LENGTH_SHORT).show();

            // Reemplazar el fragmento de detalle con uno nuevo
            fragmentManager.popBackStack(); // Eliminar el fragmento actual de la pila
            navigateToFragment(new ClientDetailFragment(), false); // Cargar un nuevo fragmento con los datos actualizados
        }
    }

    /**
     * ON TECHNICIAN MODIFY: Handles the modification of a technician
     *
     * @param updatedTechnician Technician to modify
     */
    @Override
    public void onTechnicianModify(Technician updatedTechnician) {
        //TODO: IMPLEMENTAR
        if (selectedTechnician != null) {
            // Actualizar el técnico en el WorkshopManager
            workshopManager.removePerson(selectedTechnician);
            workshopManager.addPerson(updatedTechnician);
            updatedTechnician.getDevices().addAll(selectedTechnician.getDevices());
            selectedTechnician = updatedTechnician;
            Toast.makeText(this, "Technician modified: " + updatedTechnician.getName(), Toast.LENGTH_SHORT).show();
            fragmentManager.popBackStack();
            navigateToFragment(new TechnicianDetailFragment(), false);
        }
    }

    /**
     * ON DEVICE MODIFY: Handles the modification of a device
     *
     * @param updatedDevice Device to modify
     */
    @Override
    public void onDeviceModify(Device updatedDevice) {
        //TODO: IMPLEMENTAR
        if (selectedClient != null) {

            //Buscar el dispositivo en el cliente y actualizarlo
            List<Device> clientDevices = selectedClient.getDevices();
            for (int i = 0; i < clientDevices.size(); i++) {
                if (clientDevices.get(i).getModel().equals(selectedDevice.getModel())) {
                    clientDevices.set(i, updatedDevice);
                    break;
                }
            }
            //Actualizar el dispositivo en el workshopManager
            workshopManager.removeDevice(selectedDevice);
            workshopManager.addDevice(updatedDevice);
            Toast.makeText(this, "Device modified: " + updatedDevice.getModel(), Toast.LENGTH_SHORT).show();
            fragmentManager.popBackStack();
            navigateToFragment(new DeviceDetailFragment(), false);
        }
        if (selectedTechnician != null) {
            //Buscar el dispositivos asignados al técnico y actualizarlo
            List<Device> technicianDevices = selectedTechnician.getDevices();
            for (int i = 0; i < technicianDevices.size(); i++) {
                if (technicianDevices.get(i).getModel().equals(selectedDevice.getModel())) {
                    technicianDevices.set(i, updatedDevice);
                    break;
                }
            }
            //Actualizar el dispositivo en el workshopManager
            workshopManager.removeDevice(selectedDevice);
            workshopManager.addDevice(updatedDevice);
            Toast.makeText(this, "Device modified: " + updatedDevice.getModel(), Toast.LENGTH_SHORT).show();
            fragmentManager.popBackStack();
            navigateToFragment(new DeviceDetailFragment(), false);
        }
    }


}