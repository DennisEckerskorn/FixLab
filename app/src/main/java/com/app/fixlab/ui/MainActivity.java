package com.app.fixlab.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.fixlab.R;
import com.app.fixlab.listeners.IOnItemRepairClickListener;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.listeners.MenuActionListener;
import com.app.fixlab.listeners.OnCheckedChangeListener;
import com.app.fixlab.listeners.OnDeleteListener;
import com.app.fixlab.listeners.OnDeviceClickListener;
import com.app.fixlab.listeners.OnModifyListener;
import com.app.fixlab.listeners.OnSaveAddClient;
import com.app.fixlab.listeners.OnSaveAddDevice;
import com.app.fixlab.listeners.OnSaveAddTechnician;
import com.app.fixlab.listeners.OnSaveDiagnosis;
import com.app.fixlab.listeners.OnSplashDelayFinished;
import com.app.fixlab.managers.WorkshopManager;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.devices.DeviceStatus;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;
import com.app.fixlab.models.repair.Diagnosis;
import com.app.fixlab.models.repair.Repair;
import com.app.fixlab.parsers.DataManager;
import com.app.fixlab.ui.fragments.MainMenuFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientDetailFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientFragment;
import com.app.fixlab.ui.fragments.SplashFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientListFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientModifyFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceDetailFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceListFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceModifyFragment;
import com.app.fixlab.ui.fragments.repairfragments.CompletedRepairListFragment;
import com.app.fixlab.ui.fragments.repairfragments.DiagnosisFragment;
import com.app.fixlab.ui.fragments.repairfragments.RepairSummaryFragment;
import com.app.fixlab.ui.fragments.repairfragments.RepairedDeviceListFragment;
import com.app.fixlab.ui.fragments.repairfragments.TechnicianSelectionFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianDetailFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianListFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianModifyFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IonItemClickListenerGeneric<Person>, IdataProvider, IOnItemRepairClickListener, OnDeviceClickListener,
        MenuActionListener, OnSaveAddClient, OnSaveAddTechnician, OnSplashDelayFinished,
        OnSaveAddDevice, OnModifyListener, OnCheckedChangeListener, OnSaveDiagnosis, OnDeleteListener {
    private Person selectedClient;
    private Person selectedTechnician;
    private Device selectedDevice;
    private Repair currentRepair;
    private WorkshopManager workshopManager;
    private FragmentManager fragmentManager;
    //private boolean detailAvailable;
    private static final long SPLASH_SCREEN_DELAY = 3000;
    private static final String CLIENT_KEY = "SELECTED_CLIENT";
    private static final String TECHNICIAN_KEY = "SELECTED_TECHNICIAN";
    private static final String DEVICE_KEY = "SELECTED_DEVICE";
    private static final String REPAIR_KEY = "SELECTED_REPAIR";
    private static DataManager dataManager;

    private final Map<String, Fragment> fragmentCache = new HashMap<>();

    /**
     * Called when the activity is first created.
     * Initializes the application, sets up the UI, loads initial data, and displays the SplashFragment.
     *
     * @param savedInstanceState If the activity is being reinitialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in onSaveInstanceState().
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataManager = DataManager.getInstance();
        //detailAvailable = findViewById(R.id.fcvDetail) != null;
        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            workshopManager = new WorkshopManager(this);
            loadData();
            selectedClient = null;
            selectedTechnician = null;
            selectedDevice = null;
            replaceFragment(SplashFragment.class, false);
        } else {
            selectedClient = (Person) savedInstanceState.getSerializable(CLIENT_KEY);
            selectedTechnician = (Person) savedInstanceState.getSerializable(TECHNICIAN_KEY);
            selectedDevice = (Device) savedInstanceState.getSerializable(DEVICE_KEY);
            currentRepair = (Repair) savedInstanceState.getSerializable(REPAIR_KEY);
        }

        // toolbarSettings();
    }

    /**
     * Saves the current state of the activity.
     * Used to preserve the state of selected client, technician, device, and repair during configuration changes.
     *
     * @param outState The Bundle in which to place the saved state.
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CLIENT_KEY, (Serializable) selectedClient);
        outState.putSerializable(TECHNICIAN_KEY, (Serializable) selectedTechnician);
        outState.putSerializable(DEVICE_KEY, (Serializable) selectedDevice);
        outState.putSerializable(REPAIR_KEY, (Serializable) currentRepair);
    }

    /**
     * Replaces the current fragment with a new one.
     *
     * @param addToBackStack If true, the transaction is added to the back stack, allowing the user to navigate back.
     */
    public void replaceFragment(Class<? extends Fragment> fragmentClass, boolean addToBackStack) {
        String fragmentTag = fragmentClass.getSimpleName();
        Fragment fragment = fragmentCache.get(fragmentTag);

        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
                fragmentCache.put(fragmentTag, fragment);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error creating fragment: " + fragmentTag, Toast.LENGTH_SHORT).show();
                return;
            }
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .replace(R.id.fcvMain, fragment, fragmentTag);

        if (addToBackStack) {
            transaction.addToBackStack(fragmentTag);
        }

        //commitAllowingStateLoss para evitar problemas si el estado ya fue guardado
        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        String fragmentTag = fragment.getClass().getSimpleName();

        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .replace(R.id.fcvMain, fragment, fragmentTag);

        if (addToBackStack) {
            transaction.addToBackStack(fragmentTag);
        }

        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    /**
     * Loads the workshop data for clients and technicians from JSON files.
     * Displays a Toast message indicating the number of clients and technicians loaded.
     */
    private void loadData() {
        new Thread(() -> {
            int techniciansLoaded = workshopManager.loadTechnicians();
            int clientsLoaded = workshopManager.loadClients();

            // Actualiza la UI en el hilo principal
            runOnUiThread(() -> {
                Toast.makeText(this, "Technicians loaded: " + techniciansLoaded, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Clients loaded: " + clientsLoaded, Toast.LENGTH_SHORT).show();
            });
        }).start();
    }


    /**
     * TOOLBAR SETTINGS: Sets the toolbar
     */
//    private void toolbarSettings() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//    }

    /*
     * MENU ACTION LISTENERS:
     */

    /**
     * Navigates to the ClientsFragment to display the list of clients.
     */
    @Override
    public void onClientsSelected() {
        replaceFragment(ClientFragment.class, true);
    }

    /**
     * Navigates to the DevicesFragment to display the list of devices.
     */
    @Override
    public void onDevicesSelected() {
        replaceFragment(DeviceFragment.class, true);
    }

    /**
     * Navigates to the TechniciansFragment to display the list of technicians.
     */
    @Override
    public void onTechniciansSelected() {
        replaceFragment(new TechnicianFragment(), true);
    }

    /**
     * Navigates to the TechnicianSelectionFragment to start a repair process.
     */
    @Override
    public void onStartReparationSelected() {
        replaceFragment(TechnicianSelectionFragment.class, true);
    }


    /*
     * GETTERS AND SETTERS:
     */


    /**
     * Retrieves the list of all clients in the workshop.
     *
     * @return A list of clients; an empty list if no clients exist.
     */
    @Override
    public List<Person> getClientData() {
        List<Person> clients = workshopManager.getAllClients();
        return clients == null ? Collections.emptyList() : clients;
    }

    /**
     * Retrieves the list of all technicians in the workshop.
     *
     * @return A list of technicians; an empty list if no technicians exist.
     */
    @Override
    public List<Person> getTechnicianData() {
        List<Person> technicians = workshopManager.getAllTechnicians();
        return technicians == null ? Collections.emptyList() : technicians;
    }

    /**
     * Retrieves the list of all devices in the workshop.
     *
     * @return A list of devices; an empty list if no devices exist.
     */
    @Override
    public List<Device> getDeviceData() {
        List<Device> devices = workshopManager.getAllDevices();
        return devices == null ? Collections.emptyList() : devices;
    }

    /**
     * Retrieves the list of devices belonging to the selected client.
     *
     * @return A list of devices; an empty list if the client has no devices or no client is selected.
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
     * Retrieves the list of completed repairs.
     *
     * @return A list of completed repairs; an empty list if no repairs exist.
     */
    @Override
    public List<Repair> getCompletedRepairs() {
        // Obtener todas las reparaciones desde WorkshopManager
        List<Repair> allRepairs = workshopManager.getAllRepairs();

        // Si no hay reparaciones, devolver una lista vacía
        if (allRepairs == null || allRepairs.isEmpty()) {
            return Collections.emptyList();
        }

        // Filtrar las reparaciones con estado COMPLETED
        List<Repair> completedRepairs = new ArrayList<>();
        for (Repair repair : allRepairs) {
            if (repair.getStatus() == Repair.RepairStatus.COMPLETED) {
                completedRepairs.add(repair);
            }
        }

        return completedRepairs;
    }


    /**
     * Gets the currently selected client.
     *
     * @return The selected client; null if no client is selected.
     */
    @Override
    public Person getClient() {
        return selectedClient;
    }

    /**
     * Gets the currently selected technician.
     *
     * @return The selected technician; null if no technician is selected.
     */
    @Override
    public Person getTechnician() {
        return selectedTechnician;
    }

    /**
     * Gets the currently selected device.
     *
     * @return The selected device; null if no device is selected.
     */
    @Override
    public Device getDevice() {
        return selectedDevice;
    }

    /**
     * Gets the current repair process.
     *
     * @return The current repair; null if no repair is active.
     */
    @Override
    public Repair getRepair() {
        return currentRepair;
    }

    /*
     * LISTENERS:
     */


    /**
     * Saves a new client to the system.
     *
     * @param client The client to be added.
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
     * Saves a new technician to the system.
     *
     * @param technician The technician to be added.
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
     * Saves a new device to the system.
     *
     * @param device The device to be added.
     */
    @Override
    public void onSaveAddDevice(Device device) {
        if (device != null) {
            workshopManager.addDevice(device);
            Toast.makeText(this, "Device saved: " + device.getModel(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Saves the diagnosis data and updates the repair status.
     *
     * @param diagnosis The diagnosis data to be saved.
     */
    @Override
    public void onSaveDiagnosis(Diagnosis diagnosis) {
        if (currentRepair != null) {
            // Asignar el diagnóstico a la reparación
            currentRepair.setDiagnosis(diagnosis);
            currentRepair.setStatus(Repair.RepairStatus.IN_PROGRESS);

            if (currentRepair.getDevice() != null) {
                currentRepair.getDevice().setStatus(DeviceStatus.IN_REPAIR);
            }

            Toast.makeText(this, "Diagnosis saved successfully", Toast.LENGTH_SHORT).show();

            // Navegar al fragmento de resumen
            replaceFragment(new RepairSummaryFragment(), true);
        } else {
            Toast.makeText(this, "No repair initialized", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Starts the main menu after the splash delay has finished.
     */
    @Override
    public void onSPlashDelayFinished() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            replaceFragment(MainMenuFragment.class, false);
        }, SPLASH_SCREEN_DELAY);
    }

    /**
     * Handles the selection of a person item.
     * Navigates to the appropriate detail fragment based on whether the item is a client or a technician.
     *
     * @param item The person item selected.
     */
    @Override
    public void onItemClick(Person item) {
        if (item instanceof Client) {
            selectedClient = item;

            ClientDetailFragment clientDetailFragment = (ClientDetailFragment) fragmentCache.get(ClientDetailFragment.class.getSimpleName());

            if (clientDetailFragment == null) {
                clientDetailFragment = new ClientDetailFragment();
                fragmentCache.put(ClientDetailFragment.class.getSimpleName(), clientDetailFragment);
            }
            replaceFragment(ClientDetailFragment.class, true);
            Toast.makeText(this, "Client selected: " + selectedClient.getName(), Toast.LENGTH_SHORT).show();
        } else if (item instanceof Technician) {
            selectedTechnician = item;
            TechnicianDetailFragment technicianDetailFragment = (TechnicianDetailFragment) fragmentCache.get(TechnicianDetailFragment.class.getSimpleName());
            if (technicianDetailFragment == null) {
                technicianDetailFragment = new TechnicianDetailFragment();
                fragmentCache.put(TechnicianDetailFragment.class.getSimpleName(), technicianDetailFragment);
            }

            replaceFragment(TechnicianDetailFragment.class, true);
            Toast.makeText(this, "Technician selected: " + selectedTechnician.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the selection of a repair item to view its summary.
     *
     * @param item The repair item selected.
     */
    /**
     * Handles the selection of a repair item to view its summary.
     *
     * @param item The repair item selected.
     */
    @Override
    public void onCompletedSummaryRepair(Repair item) {
        currentRepair = item;

        // Crear una nueva instancia del fragmento
        RepairSummaryFragment repairSummaryFragment = new RepairSummaryFragment();
        repairSummaryFragment.setShowFields(false);

        // Reemplazar el fragmento sin usar el caché
        replaceFragment(repairSummaryFragment, true);
    }

    /**
     * Handles the selection of a technician or other repair-related item.
     *
     * @param item The repair-related item selected.
     */
    @Override
    public void onItemClickRepair(Object item) {
        if (item instanceof Technician) {
            selectedTechnician = (Person) item;

            // Crear una nueva reparación
            currentRepair = new Repair(selectedTechnician, null, null);

            // Navegar al fragmento de selección de dispositivos
            replaceFragment(RepairedDeviceListFragment.class, true);

            Toast.makeText(this, "Technician selected: " + selectedTechnician.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Technician not selected", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the selection of a repaired device and starts a repair process.
     *
     * @param device The device selected for repair.
     */
    @Override
    public void onRepairedDeviceClick(Device device) {
        if (currentRepair != null) {
            selectedDevice = device;

            // Asignar el dispositivo a la reparación
            currentRepair.setDevice(selectedDevice);
            currentRepair.setClient((Client) workshopManager.getClientByDevice(selectedDevice));
            currentRepair.setStatus(Repair.RepairStatus.PENDING);

            Toast.makeText(this, "Device selected: " + device.getModel(), Toast.LENGTH_SHORT).show();

            // Navegar al fragmento de diagnóstico
            replaceFragment(new DiagnosisFragment(), true);
        } else {
            Toast.makeText(this, "No repair initialized", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Marks the current repair as completed and updates device and repair statuses.
     */
    @Override
    public void onRepairCompleted() {
        if (currentRepair != null) {
            // Actualizar el estado de la reparación y del dispositivo
            currentRepair.setStatus(Repair.RepairStatus.COMPLETED);
            if (currentRepair.getDevice() != null) {
                currentRepair.getDevice().setStatus(DeviceStatus.COMPLETED);
            }

            // Guardar la reparación en WorkshopManager
            workshopManager.addRepair(currentRepair);

            Toast.makeText(this, "Repair completed successfully", Toast.LENGTH_SHORT).show();

            // Reiniciar el estado para la próxima reparación
            currentRepair = null;
            selectedTechnician = null;
            selectedDevice = null;

            // Volver al menú principal
            replaceFragment(MainMenuFragment.class, false);
        } else {
            Toast.makeText(this, "No repair to complete", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Handles the selection of a device and navigates to its details.
     *
     * @param device The device selected.
     */
    @Override
    public void onDeviceClick(Device device) {
        selectedDevice = device;
        Toast.makeText(this, "Device selected: " + device.getModel(), Toast.LENGTH_SHORT).show();
        replaceFragment(DeviceDetailFragment.class, true);
    }


    /**
     * Updates the status of a diagnosis check item.
     *
     * @param item      The diagnosis item being checked or unchecked.
     * @param isChecked The state of the item (checked or unchecked).
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
     * Navigates to the ClientModifyFragment to allow modification of client details.
     */
    @Override
    public void onModifyButtonClient() {
        replaceFragment(new ClientModifyFragment(), true);
    }

    /**
     * Navigates to the TechnicianModifyFragment to allow modification of technician details.
     */
    @Override
    public void onModifyButtonTechnician() {
        replaceFragment(new TechnicianModifyFragment(), true);
    }

    /**
     * Navigates to the DeviceModifyFragment to allow modification of device details.
     */
    @Override
    public void onModifyButtonDevice() {
        replaceFragment(new DeviceModifyFragment(), true);
    }

    /**
     * Navigates to the CompletedRepairListFragment to view completed repairs.
     */
    @Override
    public void onRepairSummarySelected() {
        replaceFragment(new CompletedRepairListFragment(), true);
    }

    /**
     * Modifies the details of the selected client and updates it in the system.
     *
     * @param updatedClient The updated client details.
     */
    @Override
    public void onClientModify(Client updatedClient) {
        if (updatedClient != null) {
            if (workshopManager.updatePerson(selectedClient, updatedClient)) {
                // Actualizar el cliente seleccionado
                selectedClient = updatedClient;
                Toast.makeText(this, "Client modified: " + updatedClient.getName(), Toast.LENGTH_SHORT).show();
                // Volver al fragmento de detalle
                fragmentManager.popBackStack();
                replaceFragment(ClientDetailFragment.class, false);
            } else {
                Toast.makeText(this, "Error updating client", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Modifies the details of the selected technician and updates it in the system.
     *
     * @param updatedTechnician The updated technician details.
     */
    @Override
    public void onTechnicianModify(Technician updatedTechnician) {
        if (updatedTechnician != null) {
            if (workshopManager.updatePerson(selectedTechnician, updatedTechnician)) {
                // Transferir los dispositivos del técnico antiguo al actualizado
                updatedTechnician.getDevices().addAll(selectedTechnician.getDevices());

                // Actualizar el técnico seleccionado
                selectedTechnician = updatedTechnician;
                Toast.makeText(this, "Technician modified: " + updatedTechnician.getName(), Toast.LENGTH_SHORT).show();

                // Volver al fragmento de detalle
                fragmentManager.popBackStack();
                replaceFragment(TechnicianDetailFragment.class, false);
            } else {
                Toast.makeText(this, "Error updating technician", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Modifies the details of the selected device and updates it in the system.
     *
     * @param updatedDevice The updated device details.
     */
    @Override
    public void onDeviceModify(Device updatedDevice) {
        if (updatedDevice != null && selectedDevice != null) {
            // Buscar el cliente que tiene el dispositivo seleccionado
            Person client = workshopManager.getClientByDevice(selectedDevice);
            if (client == null) {
                Toast.makeText(this, "Error: Client not found for the selected device", Toast.LENGTH_SHORT).show();
                return;
            }

            // Obtener la lista de dispositivos del cliente
            List<Device> clientDevices = client.getDevices();
            if (clientDevices == null || clientDevices.isEmpty()) {
                Toast.makeText(this, "Error: Client has no devices to update", Toast.LENGTH_SHORT).show();
                return;
            }

            // Buscar y actualizar el dispositivo en la lista del cliente
            int deviceIndex = clientDevices.indexOf(selectedDevice);
            if (deviceIndex == -1) {
                Toast.makeText(this, "Error: Device not found in client's devices", Toast.LENGTH_SHORT).show();
                return;
            }

            // Actualizar el dispositivo en la lista del cliente
            clientDevices.set(deviceIndex, updatedDevice);

            // Actualizar el dispositivo en el WorkshopManager
            if (workshopManager.updateDevice(selectedDevice)) {
                selectedDevice = updatedDevice; // Actualizar la referencia seleccionada
                Toast.makeText(this, "Device modified: " + updatedDevice.getModel(), Toast.LENGTH_SHORT).show();

                // Volver al fragmento de detalle
                fragmentManager.popBackStack();
                replaceFragment(new DeviceDetailFragment(), false);
            } else {
                Toast.makeText(this, "Error updating device in the system", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error: Missing device data for modification", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Deletes the currently selected client and navigates to the ClientListFragment.
     */
    @Override
    public void onDeleteClient() {
        fragmentManager.popBackStack();
        replaceFragment(ClientListFragment.class, false);
        workshopManager.removePerson(selectedClient);
    }

    /**
     * Deletes the currently selected device and navigates to the DeviceListFragment.
     */
    @Override
    public void onDeleteDevice() {
        int fragmentsLoaded = fragmentManager.getBackStackEntryCount();
        if (fragmentsLoaded > 1) {
            String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentsLoaded - 2).getName();
            Fragment currentFragment = fragmentManager.findFragmentByTag(fragmentTag);
            if (currentFragment instanceof ClientDetailFragment) {
                replaceFragment(new ClientDetailFragment(), false);
            } else {
                replaceFragment(new DeviceListFragment(), false);
            }
            workshopManager.removeDevice(selectedDevice);
            fragmentManager.popBackStack();
        }
    }
}