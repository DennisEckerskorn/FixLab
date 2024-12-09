package com.app.fixlab.ui.fragments.clientfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.adapters.clientadapters.ClientsAdapter;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.models.persons.Person;

import java.util.List;

/**
 * ClientListFragment displays a list of clients in a RecyclerView.
 *
 * <p>This fragment is part of the client management system and shows all clients retrieved
 * from a data provider. Each client is displayed in an item within the RecyclerView, and clicking
 * on an item triggers a listener for further actions.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.fragment_list}.</p>
 *
 * <p>Key features:
 * <ul>
 *   <li>Displays a list of {@link Person} objects representing clients.</li>
 *   <li>Uses {@link ClientsAdapter} to bind data to the RecyclerView.</li>
 *   <li>Integrates with {@link IonItemClickListenerGeneric} to handle item clicks.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link RecyclerView} for displaying the list of clients.</li>
 *   <li>{@link LinearLayoutManager} for vertical list layout management.</li>
 *   <li>{@link IdataProvider} for fetching client data.</li>
 * </ul>
 * </p>
 *
 * @author [Dennis Eckerskorn]
 */
public class ClientListFragment extends Fragment {
    private List<Person> clients;
    private IonItemClickListenerGeneric<Person> itemClickListener;

    /**
     * Default constructor. Initializes the fragment with its layout resource.
     */
    public ClientListFragment() {
        super(R.layout.fragment_list);
    }

    /**
     * Called when the view hierarchy for this fragment is created.
     * Configures the {@link RecyclerView} to display the client list using {@link ClientsAdapter}.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = view.findViewById(R.id.rvList);

        // Set the adapter for the recycler view
        ClientsAdapter clientsAdapter = new ClientsAdapter(clients);
        clientsAdapter.setListener(itemClickListener);
        rvList.setAdapter(clientsAdapter);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Initializes the client data and item click listener by interacting with the hosting activity.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Get the listener for the client list fragment
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        itemClickListener = (IonItemClickListenerGeneric<Person>) requireActivity();
        clients = dataProvider.getClientData();
    }
}
