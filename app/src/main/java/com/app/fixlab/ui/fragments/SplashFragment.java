package com.app.fixlab.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.app.fixlab.R;
import com.app.fixlab.listeners.FragmentNavigationListener;
import com.app.fixlab.listeners.OnSplashDelayFinished;

public class SplashFragment extends Fragment {
    private static final long SPLASH_SCREEN_DELAY = 3000;

    public SplashFragment() {
        super(R.layout.activity_splash);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof OnSplashDelayFinished listener) {
            listener.onSPlashDelayFinished();
        }


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
