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

/**
 * MainMenuFragment represents the main menu of the application, providing
 * navigation options for clients, devices, technicians, starting reparations,
 * and viewing completed repairs.
 * This fragment inflates a layout containing buttons and uses a
 * {@link MenuActionListener} interface to communicate user actions to the
 * hosting activity.
 *
 * <p>The fragment assumes that the hosting activity implements
 * {@link MenuActionListener} for handling button click events.</p>
 *
 * @author [Dennis Eckerskorn]
 */
public class MainMenuFragment extends Fragment {
    private MenuActionListener menuActionListener;

    /**
     * Default constructor for MainMenuFragment.
     * Sets the layout resource for this fragment to {@code R.layout.activity_menu}.
     */
    public MainMenuFragment() {
        super(R.layout.activity_menu);
    }

    /**
     * Called when the view hierarchy for this fragment is created.
     * Initializes the buttons in the layout and sets click listeners to trigger
     * corresponding actions through the {@link MenuActionListener}.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize buttons
        Button bClients = view.findViewById(R.id.bClients);
        Button bDevices = view.findViewById(R.id.bDevices);
        Button bTechnicians = view.findViewById(R.id.bTechnicians);
        Button bStartReparation = view.findViewById(R.id.bStartReparation);
        Button bCompletedRepair = view.findViewById(R.id.bCompletedRepair);

        // Set click listeners
        bClients.setOnClickListener(v -> menuActionListener.onClientsSelected());
        bDevices.setOnClickListener(v -> menuActionListener.onDevicesSelected());
        bTechnicians.setOnClickListener(v -> menuActionListener.onTechniciansSelected());
        bStartReparation.setOnClickListener(v -> menuActionListener.onStartReparationSelected());
        bCompletedRepair.setOnClickListener(v -> menuActionListener.onRepairSummarySelected());
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Binds the {@link MenuActionListener} from the hosting activity to enable
     * communication of button click events.
     *
     * @param context The context of the host activity.
     * @throws ClassCastException if the hosting activity does not implement
     *                            {@link MenuActionListener}.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        menuActionListener = (MenuActionListener) requireActivity();
    }
}
