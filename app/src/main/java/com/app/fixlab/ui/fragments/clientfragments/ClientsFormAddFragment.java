package com.app.fixlab.ui.fragments.clientfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.listeners.OnSaveAddClient;
import com.app.fixlab.models.persons.Client;
import com.app.fixlab.models.persons.Person;
import com.google.android.material.textfield.TextInputLayout;

/**
 * ClientsFormAddFragment is a Fragment designed for adding new clients.
 *
 * <p>This fragment provides a form with input fields for entering a new client's details, such as
 * their name, email, phone number, and other relevant information. It interacts with a listener
 * to save the newly created client.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.activity_add_client}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Form fields with validation to ensure correct client details are entered.</li>
 *   <li>Integrates with {@link OnSaveAddClient} to handle the saving of a new client.</li>
 *   <li>Uses {@link TextInputLayout} for user-friendly input management.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link OnSaveAddClient} interface to handle actions when the "Save" button is clicked.</li>
 * </ul>
 * </p>
 *
 * @author [Dennis Eckerskorn]
 */
public class ClientsFormAddFragment extends Fragment {
    private OnSaveAddClient saveAddClientListener;
    private TextInputLayout tilDni;
    private TextInputLayout tilName;
    private TextInputLayout tilSurname;
    private TextInputLayout tilEmail;
    private TextInputLayout tilTelefono;
    private TextInputLayout tilDireccion;
    private TextInputLayout tilUsername;
    private TextInputLayout tilPassword;
    private Button bGuardar;

    /**
     * Default constructor. Initializes the fragment with its layout resource.
     */
    public ClientsFormAddFragment() {
        super(R.layout.activity_add_client);
    }

    /**
     * Called when the view hierarchy for this fragment is created.
     * Sets up the input fields and the save button's click listener.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilDni = view.findViewById(R.id.tilDni);
        tilName = view.findViewById(R.id.tilName);
        tilSurname = view.findViewById(R.id.tilSurname);
        tilEmail = view.findViewById(R.id.tilEmail);
        tilTelefono = view.findViewById(R.id.tilTelefono);
        tilDireccion = view.findViewById(R.id.tilDireccion);
        tilUsername = view.findViewById(R.id.tilUsername);
        tilPassword = view.findViewById(R.id.tilPassword);
        bGuardar = view.findViewById(R.id.bGuardar);

        bGuardar.setOnClickListener(v -> {
            if (saveAddClientListener != null) {
                // Get data from TextInputEditText views
                String DNI = tilDni.getEditText().getText().toString();
                String name = tilName.getEditText().getText().toString(); // Access EditText within TextInputLayout
                String surname = tilSurname.getEditText().getText().toString();
                String email = tilEmail.getEditText().getText().toString();
                String telefono = tilTelefono.getEditText().getText().toString();
                String direccion = tilDireccion.getEditText().getText().toString();
                String username = tilUsername.getEditText().getText().toString();
                String password = tilPassword.getEditText().getText().toString();

                Client newClient = new Client(DNI, name, surname, email, telefono, direccion, username, password); // Assuming your Client class has a constructor that takes these parameters

                saveAddClientListener.onSaveAddClient(newClient);
            }
        });
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Ensures the activity implements {@link OnSaveAddClient}.
     *
     * @param context The context of the host activity.
     * @throws ClassCastException if the host activity does not implement {@link OnSaveAddClient}.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        saveAddClientListener = (OnSaveAddClient) requireActivity();
    }
}
