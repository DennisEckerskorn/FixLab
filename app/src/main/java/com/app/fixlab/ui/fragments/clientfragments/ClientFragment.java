package com.app.fixlab.ui.fragments.clientfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.app.fixlab.R;
import com.app.fixlab.adapters.ClientFragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ClientFragment extends Fragment {

    public ClientFragment() {
        super(R.layout.activity_clients);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 vp2Clients = view.findViewById(R.id.vp2Clients);
        TabLayout tabLayout = view.findViewById(R.id.layoutTab);

        ClientFragmentStateAdapter clientFragmentStateAdapter = new ClientFragmentStateAdapter(requireActivity());
        vp2Clients.setAdapter(clientFragmentStateAdapter);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        TabLayoutMediator tlm = new TabLayoutMediator(tabLayout, vp2Clients, (tab, position) -> {
            String text = switch (position) {
                case 0 -> "Lista";
                case 1 -> "Añadir";
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
