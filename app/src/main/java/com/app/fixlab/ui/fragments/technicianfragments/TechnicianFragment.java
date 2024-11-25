package com.app.fixlab.ui.fragments.technicianfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.app.fixlab.R;
import com.app.fixlab.adapters.TechnicianFragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TechnicianFragment extends Fragment{

    public TechnicianFragment() {
        super(R.layout.activity_technician);
    }
        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
            super.onViewCreated(view, savedInstanceState);
            ViewPager2 vp2Technician = view.findViewById(R.id.vp2Tec);
            TabLayout tabLayout = view.findViewById(R.id.layoutTab);

            TechnicianFragmentStateAdapter technicianFragmentStateAdapter = new TechnicianFragmentStateAdapter(requireActivity());
            vp2Technician.setAdapter(technicianFragmentStateAdapter);

            tabLayout.setTabMode(TabLayout.MODE_FIXED);

            TabLayoutMediator tlm = new TabLayoutMediator(tabLayout, vp2Technician, (tab, position) -> {
                String text = switch (position) {
                    case 0 -> getString(R.string.list_name);
                    case 1 -> getString(R.string.add_name);
                    default -> "";
            };
                tab.setText(text);
            });
            tlm.attach();
        }
        public void onAttach(@NonNull Context context){
            super.onAttach(context);
        }
}