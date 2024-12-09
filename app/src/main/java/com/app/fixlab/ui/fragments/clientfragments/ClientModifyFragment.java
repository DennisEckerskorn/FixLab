package com.app.fixlab.ui.fragments.clientfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.listeners.OnModifyListener;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.ui.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

/**
 * ClientModifyFragment is a Fragment used for editing and saving changes to a client's details.
 *
 * <p>This fragment allows users to modify the details of a selected client, such as their name,
 * email, phone number, and address. It includes validation to ensure that the inputs conform
 * to expected formats.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.fragment_modify_client}.</p>
 *
 * <p>Key features:
 * <ul>
 *   <li>Displays editable fields for client details.</li>
 *   <li>Performs validation on user inputs, including format checks for email and phone number.</li>
 *   <li>Communicates updates to a listener implementing {@link OnModifyListener}.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link OnModifyListener} to handle saving of updated client details.</li>
 *   <li>{@link MainActivity} to retrieve the selected client's data.</li>
 * </ul>
 * </p>
 *
 * @author [Dennis, Borja]
 * @version 1.0
 */
public class ClientModifyFragment extends Fragment {
    private Person selectedPerson;
    private TextInputEditText etName, etEmail, etPhone, etAddress;
    private OnModifyListener modifyListener;
    Button btnSave;

    /**
     * Default constructor. Initializes the fragment with its layout resource.
     */
    public ClientModifyFragment() {
        super(R.layout.fragment_modify_client);
    }

    /**
     * Called when the view hierarchy for this fragment is created.
     * Sets up the view elements and initializes data for editing.
     *
     * @param view               The root view of the fragment.
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
     * Initializes the views, including setting up the save button's click listener.
     *
     * @param view The root view of the fragment.
     */
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

    /**
     * Populates the input fields with the selected client's current details.
     */
    private void setupInitialData() {
        if (selectedPerson != null) {
            etName.setText(selectedPerson.getName());
            etEmail.setText(selectedPerson.getEmail());
            etPhone.setText(selectedPerson.getPhoneNumber());
            etAddress.setText(selectedPerson.getAddress());
        }
    }

    /**
     * Validates the input fields for correctness.
     *
     * @return true if all fields pass validation; false otherwise.
     */
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


    /**
     * Saves the modified client details by notifying the listener.
     */
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

    /**
     * Called when the fragment is attached to its host activity.
     * Sets up the listener and retrieves the selected client for modification.
     *
     * @param context The context of the host activity.
     */
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
