package com.app.fixlab.ui.fragments.devicefragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.OnDeleteListener;
import com.app.fixlab.listeners.OnModifyListener;
import com.app.fixlab.models.devices.Device;

/**
 * Fragment that displays the details of a selected device.
 * Allows for modification or deletion of the device.
 */
public class DeviceDetailFragment extends Fragment {
    private Device selectedDevice;
    private OnModifyListener onModifyListener;

    private TextView tvDeviceModel;
    private TextView tvDeviceBrand;
    private TextView tvDeviceSerialNumber;
    private TextView tvDeviceCondition;
    private TextView tvDeviceStatus;
    private TextView tvDeviceDescription;

    private OnDeleteListener buttonListener;

    /**
     * Default constructor that sets the layout resource for the fragment.
     */
    public DeviceDetailFragment() {
        super(R.layout.detail_device_item);
    }

    /**
     * Called when the fragment's view is created.
     * Initializes the views and sets up event listeners for the delete and modify buttons.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState Saved state of the fragment, if available.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize text views
        tvDeviceModel = view.findViewById(R.id.tvDeviceModel);
        tvDeviceBrand = view.findViewById(R.id.tvDeviceBrand);
        tvDeviceSerialNumber = view.findViewById(R.id.tvDeviceSerialNumber);
        tvDeviceCondition = view.findViewById(R.id.tvDeviceCondition);
        tvDeviceStatus = view.findViewById(R.id.tvDeviceStatus);
        tvDeviceDescription = view.findViewById(R.id.tvDeviceDescription);

        // Populate the views with the details of the selected device
        if (selectedDevice != null) {
            tvDeviceModel.setText(selectedDevice.getModel());
            tvDeviceBrand.setText(selectedDevice.getBrand());
            tvDeviceSerialNumber.setText(selectedDevice.getSerialNumber());
            tvDeviceCondition.setText(selectedDevice.getConditionString());
            tvDeviceStatus.setText(selectedDevice.getStatusString());
            tvDeviceDescription.setText(selectedDevice.getDescription());
        }

        // Set up the delete button listener
        Button btnDelete = view.findViewById(R.id.btnDeleteOnDeviceDetail);
        btnDelete.setOnClickListener(v -> buttonListener.onDeleteDevice());

        // Set up the modify button listener
        Button btnModify = view.findViewById(R.id.btnModifyDevice);
        btnModify.setOnClickListener(v -> onModifyListener.onModifyButtonDevice());
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Retrieves the required listeners and the selected device from the activity.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Get the required interfaces from the activity
            IdataProvider dataProvider = (IdataProvider) requireActivity();
            onModifyListener = (OnModifyListener) requireActivity();
            buttonListener = (OnDeleteListener) requireActivity();

            // Retrieve the selected device
            selectedDevice = dataProvider.getDevice();

            // Display a toast message with the selected device's model
            Toast.makeText(context, "Device selected: " + selectedDevice.getModel(), Toast.LENGTH_SHORT).show();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement required listeners.");
        }
    }
}
