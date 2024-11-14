package com.app.fixlab.listeners;

import androidx.fragment.app.Fragment;

public interface FragmentNavigationListener {
    void navigateToFragment(Fragment fragment, boolean addToBackStack);
}
