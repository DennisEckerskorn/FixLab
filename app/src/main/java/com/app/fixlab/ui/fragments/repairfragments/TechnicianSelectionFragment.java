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
import com.app.fixlab.adapters.technicianadapters.TechnicianAdapter;
import com.app.fixlab.listeners.IOnItemRepairClickListener;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.models.persons.Person;

import java.util.List;

/**
 * TechnicianSelectionFragment displays a list of technicians for the user to select.
 *
 * <p>This fragment allows the user to choose a technician from a list by interacting with the
 * displayed list of technicians.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.fragment_list}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Displays a list of technicians using a RecyclerView.</li>
 *   <li>Handles technician selection via an item click listener.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link IdataProvider} interface for retrieving the list of technicians.</li>
 *   <li>{@link IOnItemRepairClickListener} interface for handling technician selection events.</li>
 * </ul>
 * </p>
 *
 * @author [Dennis Eckerskorn]
 */
public class TechnicianSelectionFragment extends Fragment {
    private List<Person> technicians;
    private IOnItemRepairClickListener itemClickListener;

    /**
     * Default constructor. Initializes the fragment with its layout resource.
     */
    public TechnicianSelectionFragment() {
        super(R.layout.fragment_list);
    }

    /**
     * Called when the fragment's view hierarchy is created.
     * Sets up the UI elements with the list of technicians and sets up the RecyclerView.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = view.findViewById(R.id.rvList);

        // Set up the TechnicianAdapter with the list of technicians
        TechnicianAdapter technicianAdapter = new TechnicianAdapter(technicians, true);
        technicianAdapter.setRepairListener(itemClickListener);
        rvList.setAdapter(technicianAdapter);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Initializes the item click listener and retrieves the list of technicians from the data provider.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Retrieve the data provider and item click listener from the host activity
        IdataProvider idataProvider = (IdataProvider) requireActivity();
        itemClickListener = (IOnItemRepairClickListener) requireActivity();
        technicians = idataProvider.getTechnicianData();
    }
}
