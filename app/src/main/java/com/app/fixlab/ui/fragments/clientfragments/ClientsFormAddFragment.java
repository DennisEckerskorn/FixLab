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

    public ClientsFormAddFragment() {
        super(R.layout.activity_add_client);
    }

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

                //TODO: ADJUST DATA; LAYOUT NEEDS TO BE ADAPTED:
                Client newClient = new Client(DNI, name, surname,  email, telefono, direccion, username, password); // Assuming your Client class has a constructor that takes these parameters

                saveAddClientListener.onSaveAddClient(newClient);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
       saveAddClientListener = (OnSaveAddClient) requireActivity();
    }
}
