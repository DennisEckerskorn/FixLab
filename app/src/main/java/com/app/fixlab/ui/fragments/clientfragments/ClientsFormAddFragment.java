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
    private TextInputLayout tilName;
    private TextInputLayout tilEmail;
    private TextInputLayout tilTelefono;
    private TextInputLayout tilDireccion;
    private Button bGuardar;

    public ClientsFormAddFragment() {
        super(R.layout.activity_add_client);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilName = view.findViewById(R.id.tilName);
        tilEmail = view.findViewById(R.id.tilEmail);
        tilTelefono = view.findViewById(R.id.tilTelefono);
        tilDireccion = view.findViewById(R.id.tilDireccion);
        bGuardar = view.findViewById(R.id.bGuardar);

        bGuardar.setOnClickListener(v -> {
            if (saveAddClientListener != null) {
                // Get data from TextInputEditText views
                String name = tilName.getEditText().getText().toString(); // Access EditText within TextInputLayout
                String email = tilEmail.getEditText().getText().toString();
                String telefono = tilTelefono.getEditText().getText().toString();
                String direccion = tilDireccion.getEditText().getText().toString();

                //TODO: ADJUST DATA; LAYOUT NEEDS TO BE ADAPTED:
                Client newClient = new Client("111", name, "Surname",  email, telefono, direccion, "Pepito", "****"); // Assuming your Client class has a constructor that takes these parameters

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
