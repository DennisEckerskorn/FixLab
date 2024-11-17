package com.app.fixlab.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.listeners.MenuActionListener;

public class MainMenuFragment extends Fragment {
    private MenuActionListener menuActionListener;

    public MainMenuFragment() {
        super(R.layout.activity_menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button bClients = view.findViewById(R.id.bClients);
        Button bDevices = view.findViewById(R.id.bDevices);
        Button bTechnicians = view.findViewById(R.id.bTechnicians);
        Button bStartReparation = view.findViewById(R.id.bStartReparation);

        // Set click listeners for the buttons
        bClients.setOnClickListener(v -> menuActionListener.onClientsSelected());
        bDevices.setOnClickListener(v -> menuActionListener.onDevicesSelected());
        bTechnicians.setOnClickListener(v -> menuActionListener.onTechniciansSelected());
        bStartReparation.setOnClickListener(v -> menuActionListener.onStartReparationSelected());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Check if the parent activity implements the MenuActionListener interface
        if (context instanceof MenuActionListener) {
            menuActionListener = (MenuActionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MenuActionListener");
        }
    }
}
