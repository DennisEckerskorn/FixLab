package com.app.fixlab.ui.fragments.clientfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.adapters.DevicesAdapter;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.MenuActionListener;
import com.app.fixlab.listeners.OnDeleteListener;
import com.app.fixlab.listeners.OnDeviceClickListener;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.ui.MainActivity;

import java.util.List;

public class ClientDetailFragment extends Fragment {
    private Person selectedClient;
    private List<Device> devices;
    private OnDeviceClickListener itemClickListener;
    private OnDeleteListener buttonListener;

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
        RecyclerView rvClientDevices = view.findViewById(R.id.rvClientDevices);


        DevicesAdapter devicesAdapter = new DevicesAdapter(devices);
        devicesAdapter.setListener(itemClickListener);
        rvClientDevices.setAdapter(devicesAdapter);
        rvClientDevices.setHasFixedSize(true);
        rvClientDevices.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        if (selectedClient != null) {
            tvNameDetailValue.setText(selectedClient.getName());
            tvPhoneDetailValue.setText(selectedClient.getPhoneNumber());
            tvEmailDetailValue.setText(selectedClient.getEmail());
            tvClientAddressDetailValue.setText(selectedClient.getAddress());
        }
        Button btnDelete = view.findViewById(R.id.btnDelete);
        Button btnModify = view.findViewById(R.id.btnModify);


        btnDelete.setOnClickListener(v -> {
            buttonListener.onDeleteClient();
        });

        btnModify.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity){
                ((MainActivity) getActivity()).replaceFragment(new ClientModifyFragment(), true);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        itemClickListener = (OnDeviceClickListener) requireActivity();
        buttonListener = (OnDeleteListener) requireActivity();
        devices = dataProvider.getDeviceOfClient();
        selectedClient = dataProvider.getClient();
        Toast.makeText(context, "Client selected: " + selectedClient.getName(), Toast.LENGTH_SHORT).show();
    }
}