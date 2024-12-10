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

/**
 * Fragment that displays a list of devices using a RecyclerView.
 * Devices can be interacted with via a click listener.
 */
public class DeviceListFragment extends Fragment {

    private List<Device> devices;
    private OnDeviceClickListener fragmentListener;

    /**
     * Default constructor that sets the layout resource for the fragment.
     */
    public DeviceListFragment() {
        super(R.layout.fragment_list);
    }

    /**
     * Called when the fragment's view is created.
     * Sets up the RecyclerView with an adapter, layout manager, and click listener.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState Saved state of the fragment, if available.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the RecyclerView
        RecyclerView rvList = view.findViewById(R.id.rvList);

        // Set up the adapter with the device data
        DevicesAdapter devicesAdapter = new DevicesAdapter(devices);
        devicesAdapter.setListener(fragmentListener);
        rvList.setAdapter(devicesAdapter);

        // Configure the RecyclerView for a fixed size and vertical layout
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }
    /**
     * Called when the fragment is attached to its host activity.
     * Retrieves the list of devices and the click listener from the activity.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Get the data provider and listener from the host activity
            IdataProvider dataProvider = (IdataProvider) requireActivity();
            fragmentListener = (OnDeviceClickListener) requireActivity();

            // Retrieve the device data
            devices = dataProvider.getDeviceData();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement IdataProvider and OnDeviceClickListener.");
        }
    }
}
