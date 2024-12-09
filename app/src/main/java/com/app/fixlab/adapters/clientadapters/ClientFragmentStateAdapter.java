package com.app.fixlab.adapters.clientadapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.app.fixlab.ui.fragments.clientfragments.ClientListFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientsFormAddFragment;

/**
 * CLIENT FRAGMENT STATE ADAPTER: Adapter for the client fragment state
 */
public class ClientFragmentStateAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;

    /**
     * CONSTRUCTOR: Constructor for the fragment state adapter
     *
     * @param fragmentActivity Fragment activity
     */
    public ClientFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
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
        return switch (position) {
            case 0 -> new ClientListFragment();
            case 1 -> new ClientsFormAddFragment();
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
