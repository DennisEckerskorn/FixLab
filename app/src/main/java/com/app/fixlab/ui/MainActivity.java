package com.app.fixlab.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.app.fixlab.parsers.TestSaveData;
import com.app.fixlab.ui.fragments.MainMenuFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientDetailFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientFragment;
import com.app.fixlab.ui.fragments.SplashFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientListFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientModifyFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceDetailFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceFragment;
import com.app.fixlab.ui.fragments.devicefragments.DeviceModifyFragment;
import com.app.fixlab.ui.fragments.repairfragments.CompletedRepairListFragment;
import com.app.fixlab.ui.fragments.repairfragments.DiagnosisFragment;
import com.app.fixlab.ui.fragments.repairfragments.RepairSummaryFragment;
import com.app.fixlab.ui.fragments.repairfragments.RepairedDeviceListFragment;
import com.app.fixlab.ui.fragments.repairfragments.TechnicianSelectionFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianDetailFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianModifyFragment;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataManager = DataManager.getInstance();
        TestSaveData testSaveData = new TestSaveData(this);
        testSaveData.loadData();
        testSaveData.addPerson(new Client("1234", "pedro", "pascual", "pedropascual@gmail.com", "656764563", "avenida siempre viva 14", "pedropas23", "1234"));
        testSaveData.saveData();
        //detailAvailable = findViewById(R.id.fcvDetail) != null;
        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            workshopManager = new WorkshopManager(this);
            loadData();
            selectedClient = null;
            selectedTechnician = null;
            selectedDevice = null;
            replaceFragment(new SplashFragment(), false);
        } else {
            selectedClient = (Person) savedInstanceState.getSerializable(CLIENT_KEY);
            selectedTechnician = (Person) savedInstanceState.getSerializable(TECHNICIAN_KEY);
            selectedDevice = (Device) savedInstanceState.getSerializable(DEVICE_KEY);
            currentRepair = (Repair) savedInstanceState.getSerializable(REPAIR_KEY);
        }

        // toolbarSettings();
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
    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
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
        int techniciansLoaded = workshopManager.loadTechnicians();
        int clientsLoaded = workshopManager.loadClients();
        Toast.makeText(this, "Technicians loaded: " + techniciansLoaded, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Clients loaded: " + clientsLoaded, Toast.LENGTH_SHORT).show();
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
     * ON CLIENTS SELECTED: Navigates to the clients fragment
     */
    @Override
    public void onClientsSelected() {
        replaceFragment(new ClientFragment(), true);
    }

    /**
     * ON DEVICES SELECTED: Navigates to the devices fragment
     */
    @Override
    public void onDevicesSelected() {
        replaceFragment(new DeviceFragment(), true);
    }

    /**
     * ON TECHNICIANS SELECTED: Navigates to the technicians fragment
     */
    @Override
    public void onTechniciansSelected() {
        replaceFragment(new TechnicianFragment(), true);
    }

    /**
     * ON START REPARATION SELECTED: Navigates to the start reparation fragment
     */
    @Override
    public void onStartReparationSelected() {
        replaceFragment(new TechnicianSelectionFragment(), true);
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

    @Override
    public List<Repair> getCompletedRepairs() {
        if (currentRepair != null) {
            List<Repair> repairs = workshopManager.getAllRepairs();
            return repairs == null ? Collections.emptyList() : repairs;
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
            replaceFragment(new MainMenuFragment(), false);
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
            replaceFragment(clientDetailFragment, true);
            Toast.makeText(this, "Client selected: " + selectedClient.getName(), Toast.LENGTH_SHORT).show();
        } else if (item instanceof Technician) {
            selectedTechnician = item;
            Toast.makeText(this, "Technician selected: " + selectedTechnician.getName(), Toast.LENGTH_SHORT).show();
            replaceFragment(new TechnicianDetailFragment(), true);
        }
    }

    @Override
    public void onCompletedSummaryRepair(Repair item) {
        currentRepair = item;
        RepairSummaryFragment repairSummaryFragment = new RepairSummaryFragment();
        repairSummaryFragment.setShowFields(false);
        replaceFragment(repairSummaryFragment, true);
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
            this.currentRepair = new Repair(selectedTechnician, null, null);
            replaceFragment(new RepairedDeviceListFragment(), true);
            Toast.makeText(this, "Technician selected: " + selectedTechnician.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Technician not selected", Toast.LENGTH_SHORT).show();
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
        replaceFragment(new DeviceDetailFragment(), true);
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
        selectedDevice.setStatus(DeviceStatus.IN_PROGRESS);
        selectedClient = workshopManager.getClientByDevice(selectedDevice);

        if (currentRepair != null) {
            currentRepair.setDevice(selectedDevice);
            currentRepair.setClient((Client) selectedClient);
            currentRepair.setStatus(Repair.RepairStatus.PENDING);
            Toast.makeText(this, "Device selected for repair: " + device.getModel() + "current repair: " + currentRepair.getStatus(), Toast.LENGTH_SHORT).show();
            replaceFragment(new DiagnosisFragment(), true);
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
            replaceFragment(new RepairSummaryFragment(), true);
        } else {
            Toast.makeText(this, "Diagnosis not saved", Toast.LENGTH_SHORT).show();
        }
    }

    //TODO: Marcar dispositivo como COMPLETED y que se cambie en Devices
    @Override
    public void onRepairCompleted() {
        if (currentRepair != null) {
            currentRepair.setStatus(Repair.RepairStatus.COMPLETED);
            currentRepair.getDevice().setStatus(DeviceStatus.COMPLETED);
            workshopManager.addRepair(currentRepair);
            Toast.makeText(this, "Reparación completada", Toast.LENGTH_SHORT).show();
            replaceFragment(new MainMenuFragment(), false);
        } else {
            Toast.makeText(this, "No se puede completar la reparación", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onModifyButtonClient() {
        replaceFragment(new ClientModifyFragment(), true);
    }

    @Override
    public void onModifyButtonTechnician() {
        replaceFragment(new TechnicianModifyFragment(), true);
    }

    @Override
    public void onModifyButtonDevice() {
        replaceFragment(new DeviceModifyFragment(), true);
    }

    @Override
    public void onRepairSummarySelected() {
        replaceFragment(new CompletedRepairListFragment(), true);
    }


    /**
     * ON CLIENT MODIFY: Handles the modification of a client.
     *
     * @param updatedClient Client to modify.
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
                replaceFragment(new ClientDetailFragment(), false);
            } else {
                Toast.makeText(this, "Error updating client", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * ON TECHNICIAN MODIFY: Handles the modification of a technician.
     *
     * @param updatedTechnician Technician to modify.
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
                replaceFragment(new TechnicianDetailFragment(), false);
            } else {
                Toast.makeText(this, "Error updating technician", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * ON DEVICE MODIFY: Handles the modification of a device.
     *
     * @param updatedDevice Device to modify.
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
            if (workshopManager.updateDevice(selectedDevice, updatedDevice)) {
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


    @Override
    public void onDeleteClient() {
        fragmentManager.popBackStack();
        replaceFragment(new ClientListFragment(), false);
        workshopManager.removePerson(selectedClient);
    }
}