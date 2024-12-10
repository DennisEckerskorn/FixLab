package com.app.fixlab.ui.fragments.technicianfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.OnDeleteListener;
import com.app.fixlab.listeners.OnModifyListener;
import com.app.fixlab.models.persons.Technician;

/**
 * TechnicianDetailFragment displays the details of a selected technician.
 *
 * <p>This fragment allows the user to view and interact with the details of a technician,
 * including their availability, personal details, and contact information.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.detail_technician_item}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Displays detailed information about a technician, including name, email, and availability.</li>
 *   <li>Allows the user to modify the technician's availability using a toggle switch.</li>
 *   <li>Provides buttons to delete the technician or modify their details.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link IdataProvider} interface for retrieving the selected technician's data.</li>
 *   <li>{@link OnDeleteListener} interface for handling delete events.</li>
 *   <li>{@link OnModifyListener} interface for handling modify events.</li>
 * </ul>
 * </p>
 *
 * @author Borja Bolufer
 */
public class TechnicianDetailFragment extends Fragment {

    private IdataProvider technicianProvider;
    private OnModifyListener modifyListener;
    private OnDeleteListener buttonListener;
    private Technician selectedTechnician;

    private TextView tvNameDetailValue;
    private TextView tvDniDetailValue;
    private TextView tvSurnameDetailValue;
    private TextView tvEmailDetailValue;
    private TextView tvPhoneDetailValue;
    private TextView tvTechnicianAddressDetailValue;
    private TextView tvAvailabilityDetailValue;
    private Switch switchAvailability;

    /**
     * Default constructor for TechnicianDetailFragment.
     * Sets the layout for the fragment to {@code R.layout.detail_technician_item}.
     */
    public TechnicianDetailFragment() {
        super(R.layout.detail_technician_item);
    }

    /**
     * Called when the view hierarchy of the fragment is created.
     * Initializes the views and sets up listeners for user interactions.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        tvNameDetailValue = view.findViewById(R.id.tvTechnicianName);
        tvDniDetailValue = view.findViewById(R.id.tvTechnicianDni);
        tvSurnameDetailValue = view.findViewById(R.id.tvTechnicianSurname);
        tvEmailDetailValue = view.findViewById(R.id.tvTechnicianEmail);
        tvPhoneDetailValue = view.findViewById(R.id.tvTechnicianPhone);
        tvTechnicianAddressDetailValue = view.findViewById(R.id.tvTechnicianAddress);
        tvAvailabilityDetailValue = view.findViewById(R.id.tvTechnicianAvailability);
        switchAvailability = view.findViewById(R.id.switchAvailability);

        // Set technician details
        tvNameDetailValue.setText(selectedTechnician.getName());
        tvDniDetailValue.setText(selectedTechnician.getDni());
        tvSurnameDetailValue.setText(selectedTechnician.getSurname());
        tvEmailDetailValue.setText(selectedTechnician.getEmail());
        tvPhoneDetailValue.setText(selectedTechnician.getPhoneNumber());
        tvTechnicianAddressDetailValue.setText(selectedTechnician.getAddress());

        // Set availability details and toggle listener
        if (selectedTechnician != null) {
            Technician.Availability availability = selectedTechnician.getAvailability();
            tvAvailabilityDetailValue.setText(availability.toString());
            switchAvailability.setChecked(availability == Technician.Availability.AVAILABLE);

            int color = availability == Technician.Availability.AVAILABLE
                    ? R.color.neon_green
                    : R.color.red;
            tvAvailabilityDetailValue.setTextColor(ContextCompat.getColor(tvAvailabilityDetailValue.getContext(), color));
        }

        switchAvailability.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (selectedTechnician != null) {
                Technician.Availability newAvailability = isChecked
                        ? Technician.Availability.AVAILABLE
                        : Technician.Availability.UNAVAILABLE;

                selectedTechnician.setAvailability(newAvailability);
                tvAvailabilityDetailValue.setText(newAvailability.toString());

                int color = newAvailability == Technician.Availability.AVAILABLE
                        ? R.color.neon_green
                        : R.color.red;
                tvAvailabilityDetailValue.setTextColor(ContextCompat.getColor(tvAvailabilityDetailValue.getContext(), color));
            }
        });

        // Set delete button listener
        Button btnDelete = view.findViewById(R.id.btnDeleteOnTechnicianDetail);
        btnDelete.setOnClickListener(v -> buttonListener.onDeleteClient());

        // Set modify button listener
        Button btnModifyTechnician = view.findViewById(R.id.btnModifyTechnician);
        btnModifyTechnician.setOnClickListener(v -> modifyListener.onModifyButtonTechnician());
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Retrieves required listeners and the selected technician from the activity context.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // Retrieve listeners and selected technician from the activity
        technicianProvider = (IdataProvider) requireActivity();
        buttonListener = (OnDeleteListener) requireActivity();
        modifyListener = (OnModifyListener) requireActivity();
        selectedTechnician = (Technician) technicianProvider.getTechnician();

        // Display a toast message with the selected technician's name
        Toast.makeText(context, "Technician selected: " + selectedTechnician.getName(), Toast.LENGTH_SHORT).show();
    }
}