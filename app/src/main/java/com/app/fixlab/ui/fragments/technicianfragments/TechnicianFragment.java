package com.app.fixlab.ui.fragments.technicianfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.app.fixlab.R;
import com.app.fixlab.adapters.technicianadapters.TechnicianFragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * TechnicianFragment is a container fragment that manages technician-related sub-fragments.
 *
 * <p>This fragment uses a {@link ViewPager2} to manage two tabs:
 * <ul>
 *   <li>Technician List: Displays a list of technicians.</li>
 *   <li>Add Technician: Provides a form to add a new technician.</li>
 * </ul>
 * </p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.activity_technician}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Uses a {@link TechnicianFragmentStateAdapter} to manage sub-fragments.</li>
 *   <li>Displays tabs using a {@link TabLayout} for navigation.</li>
 *   <li>Synchronizes tabs with the {@link ViewPager2} using {@link TabLayoutMediator}.</li>
 * </ul>
 * </p>
 *
 * @author Borja Bolufer
 */
public class TechnicianFragment extends Fragment {

    /**
     * Default constructor for TechnicianFragment.
     * Sets the layout for the fragment to {@code R.layout.activity_technician}.
     */
    public TechnicianFragment() {
        super(R.layout.activity_technician);
    }

    /**
     * Called when the view hierarchy of the fragment is created.
     * Initializes the ViewPager2 and TabLayout components.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewPager2 and TabLayout
        ViewPager2 vp2Technician = view.findViewById(R.id.vp2Tec);
        TabLayout tabLayout = view.findViewById(R.id.layoutTab);

        // Set up the adapter for managing sub-fragments
        TechnicianFragmentStateAdapter technicianFragmentStateAdapter = new TechnicianFragmentStateAdapter(requireActivity());
        vp2Technician.setAdapter(technicianFragmentStateAdapter);

        // Configure TabLayout to use fixed mode
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        // Attach TabLayoutMediator to synchronize tabs with ViewPager2
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