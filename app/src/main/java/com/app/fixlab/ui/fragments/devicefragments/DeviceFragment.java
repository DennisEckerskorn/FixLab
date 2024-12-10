package com.app.fixlab.ui.fragments.devicefragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.app.fixlab.R;
import com.app.fixlab.adapters.deviceadapters.DeviceFragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Fragment that hosts device-related tabs for listing, adding, and viewing repaired devices.
 * Uses a ViewPager2 and TabLayout for tabbed navigation.
 */
public class DeviceFragment extends Fragment {

    /**
     * Default constructor that sets the layout resource for the fragment.
     */
    public DeviceFragment() {
        super(R.layout.activity_devices);
    }

    /**
     * Called when the fragment's view is created.
     * Sets up the ViewPager2, TabLayout, and attaches a TabLayoutMediator to manage the tabs.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState Saved state of the fragment, if available.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the ViewPager2 and TabLayout
        ViewPager2 vp2Devices = view.findViewById(R.id.vp2Devices);
        TabLayout tabLayout = view.findViewById(R.id.layoutTab);

        // Set the adapter for the ViewPager2
        DeviceFragmentStateAdapter deviceFragmentStateAdapter = new DeviceFragmentStateAdapter(requireActivity());
        vp2Devices.setAdapter(deviceFragmentStateAdapter);

        // Configure TabLayout for fixed mode
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        // Attach TabLayoutMediator to synchronize tabs with the ViewPager2
        TabLayoutMediator tlm = new TabLayoutMediator(tabLayout, vp2Devices, (tab, position) -> {
            // Set the tab text based on the position
            String text = switch (position) {
                case 0 -> getString(R.string.list_name); // "List" tab
                case 1 -> getString(R.string.add_name); // "Add" tab
                case 2 -> getString(R.string.repaired_list_name); // "Repaired Devices" tab
                default -> ""; // Fallback for invalid position
            };
            tab.setText(text);
        });
        tlm.attach(); // Attach the TabLayoutMediator
    }

    /**
     * Called when the fragment is attached to its host activity.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
