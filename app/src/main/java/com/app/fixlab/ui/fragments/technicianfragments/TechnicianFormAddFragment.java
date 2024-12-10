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

/**
 * TechnicianFormAddFragment allows the user to add a new technician by filling out a form.
 *
 * <p>This fragment provides input fields for technician details and validates the input before saving.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.activity_add_technician}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Provides input fields for technician details such as name, email, and availability.</li>
 *   <li>Validates user input to ensure all fields are correctly filled.</li>
 *   <li>Invokes a callback to save the technician when the form is valid.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link OnSaveAddTechnician} interface for saving the new technician.</li>
 * </ul>
 * </p>
 *
 * @author Borja Bolufer
 */
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

    /**
     * Default constructor for TechnicianFormAddFragment.
     * Sets the layout for the fragment to {@code R.layout.activity_add_technician}.
     */
    public TechnicianFormAddFragment() {
        super(R.layout.activity_add_technician);
    }

    /**
     * Called when the view hierarchy of the fragment is created.
     * Initializes the views and sets up the form logic.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        setupAvailabilitySpinner();
        setupSaveButton();
    }

    /**
     * Initializes the views for the fragment.
     *
     * @param view The root view of the fragment.
     */
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

    /**
     * Sets up the availability dropdown menu with predefined options.
     */
    private void setupAvailabilitySpinner() {
        String[] availabilityOptions = {"AVAILABLE", "UNAVAILABLE"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                availabilityOptions
        );
        spinnerAvailability.setAdapter(adapter);
    }

    /**
     * Sets up the save button to validate the form and save the technician.
     */
    private void setupSaveButton() {
        btnSave.setOnClickListener(v -> {
            if (validateFields()) {
                saveTechnician();
            }
        });
    }

    /**
     * Validates the input fields to ensure all required fields are correctly filled.
     *
     * @return {@code true} if all fields are valid, {@code false} otherwise.
     */
    private boolean validateFields() {
        boolean isValid = true;

        // Validate DNI
        if (getTextFromInput(tilDni).isEmpty()) {
            tilDni.setError("DNI is required");
            isValid = false;
        }

        // Validate Name
        if (getTextFromInput(tilName).isEmpty()) {
            tilName.setError("Name is required");
            isValid = false;
        }

        // Validate Surname
        if (getTextFromInput(tilSurname).isEmpty()) {
            tilSurname.setError("Surname is required");
            isValid = false;
        }

        // Validate Email
        String email = getTextFromInput(tilEmail);
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Valid email is required");
            isValid = false;
        }

        // Validate Phone
        if (getTextFromInput(tilPhone).isEmpty()) {
            tilPhone.setError("Phone is required");
            isValid = false;
        }

        // Validate Address
        if (getTextFromInput(tilAddress).isEmpty()) {
            tilAddress.setError("Address is required");
            isValid = false;
        }

        // Validate Username
        if (getTextFromInput(tilUsername).isEmpty()) {
            tilUsername.setError("Username is required");
            isValid = false;
        }

        // Validate Password
        if (getTextFromInput(tilPassword).isEmpty()) {
            tilPassword.setError("Password is required");
            isValid = false;
        }

        // Validate Availability
        if (spinnerAvailability.getText().toString().isEmpty()) {
            spinnerAvailability.setError("Availability is required");
            isValid = false;
        }

        return isValid;
    }

    /**
     * Retrieves the trimmed text from a {@link TextInputLayout}.
     *
     * @param textInputLayout The input field to retrieve text from.
     * @return The trimmed text or an empty string if the input is null.
     */
    private String getTextFromInput(TextInputLayout textInputLayout) {
        return textInputLayout.getEditText() != null ?
                textInputLayout.getEditText().getText().toString().trim() : "";
    }

    /**
     * Creates a new Technician object with the input data and invokes the save listener.
     */
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
                        "Error creating technician: " + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Retrieves the {@link OnSaveAddTechnician} listener from the activity context.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            saveAddTechnicianListener = (OnSaveAddTechnician) requireActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSaveAddTechnician");
        }
    }
}