package com.app.fixlab.ui.fragments.technicianfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.OnDeviceClickListener;
import com.app.fixlab.listeners.OnModifyListener;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;
import com.app.fixlab.ui.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class TechnicianModifyFragment extends Fragment {
    private TextInputEditText etDNI, etName, etSurname, etEmail, etPhone, etAddress, etPassword, etUsername;
    private OnModifyListener modifyListener;
    private Technician selectedTechnician;
    private List<Device> devices;
    private OnDeviceClickListener itemClickListener;
    private Button btnSave;


    public TechnicianModifyFragment(){
        super(R.layout.fragment_modify_technician);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupInitialData();
    }

    private void initViews(View view) {
        btnSave = view.findViewById(R.id.btnSaveTechnicianEdit);
        btnSave.setOnClickListener(v -> {
            if (validateFields()) {
                saveChanges();
            }
        });
        etName = view.findViewById(R.id.etEditTechnicianName);
        etSurname = view.findViewById(R.id.etEditTechnicianSurname);
        etDNI = view.findViewById(R.id.etEditTechnicianDNI);
        etEmail = view.findViewById(R.id.etEditTechnicianEmail);
        etPhone = view.findViewById(R.id.etEditTechnicianPhone);
        etAddress = view.findViewById(R.id.etEditTechnicianAddress);
        etUsername = view.findViewById(R.id.etEditTechnicianUsername);
        etPassword = view.findViewById(R.id.etEditTechnicianPassword);
    }




    private void setupInitialData() {
        if (selectedTechnician != null) {
            etName.setText(selectedTechnician.getName());
            etSurname.setText(selectedTechnician.getSurname());
            etDNI.setText(selectedTechnician.getDni());
            etEmail.setText(selectedTechnician.getEmail());
            etPhone.setText(selectedTechnician.getPhoneNumber());
            etAddress.setText(selectedTechnician.getAddress());
            etUsername.setText(selectedTechnician.getUsername());
            etPassword.setText(selectedTechnician.getPassword());
        }
    }

    private boolean validateFields() {
        boolean isValid = true;
        if (etName.getText().toString().isEmpty()) {
            etName.setError("El nombre es obligatorio");
            isValid = false;
        }
        if (etSurname.getText().toString().isEmpty()) {
            etSurname.setError("El apellido es obligatorio");
            isValid = false;
        }
        if (etDNI.getText().toString().isEmpty()) {
            etDNI.setError("El DNI es obligatorio");
            isValid = false;
        }
        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("El email es obligatorio");
            isValid = false;
        }
        if (etPhone.getText().toString().isEmpty()) {
            etPhone.setError("El teléfono es obligatorio");
            isValid = false;
        }
        if (etAddress.getText().toString().isEmpty()) {
            etAddress.setError("La dirección es obligatoria");
            isValid = false;
        }
        if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("La contraseña es obligatoria");
            isValid = false;
        }
        return isValid;
    }

    private void saveChanges() {
        if (modifyListener != null && selectedTechnician != null) {
            Technician updatedTechnician = new Technician(
                    etDNI.getText().toString(),
                    etName.getText().toString(),
                    etSurname.getText().toString(),
                    etEmail.getText().toString(),
                    etPhone.getText().toString(),
                    etAddress.getText().toString(),
                    etUsername.getText().toString(),
                    etPassword.getText().toString(),
                    selectedTechnician.getAvailability().toString()
            );
            modifyListener.onTechnicianModify(updatedTechnician);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            itemClickListener = (OnDeviceClickListener) requireActivity();
            IdataProvider dataProvider = (IdataProvider) requireActivity();
            devices = dataProvider.getDeviceOfClient();
            modifyListener = (OnModifyListener) requireActivity();
            selectedTechnician = (Technician) ((MainActivity) requireActivity()).getTechnician();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnTechnicianModifyListener");
        }
    }
}
