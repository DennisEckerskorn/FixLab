package com.app.fixlab.adapters.deviceadapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.fixlab.ui.fragments.devicefragments.DeviceListFragment;
import com.app.fixlab.ui.fragments.devicefragments.DevicesFormAddFragment;

/**
 * A {@link FragmentStateAdapter} implementation that manages fragments
 * representing different pages in a ViewPager2.
 * <p>This adapter manages two fragments:
 * <ul>
 *     <li>{@link DeviceListFragment}: Displays a list of devices.</li>
 *     <li>{@link DevicesFormAddFragment}: Provides a form to add a new device.</li>
 * </ul>
 * @author [Jon]
 */
public class DeviceFragmentStateAdapter extends FragmentStateAdapter {

    /**
     * The total number of pages managed by the adapter.
     */
    private static final int NUM_PAGES = 2;

    /**
     * Constructs a new {@link DeviceFragmentStateAdapter}.
     *
     * @param fragmentActivity The {@link FragmentActivity} that hosts the adapter.
     */
    public DeviceFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * Creates a new fragment for the given position in the ViewPager2.
     *
     * @param position The position of the fragment to create.
     *                 <ul>
     *                     <li>0: {@link DeviceListFragment}</li>
     *                     <li>1: {@link DevicesFormAddFragment}</li>
     *                 </ul>
     * @return A {@link Fragment} instance corresponding to the specified position.
     * @throws RuntimeException if the position is invalid.
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position) {
            case 0 -> new DeviceListFragment();
            case 1 -> new DevicesFormAddFragment();
            default -> throw new RuntimeException("Invalid fragment position: " + position);
        };
    }

    /**
     * Returns the total number of pages (fragments) managed by this adapter.
     *
     * @return The total number of pages.
     */
    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
