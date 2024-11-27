package com.app.fixlab.ui.fragments.devicefragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.app.fixlab.R;
import com.app.fixlab.adapters.ClientFragmentStateAdapter;
import com.app.fixlab.adapters.DeviceFragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DeviceFragment extends Fragment {

    public DeviceFragment() {
    //todo preguntar a dennis si uso un nuevo layout o mantenemos ese cambiandole el nombre
        super(R.layout.activity_client);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 vp2Clients = view.findViewById(R.id.vp2Client);
        TabLayout tabLayout = view.findViewById(R.id.layoutTab);
        DeviceFragmentStateAdapter deviceFragmentStateAdapter = new DeviceFragmentStateAdapter(requireActivity());
        vp2Clients.setAdapter(deviceFragmentStateAdapter);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        TabLayoutMediator tlm = new TabLayoutMediator(tabLayout, vp2Clients, (tab, position) -> {
            String text = switch (position) {
                case 0 -> getString(R.string.list_name);
                case 1 -> getString(R.string.add_name);
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
