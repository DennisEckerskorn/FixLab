package com.app.fixlab.ui.fragments.technicianfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.listeners.OnSaveAddTechnician;
import com.app.fixlab.models.persons.Technician;
import com.google.android.material.textfield.TextInputLayout;

public class TechnicianFormAddFragment extends Fragment {
    private OnSaveAddTechnician saveAddTechnicianListener;
    private TextInputLayout tilName;
    private TextInputLayout tilSurname;
    private TextInputLayout tilDni;
    private TextInputLayout tilEmail;
    private TextInputLayout tilPhone;
    private TextInputLayout tilAddress;
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;
    private AutoCompleteTextView spinnerAvailability;
    private Button btnSave;

    public TechnicianFormAddFragment() {
        super(R.layout.activity_add_technician);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        setupAvailabilitySpinner();
        setupSaveButton();
    }

    private void initializeViews(View view) {
        tilName = view.findViewById(R.id.tilTechnicianName);
        tilSurname = view.findViewById(R.id.tilTechnicianSurname);
        tilDni = view.findViewById(R.id.tilTechnicianDni);
        tilEmail = view.findViewById(R.id.tilTechnicianEmail);
        tilPhone = view.findViewById(R.id.tilTechnicianPhone);
        tilAddress = view.findViewById(R.id.tilTechnicianAddress);
        tilUsername = view.findViewById(R.id.tilTechnicianUsername);
        tilPassword = view.findViewById(R.id.tilTechnicianPassword);
        spinnerAvailability = view.findViewById(R.id.spinnerTechnicianAvailability);
        btnSave = view.findViewById(R.id.btnSaveTechnician);
    }

    private void setupAvailabilitySpinner() {
        String[] availabilityOptions = {"AVAILABLE", "UNAVAILABLE"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                availabilityOptions
        );
        spinnerAvailability.setAdapter(adapter);
    }



    private void setupSaveButton() {
        btnSave.setOnClickListener(v -> {
            if (validateFields()) {
                saveTechnician();
            }
        });
    }

    private boolean validateFields() {
        boolean isValid = true;

            // Validate DNI
            if (getTextFromInput(tilDni).isEmpty()) {
                tilDni.setError("DNI es requerido");
                isValid = false;
            }

            // Validate Name
            if (getTextFromInput(tilName).isEmpty()) {
                tilName.setError("Nombre es requerido");
                isValid = false;
            }

            // Validate Surname
            if (getTextFromInput(tilSurname).isEmpty()) {
                tilSurname.setError("Apellidos son requeridos");
                isValid = false;
            }

            // Validate Email
            String email = getTextFromInput(tilEmail);
            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                tilEmail.setError("Email válido es requerido");
                isValid = false;
            }

            // Validate Phone
            if (getTextFromInput(tilPhone).isEmpty()) {
                tilPhone.setError("Teléfono es requerido");
                isValid = false;
            }

            // Validate Address
            if (getTextFromInput(tilAddress).isEmpty()) {
                tilAddress.setError("Dirección es requerida");
                isValid = false;
            }

            // Validate Username
            if (getTextFromInput(tilUsername).isEmpty()) {
                tilUsername.setError("Usuario es requerido");
                isValid = false;
            }

            // Validate Password
            if (getTextFromInput(tilPassword).isEmpty()) {
                tilPassword.setError("Contraseña es requerida");
                isValid = false;
            }

            // Validate Availability
            if (spinnerAvailability.getText().toString().isEmpty()) {
                spinnerAvailability.setError("Disponibilidad es requerida");
                isValid = false;
            }

        return isValid;
    }

    private String getTextFromInput(TextInputLayout textInputLayout) {
        return textInputLayout.getEditText() != null ?
                textInputLayout.getEditText().getText().toString().trim() : "";
    }

    private void saveTechnician() {
        if (saveAddTechnicianListener != null) {
            try {
                Technician newTechnician = new Technician(
                        getTextFromInput(tilDni),
                        getTextFromInput(tilName),
                        getTextFromInput(tilSurname),
                        getTextFromInput(tilEmail),
                        getTextFromInput(tilPhone),
                        getTextFromInput(tilAddress),
                        getTextFromInput(tilUsername),
                        getTextFromInput(tilPassword),
                        spinnerAvailability.getText().toString()
                );

                saveAddTechnicianListener.onSaveAddTechnician(newTechnician);
            } catch (Exception e) {
                Toast.makeText(requireContext(),
                        "Error al crear técnico: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            saveAddTechnicianListener = (OnSaveAddTechnician) requireActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " debe implementar OnSaveAddTechnician");
        }
    }


}