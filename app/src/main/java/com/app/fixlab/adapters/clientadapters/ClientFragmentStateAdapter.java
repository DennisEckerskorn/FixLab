package com.app.fixlab.adapters.clientadapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.app.fixlab.ui.fragments.clientfragments.ClientListFragment;
import com.app.fixlab.ui.fragments.clientfragments.ClientsFormAddFragment;

/**
 * ClientFragmentStateAdapter is an adapter that manages the fragments for the client section.
 * It is used in conjunction with a {@link ViewPager2} to display a set of fragments,
 * each representing different client-related views.
 * <p>
 * This adapter provides a way to display two main fragments: a client list and a client form for adding a new client.
 * </p>
 *
 * @author [Your Name]
 * @version 1.0
 * @see ClientListFragment
 * @see ClientsFormAddFragment
 */
public class ClientFragmentStateAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;

    /**
     * Constructor for ClientFragmentStateAdapter.
     *
     * @param fragmentActivity The {@link FragmentActivity} associated with this adapter.
     *                         This is typically the activity that hosts the {@link ViewPager2}.
     */
    public ClientFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    /**
     * Creates the fragment corresponding to the given position.
     *
     * @param position The position of the fragment within the {@link ViewPager2}.
     * @return The fragment at the specified position. Returns either {@link ClientListFragment} or
     * {@link ClientsFormAddFragment} based on the position.
     * @throws RuntimeException If an invalid position is provided (should not happen with proper usage).
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
     * Returns the number of fragments managed by the adapter.
     *
     * @return The number of pages (fragments) in this adapter, which is fixed to 2.
     */
    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
