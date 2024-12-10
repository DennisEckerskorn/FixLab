package com.app.fixlab.ui.fragments.technicianfragments;

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
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.models.persons.Person;

import java.util.List;

/**
 * TechnicianListFragment displays a list of technicians using a RecyclerView.
 *
 * <p>This fragment allows users to view and interact with a list of technicians.
 * The list is populated using the {@link TechnicianAdapter}, and user interactions are
 * handled through a generic item click listener.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.fragment_list}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Displays a list of technicians.</li>
 *   <li>Uses a {@link RecyclerView} with a vertical linear layout.</li>
 *   <li>Supports user interactions through an item click listener.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link IdataProvider} interface for retrieving technician data.</li>
 *   <li>{@link IonItemClickListenerGeneric} interface for handling item click events.</li>
 * </ul>
 * </p>
 *
 * @author Borja Bolufer
 */
public class TechnicianListFragment extends Fragment {
    private List<Person> technicians;
    private IonItemClickListenerGeneric<Person> itemClickListener;

    /**
     * Default constructor for TechnicianListFragment.
     * Sets the layout for the fragment to {@code R.layout.fragment_list}.
     */
    public TechnicianListFragment() {
        super(R.layout.fragment_list);
    }

    /**
     * Called when the view hierarchy of the fragment is created.
     * Initializes the RecyclerView and its adapter.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = view.findViewById(R.id.rvList);

        // Set the adapter for the recycler view
        TechnicianAdapter technicianAdapter = new TechnicianAdapter(technicians, false);
        technicianAdapter.setListenerTechnicians(itemClickListener);
        rvList.setAdapter(technicianAdapter);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Retrieves technician data and the item click listener from the activity context.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        itemClickListener = (IonItemClickListenerGeneric<Person>) requireActivity();
        technicians = dataProvider.getTechnicianData();
    }
}