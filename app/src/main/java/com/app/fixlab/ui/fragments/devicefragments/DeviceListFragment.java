package com.app.fixlab.ui.fragments.devicefragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.adapters.deviceadapters.DevicesAdapter;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.OnDeviceClickListener;
import com.app.fixlab.models.devices.Device;

import java.util.List;


public class DeviceListFragment extends Fragment {

    private List<Device> devices;
    private OnDeviceClickListener fragmentListener;

    public DeviceListFragment() {
        super(R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = view.findViewById(R.id.rvList);

        // Set the adapter for the recycler view
        DevicesAdapter devicesAdapter = new DevicesAdapter(devices);
        devicesAdapter.setListener(fragmentListener);
        rvList.setAdapter(devicesAdapter);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Get the listener for the client list fragment
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        fragmentListener = (OnDeviceClickListener) requireActivity();
        devices = dataProvider.getDeviceData();
    }


}
