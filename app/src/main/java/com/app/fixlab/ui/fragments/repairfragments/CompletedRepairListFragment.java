package com.app.fixlab.ui.fragments.repairfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.adapters.repairadapters.CompletedRepairAdapter;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.models.repair.Repair;

import java.util.List;

/**
 * CompletedRepairListFragment displays a list of completed repairs.
 *
 * <p>This fragment provides a RecyclerView that shows a list of completed repair items.
 * It uses a custom adapter, {@link CompletedRepairAdapter}, to bind repair data to the UI components.
 * It communicates with its parent activity or fragment via {@link IonItemClickListenerGeneric} for
 * item click handling and {@link IdataProvider} for retrieving the completed repair data.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.fragment_list}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Displays a vertically scrolling list of completed repairs.</li>
 *   <li>Integrates with {@link IonItemClickListenerGeneric} to handle user interactions.</li>
 *   <li>Fetches data through {@link IdataProvider}, ensuring modular data management.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link IdataProvider} interface for accessing completed repair data.</li>
 *   <li>{@link IonItemClickListenerGeneric} interface for handling item clicks.</li>
 * </ul>
 * </p>
 *
 * @author [Dennis Eckerskorn]
 */
public class CompletedRepairListFragment extends Fragment {
    private List<Repair> completedRepairs;
    private IonItemClickListenerGeneric<Repair> itemClickListener;

    /**
     * Default constructor. Initializes the fragment with its layout resource.
     */
    public CompletedRepairListFragment() {
        super(R.layout.fragment_list);
    }

    /**
     * Called when the view hierarchy for this fragment is created.
     * Sets up the RecyclerView and its adapter.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = view.findViewById(R.id.rvList);
        CompletedRepairAdapter repairAdapter = new CompletedRepairAdapter(completedRepairs);
        repairAdapter.setListener(itemClickListener);
        rvList.setAdapter(repairAdapter);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Ensures the host activity implements required interfaces and retrieves data.
     *
     * @param context The context of the host activity.
     * @throws ClassCastException if the host activity does not implement required interfaces.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        itemClickListener = (IonItemClickListenerGeneric<Repair>) requireActivity();
        completedRepairs = dataProvider.getCompletedRepairs();
    }
}
