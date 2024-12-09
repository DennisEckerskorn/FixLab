package com.app.fixlab.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.listeners.OnSplashDelayFinished;

/**
 * SplashFragment displays a splash screen for a specified delay before
 * transitioning to the next screen. This fragment uses the
 * {@link OnSplashDelayFinished} interface to notify the host activity
 * when the delay is completed.
 *
 * <p>The splash screen layout is defined in {@code R.layout.activity_splash}.</p>
 *
 * @author [Dennis Eckerskorn]
 */
public class SplashFragment extends Fragment {

    /**
     * Default constructor for SplashFragment.
     * Sets the layout resource for this fragment to {@code R.layout.activity_splash}.
     */
    public SplashFragment() {
        super(R.layout.activity_splash);
    }

    /**
     * Called when the view hierarchy for this fragment is created.
     * Notifies the hosting activity, if it implements {@link OnSplashDelayFinished},
     * that the splash delay is finished, allowing it to transition to the next screen.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof OnSplashDelayFinished listener) {
            listener.onSPlashDelayFinished();
        }
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Ensures the fragment has access to the context of the hosting activity.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
