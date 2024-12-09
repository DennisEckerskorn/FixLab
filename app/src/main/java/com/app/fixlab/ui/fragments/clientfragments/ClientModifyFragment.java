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
    private OnModifyListener modifyListener;
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
    }

    private void setupInitialData() {
        if (selectedPerson != null) {
            etName.setText(selectedPerson.getName());
            etEmail.setText(selectedPerson.getEmail());
            etPhone.setText(selectedPerson.getPhoneNumber());
            etAddress.setText(selectedPerson.getAddress());
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
        //Si el nombre del cliente contiene numeros o caracteres especiales se muestra un error
        if (!name.matches("^[a-zA-Z ]+$")) {
            etName.setError("El nombre solo puede contener letras");
            isValid = false;
        }
        //Si el nombre del cliente contine menos de 2 caracteres se muestra un error
        if (name.length() < 2) {
            etName.setError("El nombre debe tener al menos 2 caracteres");
            isValid = false;
        }

        if (email.isEmpty()) {
            etEmail.setError("El email es obligatorio");
            isValid = false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email válido es requerido");
            isValid = false;
        }

        if (phone.isEmpty()) {
            etPhone.setError("El teléfono es obligatorio");
            isValid = false;
        }
        if (phone.length() != 11) {
            etPhone.setError("El teléfono debe contener el simbolo + y 10 dígitos");
            isValid = false;
        }
        // el telefono solo puede contener numeros y sibolo más
        if (!phone.matches("^[0-9+]*$")) {
            etPhone.setError("El teléfono solo puede contener el símbolo + y números");
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
            // Crear un nuevo cliente con los datos actualizados pero manteniendo la misma referencia
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
            modifyListener.onClientModify(updatedClient);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            modifyListener = (OnModifyListener) requireActivity();
            selectedPerson = ((MainActivity) requireActivity()).getClient();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnClientModifyListener");
        }
    }
}
