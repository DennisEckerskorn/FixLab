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
import com.app.fixlab.adapters.ClientsAdapter;
import com.app.fixlab.listeners.IDataProvider;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.models.persons.Person;

import java.util.List;

/**
 * CLIENT LIST FRAGMENT: Fragment that shows a list of clients
 */
public class ClientListFragment extends Fragment {
    private List<Person> clients;
    private IonItemClickListenerGeneric<Person> itemClickListener;

    public ClientListFragment() {
        super(R.layout.fragment_list);
    }

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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Get the listener for the client list fragment
        IDataProvider<Person> dataProvider = (IDataProvider<Person>) requireActivity();
        itemClickListener = (IonItemClickListenerGeneric<Person>) requireActivity();
        clients = dataProvider.getData();
    }
}
