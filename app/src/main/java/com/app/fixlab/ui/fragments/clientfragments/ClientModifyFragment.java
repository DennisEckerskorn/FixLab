package com.app.fixlab.ui.fragments.clientfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.adapters.DevicesAdapter;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.OnDeviceClickListener;
import com.app.fixlab.listeners.OnModifyListener;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.ui.MainActivity;
import com.app.fixlab.ui.fragments.devicefragments.DeviceModifyFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ClientModifyFragment extends Fragment {
    private Person selectedPerson;
    private TextInputEditText etName, etEmail, etPhone, etAddress;
    private RecyclerView rvDevices;
    private OnModifyListener modifyListener;
    private OnDeviceClickListener itemClickListener;
    Button btnSave;

    public ClientModifyFragment() {
        super(R.layout.fragment_modify_client);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupInitialData();
    }

    private void initViews(View view) {
        btnSave = view.findViewById(R.id.btnSaveEdit);
        btnSave.setOnClickListener(v -> {
            if (validateFields()) {
                saveChanges();
            }
        });
        etName = view.findViewById(R.id.etEditName);
        etEmail = view.findViewById(R.id.etEditEmail);
        etPhone = view.findViewById(R.id.etEditPhone);
        etAddress = view.findViewById(R.id.etEditAddress);
        rvDevices = view.findViewById(R.id.rvModifyClientDevices);
    }

    private void setupInitialData() {
        if (selectedPerson != null) {
            etName.setText(selectedPerson.getName());
            etEmail.setText(selectedPerson.getEmail());
            etPhone.setText(selectedPerson.getPhoneNumber());
            etAddress.setText(selectedPerson.getAddress());

            DevicesAdapter devicesAdapter = new DevicesAdapter(selectedPerson.getDevices());
            devicesAdapter.setListener(this::navigateToDeviceModifyFragment);
            rvDevices.setAdapter(devicesAdapter);
            rvDevices.setHasFixedSize(true);
            rvDevices.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            devicesAdapter.notifyDataSetChanged();
        }
    }

    private boolean validateFields() {
        if (etName == null || etEmail == null || etPhone == null || etAddress == null) {
            return false;
        }

        boolean isValid = true;

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("El nombre es obligatorio");
            isValid = false;
        }

        if (email.isEmpty()) {
            etEmail.setError("El email es obligatorio");
            isValid = false;
        }

        if (phone.isEmpty()) {
            etPhone.setError("El teléfono es obligatorio");
            isValid = false;
        }

        if (address.isEmpty()) {
            etAddress.setError("La dirección es obligatoria");
            isValid = false;
        }

        return isValid;
    }

    private void saveChanges() {
        if (modifyListener != null && selectedPerson != null) {

            Client updatedClient = new Client(
                    selectedPerson.getDni(),
                    etName.getText().toString(),
                    selectedPerson.getSurname(),
                    etEmail.getText().toString(),
                    etPhone.getText().toString(),
                    etAddress.getText().toString(),
                    selectedPerson.getUsername(),
                    selectedPerson.getPassword()
            );
            if (updatedClient.equals(selectedPerson)){
                Toast.makeText(getContext(), "No se han realizado cambios.", Toast.LENGTH_SHORT).show();
            }else {
                modifyListener.onClientModify(updatedClient);
            }
        }
    }

    private void navigateToDeviceModifyFragment(Device device) {
        if (getActivity() instanceof MainActivity) {
            DeviceModifyFragment deviceModifyFragment = new DeviceModifyFragment();
            deviceModifyFragment.setSelectedDevice(device); // Pasar el dispositivo seleccionado
            ((MainActivity) getActivity()).navigateToFragment(deviceModifyFragment, true);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            itemClickListener = (OnDeviceClickListener) requireActivity();
            modifyListener = (OnModifyListener) requireActivity();
            selectedPerson = ((MainActivity) requireActivity()).getClient();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnClientModifyListener");
        }
    }
}
