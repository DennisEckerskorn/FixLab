package com.app.fixlab.ui.fragments.clientfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.app.fixlab.R;
import com.app.fixlab.adapters.clientadapters.ClientFragmentStateAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * ClientFragment serves as the container for managing and displaying client-related views.
 * It uses a {@link ViewPager2} to host two tabs: one for listing clients and another for adding a client.
 *
 * <p>The layout for this fragment is defined in {@code R.layout.activity_client}.</p>
 *
 * <p>It integrates with:
 * <ul>
 *   <li>{@link TabLayout} for displaying tabs.</li>
 *   <li>{@link ViewPager2} for managing swipeable pages within the tabs.</li>
 *   <li>{@link ClientFragmentStateAdapter} for providing fragments for each page.</li>
 * </ul>
 * </p>
 *
 * <p>The two tabs displayed:
 * <ul>
 *   <li>"List" - Shows a list of existing clients.</li>
 *   <li>"Add" - Provides an interface to add new clients.</li>
 * </ul>
 * </p>
 *
 * @author [Dennis Eckerskorn]
 */
public class ClientFragment extends Fragment {

    /**
     * Default constructor. Initializes the fragment with its layout.
     */
    public ClientFragment() {
        super(R.layout.activity_client);
    }

    /**
     * Called when the view hierarchy for this fragment is created.
     * Sets up the {@link ViewPager2} and {@link TabLayout} components to display
     * client management tabs, and connects them using {@link TabLayoutMediator}.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager2 vp2Clients = view.findViewById(R.id.vp2Client);
        TabLayout tabLayout = view.findViewById(R.id.layoutTab);

        ClientFragmentStateAdapter clientFragmentStateAdapter = new ClientFragmentStateAdapter(requireActivity());
        vp2Clients.setAdapter(clientFragmentStateAdapter);

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

    /**
     * Called when the fragment is attached to its host activity.
     * This method can be used to initialize any dependencies on the hosting activity.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
