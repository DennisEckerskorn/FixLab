package com.app.fixlab.ui.fragments.technicianfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.models.persons.Person;

public class TechnicianDetailFragment extends Fragment {
    public interface ITechniciantDetailFragmentListener {
        Person getTechnician();
    }

    private Person selectedTechnician;
    private TextView tvNameDetailValue;
    private TextView tvDniDetailValue;
    private TextView tvSurnameDetailValue;
    private TextView tvEmailDetailValue;
    private TextView tvPhoneDetailValue;
    private TextView tvTechnicianAddressDetailValue;
    private TextView tvAvailabilityDetailValue;
    //TODO: Implement device list
    private ITechniciantDetailFragmentListener listener;

    public TechnicianDetailFragment() {
        super(R.layout.detail_technician_item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNameDetailValue = view.findViewById(R.id.tvTechnicianName);
        tvDniDetailValue = view.findViewById(R.id.tvTechnicianDni);
        tvSurnameDetailValue = view.findViewById(R.id.tvTechnicianSurname);
        tvEmailDetailValue = view.findViewById(R.id.tvTechnicianEmail);
        tvPhoneDetailValue = view.findViewById(R.id.tvTechnicianPhone);
        tvTechnicianAddressDetailValue = view.findViewById(R.id.tvTechnicianAddress);
        tvAvailabilityDetailValue = view.findViewById(R.id.tvTechnicianAvailability);

        tvNameDetailValue.setText(selectedTechnician.getName());
        tvDniDetailValue.setText(selectedTechnician.getDni());
        tvSurnameDetailValue.setText(selectedTechnician.getSurname());
        tvEmailDetailValue.setText(selectedTechnician.getEmail());
        tvPhoneDetailValue.setText(selectedTechnician.getPhoneNumber());
        tvTechnicianAddressDetailValue.setText(selectedTechnician.getAddress());
        //TODO: Implement device list tvAvailabilityDetailValue.setText(selectedTechnician.getAvailability());

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ITechniciantDetailFragmentListener) requireActivity();
        selectedTechnician = listener.getTechnician();
        Toast.makeText(context, "Technician selected: " + selectedTechnician.getName(), Toast.LENGTH_SHORT).show();
    }
}
