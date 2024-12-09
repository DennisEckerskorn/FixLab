package com.app.fixlab.adapters.technicianadapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.fixlab.ui.fragments.technicianfragments.TechnicianFormAddFragment;
import com.app.fixlab.ui.fragments.technicianfragments.TechnicianListFragment;

/**
 * CLIENT FRAGMENT STATE ADAPTER: Adapter for the technician fragment state
 */
public class TechnicianFragmentStateAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;

    /**
     * CONSTRUCTOR: Constructor for the fragment state adapter
     *
     * @param fragmentActivity Fragment activity
     */
    public TechnicianFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * CREATE FRAGMENT: Creates a fragment based on the position
     *
     * @param position Position of the fragment
     * @return Fragment based on the position
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position){
            case 0 -> new TechnicianListFragment();
            case 1 -> new TechnicianFormAddFragment();
            default -> throw new RuntimeException("Invalid fragment:");
        };
    }
    /**
     * GET ITEM COUNT: Returns the number of pages
     *
     * @return Number of pages
     */
    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
