package com.app.fixlab.ui.fragments.clientfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.adapters.deviceadapters.DevicesAdapter;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.OnDeleteListener;
import com.app.fixlab.listeners.OnDeviceClickListener;
import com.app.fixlab.listeners.OnModifyListener;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Person;

import java.util.List;

/**
 * ClientDetailFragment displays detailed information about a selected client,
 * including their associated devices. Users can modify or delete the client
 * using the provided buttons. Devices are listed in a RecyclerView, allowing
 * interaction through item click events.
 *
 * <p>The layout for this fragment is defined in {@code R.layout.detail_client_item}.</p>
 *
 * <p>Required listeners for the host activity:
 * <ul>
 *   <li>{@link IdataProvider} - Provides data about the selected client and their devices.</li>
 *   <li>{@link OnDeviceClickListener} - Handles item click events for devices in the RecyclerView.</li>
 *   <li>{@link OnDeleteListener} - Handles delete button click events.</li>
 *   <li>{@link OnModifyListener} - Handles modify button click events.</li>
 * </ul>
 * </p>
 *
 * @author [Dennis, Borja]
 */
public class ClientDetailFragment extends Fragment {
    private Person selectedClient;
    private List<Device> devices;
    private OnDeviceClickListener itemClickListener;
    private OnDeleteListener buttonListener;
    private OnModifyListener modifyListener;

    /**
     * Default constructor. Initializes the fragment with its layout.
     */
    public ClientDetailFragment() {
        super(R.layout.detail_client_item);
    }

    /**
     * Called when the view hierarchy for this fragment is created.
     * Initializes views, populates data for the selected client, and sets up listeners
     * for the RecyclerView and buttons.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvNameDetailValue = view.findViewById(R.id.tvNameDetailValue);
        TextView tvPhoneDetailValue = view.findViewById(R.id.tvPhoneDetailValue);
        TextView tvEmailDetailValue = view.findViewById(R.id.tvEmailDetailValue);
        TextView tvClientAddressDetailValue = view.findViewById(R.id.tvClientAddressDetailValue);
        RecyclerView rvClientDevices = view.findViewById(R.id.rvClientDevices);


        DevicesAdapter devicesAdapter = new DevicesAdapter(devices);
        devicesAdapter.setListener(itemClickListener);
        rvClientDevices.setAdapter(devicesAdapter);
        rvClientDevices.setHasFixedSize(true);
        rvClientDevices.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        if (selectedClient != null) {
            tvNameDetailValue.setText(selectedClient.getName());
            tvPhoneDetailValue.setText(selectedClient.getPhoneNumber());
            tvEmailDetailValue.setText(selectedClient.getEmail());
            tvClientAddressDetailValue.setText(selectedClient.getAddress());
        }
        Button btnDelete = view.findViewById(R.id.btnDeleteOnClientDetail);
        Button btnModify = view.findViewById(R.id.btnModify);


        btnDelete.setOnClickListener(v -> {
            buttonListener.onDeleteClient();
        });

        btnModify.setOnClickListener(v -> {
            modifyListener.onModifyButtonClient();
        });
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Initializes required listeners and retrieves data for the selected client
     * and their associated devices.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        itemClickListener = (OnDeviceClickListener) requireActivity();
        buttonListener = (OnDeleteListener) requireActivity();
        modifyListener = (OnModifyListener) requireActivity();
        devices = dataProvider.getDeviceOfClient();
        selectedClient = dataProvider.getClient();
        Toast.makeText(context, "Client selected: " + selectedClient.getName(), Toast.LENGTH_SHORT).show();
    }
}