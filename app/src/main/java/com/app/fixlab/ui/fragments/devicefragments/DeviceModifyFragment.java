package com.app.fixlab.ui.fragments.devicefragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.listeners.OnModifyListener;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.ui.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Fragment that allows modifying the details of a selected device.
 * The updated device details are passed back to the host activity via a listener.
 */
public class DeviceModifyFragment extends Fragment {

    private Device selectedDevice;
    private TextInputEditText etModel, etBrand, etSerialNumber, etDescription;
    private OnModifyListener modifyListener;

    /**
     * Default constructor that sets the layout resource for the fragment.
     */
    public DeviceModifyFragment() {
        super(R.layout.fragment_modify_device);
    }

    /**
     * Called when the fragment's view is created.
     * Initializes the view elements and sets up initial device data.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState Saved state of the fragment, if available.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupInitialData();
    }

    /**
     * Initializes the input fields and button listeners in the view.
     *
     * @param view The root view of the fragment.
     */
    private void initViews(View view) {
        Button btnSave = view.findViewById(R.id.btnSaveDeviceEdit);
        btnSave.setOnClickListener(v -> {
            if (validateFields()) {
                saveChanges();
            }
        });

        // Initialize input fields
        etModel = view.findViewById(R.id.etEditDeviceModel);
        etBrand = view.findViewById(R.id.etEditDeviceBrand);
        etSerialNumber = view.findViewById(R.id.etEditDeviceSerialNumber);
        etDescription = view.findViewById(R.id.etEditDeviceDescription);
    }

    /**
     * Populates the input fields with the data of the selected device.
     */
    private void setupInitialData() {
        if (selectedDevice != null) {
            etModel.setText(selectedDevice.getModel());
            etBrand.setText(selectedDevice.getBrand());
            etSerialNumber.setText(selectedDevice.getSerialNumber());
            etDescription.setText(selectedDevice.getDescription());
        }
    }

    /**
     * Validates that all required fields are filled out.
     *
     * @return True if all fields are valid; otherwise, false.
     */
    private boolean validateFields() {
        boolean isValid = true;

        if (etModel.getText().toString().isEmpty()) {
            etModel.setError("El modelo es obligatorio");
            isValid = false;
        }
        if (etBrand.getText().toString().isEmpty()) {
            etBrand.setError("La marca es obligatoria");
            isValid = false;
        }
        if (etSerialNumber.getText().toString().isEmpty()) {
            etSerialNumber.setError("El número de serie es obligatorio");
            isValid = false;
        }
        if (etDescription.getText().toString().isEmpty()) {
            etDescription.setError("La descripción es obligatoria");
            isValid = false;
        }

        return isValid;
    }

    /**
     * Saves the changes to the device and notifies the host activity via the listener.
     */
    private void saveChanges() {
        if (modifyListener != null && selectedDevice != null) {
            Device updatedDevice = new Device(
                    etModel.getText().toString(),
                    etSerialNumber.getText().toString(),
                    etDescription.getText().toString(),
                    etBrand.getText().toString(),
                    selectedDevice.getType(),  // Keep the original type
                    selectedDevice.getCondition()  // Keep the original condition
            );

            // Notify the listener of the updated device
            modifyListener.onDeviceModify(updatedDevice);
        }
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Retrieves the selected device and the modify listener from the activity.
     *
     * @param context The context of the host activity.
     * @throws ClassCastException If the activity does not implement the required listener.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            modifyListener = (OnModifyListener) requireActivity();
            selectedDevice = ((MainActivity) requireActivity()).getDevice();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDeviceModifyListener");
        }
    }
}
