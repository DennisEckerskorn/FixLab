package com.app.fixlab.ui.fragments.devicefragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.app.fixlab.R;
import com.app.fixlab.adapters.DeviceFragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DeviceFragment extends Fragment {

    public DeviceFragment() {
        super(R.layout.activity_devices);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 vp2Devices = view.findViewById(R.id.vp2Devices);
        TabLayout tabLayout = view.findViewById(R.id.layoutTab);
        DeviceFragmentStateAdapter deviceFragmentStateAdapter = new DeviceFragmentStateAdapter(requireActivity());
        vp2Devices.setAdapter(deviceFragmentStateAdapter);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        TabLayoutMediator tlm = new TabLayoutMediator(tabLayout, vp2Devices, (tab, position) -> {
            String text = switch (position) {
                case 0 -> getString(R.string.list_name);
                case 1 -> getString(R.string.add_name);
                case 2 -> getString(R.string.repaired_list_name);
                default -> "";

            };
            tab.setText(text);
        });
        tlm.attach();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
