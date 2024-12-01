package com.app.fixlab.ui.fragments.clientfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.adapters.DevicesAdapter;
import com.app.fixlab.listeners.IClientProvider;
import com.app.fixlab.listeners.IDeviceProvider;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.listeners.OnClickListenerDevices;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Person;

import java.util.List;

public class ClientDetailFragment extends Fragment {
    public interface IClientDetailFragmentListener {
        Person getClient();
    }

    private Person selectedClient;
    private List<Device> devices;
    private IonItemClickListenerGeneric<Device> itemClickListener;

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
        devicesAdapter.setListener((OnClickListenerDevices) itemClickListener);
        rvClientDevices.setAdapter(devicesAdapter);
        rvClientDevices.setHasFixedSize(true);
        rvClientDevices.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));





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
        IDeviceProvider deviceProvider = (IDeviceProvider) requireActivity();
        itemClickListener = (IonItemClickListenerGeneric<Device>) requireActivity();
        devices = deviceProvider.getDevicesOfClient();
        selectedClient = listener.getClient();
        Toast.makeText(context, "Client selected: " + selectedClient.getName(), Toast.LENGTH_SHORT).show();
    }
}