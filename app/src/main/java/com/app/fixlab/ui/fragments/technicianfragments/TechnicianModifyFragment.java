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

/**
 * TechnicianModifyFragment allows editing details of an existing technician.
 *
 * <p>This fragment provides input fields to modify technician details and save the changes.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.fragment_modify_technician}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Allows modification of technician details like name, email, and address.</li>
 *   <li>Validates user input before saving changes.</li>
 *   <li>Updates the technician through a callback listener.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link OnModifyListener} interface for handling technician updates.</li>
 *   <li>{@link OnDeviceClickListener} interface for managing associated devices.</li>
 *   <li>{@link IdataProvider} interface for accessing technician data.</li>
 * </ul>
 * </p>
 *
 * @author Borja Bolufer
 */
public class TechnicianModifyFragment extends Fragment {
    private TextInputEditText etDNI, etName, etSurname, etEmail, etPhone, etAddress, etPassword, etUsername;
    private OnModifyListener modifyListener;
    private Technician selectedTechnician;
    private List<Device> devices;
    private OnDeviceClickListener itemClickListener;
    private Button btnSave;

    /**
     * Default constructor for TechnicianModifyFragment.
     * Sets the layout for the fragment to {@code R.layout.fragment_modify_technician}.
     */
    public TechnicianModifyFragment() {
        super(R.layout.fragment_modify_technician);
    }

    /**
     * Called when the view hierarchy of the fragment is created.
     * Initializes the views and sets up initial data.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupInitialData();
    }

    /**
     * Initializes the views for the fragment.
     *
     * @param view The root view of the fragment.
     */
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

    /**
     * Sets up the initial data for the fields based on the selected technician.
     */
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

    /**
     * Validates the input fields to ensure all required fields are filled.
     *
     * @return {@code true} if all fields are valid, {@code false} otherwise.
     */
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

    /**
     * Saves the changes to the technician's details and notifies the listener.
     */
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

    /**
     * Called when the fragment is attached to its host activity.
     * Retrieves dependencies and initializes the technician data.
     *
     * @param context The context of the host activity.
     */
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