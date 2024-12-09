package com.app.fixlab.ui.fragments.repairfragments;

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
import com.app.fixlab.listeners.IOnItemRepairClickListener;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.models.devices.Device;

import java.util.List;

/**
 * RepairedDeviceListFragment displays a list of repaired devices.
 *
 * <p>This fragment retrieves the list of devices and displays them in a RecyclerView.
 * It allows interaction with each item via a click listener, which is handled by the
 * {@link IOnItemRepairClickListener} interface.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.fragment_list}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Displays a list of repaired devices using {@link DevicesAdapter}.</li>
 *   <li>Handles item click interactions via the {@link IOnItemRepairClickListener} listener.</li>
 *   <li>Sets up a vertical RecyclerView with a fixed size.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link IdataProvider} interface for retrieving the device data.</li>
 *   <li>{@link IOnItemRepairClickListener} interface for handling item click events.</li>
 * </ul>
 * </p>
 *
 * @author [Dennis Eckerskorn, Jon]
 */
public class RepairedDeviceListFragment extends Fragment {
    private IOnItemRepairClickListener repairDeviceClickListener;
    private List<Device> devices;

    /**
     * Default constructor. Initializes the fragment with its layout resource.
     */
    public RepairedDeviceListFragment() {
        super(R.layout.fragment_list);
    }

    /**
     * Called when the view hierarchy for this fragment is created.
     * Sets up the RecyclerView to display the list of devices.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = view.findViewById(R.id.rvList);

        // Set the adapter for the recycler view
        DevicesAdapter devicesAdapter = new DevicesAdapter(devices);
        devicesAdapter.setRepairDeviceClickListener(repairDeviceClickListener);
        rvList.setAdapter(devicesAdapter);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Initializes dependencies and retrieves the device data.
     *
     * @param context The context of the host activity.
     * @throws ClassCastException if the host activity does not implement required interfaces.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        repairDeviceClickListener = (IOnItemRepairClickListener) requireActivity();
        devices = dataProvider.getDeviceData();
    }
}
