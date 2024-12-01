package com.app.fixlab.ui.fragments.clientfragments;

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

public class ClientDetailFragment extends Fragment {
    public interface IClientDetailFragmentListener {
        Person getClient();
    }

    private Person selectedClient;

    public ClientDetailFragment() {
        super(R.layout.detail_client_item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: Implement device list
        //private TextView tvClientDevicesDetailValue;

        TextView tvNameDetailValue = view.findViewById(R.id.tvNameDetailValue);
        TextView tvPhoneDetailValue = view.findViewById(R.id.tvPhoneDetailValue);
        TextView tvEmailDetailValue = view.findViewById(R.id.tvEmailDetailValue);
        TextView tvClientAddressDetailValue = view.findViewById(R.id.tvClientAddressDetailValue);
        if (selectedClient != null) {
            tvNameDetailValue.setText(selectedClient.getName());
            tvPhoneDetailValue.setText(selectedClient.getPhoneNumber());
            tvEmailDetailValue.setText(selectedClient.getEmail());
            tvClientAddressDetailValue.setText(selectedClient.getAddress());
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        IClientDetailFragmentListener listener = (IClientDetailFragmentListener) requireActivity();
        selectedClient = listener.getClient();
        Toast.makeText(context, "Client selected: " + selectedClient.getName(), Toast.LENGTH_SHORT).show();
    }
}