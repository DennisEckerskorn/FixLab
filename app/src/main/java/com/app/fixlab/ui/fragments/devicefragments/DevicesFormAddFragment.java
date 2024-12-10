package com.app.fixlab.ui.fragments.devicefragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.listeners.OnSaveAddDevice;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.devices.DeviceCondition;
import com.app.fixlab.models.devices.DeviceStatus;
import com.app.fixlab.models.devices.DeviceType;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * DevicesFormAddFragment allows the user to add a new device by filling out a form.
 *
 * <p>This fragment provides input fields for device details and validates the input before saving.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.activity_add_device}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Provides input fields for device details such as model, brand, and type.</li>
 *   <li>Validates user input to ensure all fields are correctly filled.</li>
 *   <li>Invokes a callback to save the device when the form is valid.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link OnSaveAddDevice} interface for saving the new device.</li>
 * </ul>
 * </p>
 *
 * @author Jon and Borja
 */
public class DevicesFormAddFragment extends Fragment {
    private OnSaveAddDevice saveAddDevice;
    private TextInputEditText tilDeviceModel;
    private TextInputEditText tilDeviceBrand;
    private TextInputEditText tilDeviceSerialNumber;
    private TextInputEditText tilDeviceDescription;
    private AutoCompleteTextView spinneType;
    private AutoCompleteTextView spinnerCondition;
    private Button btnSaveDevice;

    /**
     * Default constructor for DevicesFormAddFragment.
     * Sets the layout for the fragment to {@code R.layout.activity_add_device}.
     */
    public DevicesFormAddFragment() {
        super(R.layout.activity_add_device);
    }

    /**
     * Called when the view hierarchy of the fragment is created.
     * Initializes the views and sets up the form logic.
     *
     * @param view The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tilDeviceModel = view.findViewById(R.id.tilDeviceModel);
        tilDeviceBrand = view.findViewById(R.id.tilDeviceBrand);
        tilDeviceSerialNumber = view.findViewById(R.id.tilDeviceSerialNumber);
        tilDeviceDescription = view.findViewById(R.id.tilDeviceDescription);
        spinnerCondition = view.findViewById(R.id.spinnerDeviceCondition);
        spinneType = view.findViewById(R.id.spinnerDeviceType);
        btnSaveDevice = view.findViewById(R.id.btnSaveDevice);

        setUpConditionSpinner();
        setUpTypeSpinner();

        btnSaveDevice.setOnClickListener(v -> {
            if (saveAddDevice != null) {
                String model = getTextFromInput(tilDeviceModel);
                String brand = getTextFromInput(tilDeviceBrand);
                String serialNumber = getTextFromInput(tilDeviceSerialNumber);
                String desc = getTextFromInput(tilDeviceDescription);
                String type = spinneType.getText().toString();
                String condition = spinnerCondition.getText().toString();
                DeviceType deviceType = DeviceType.valueOf(type);
                DeviceCondition deviceCondition = DeviceCondition.valueOf(condition);

                Device newDevice = new Device(model, serialNumber, desc, brand, deviceType, deviceCondition);

                saveAddDevice.onSaveAddDevice(newDevice);
            }
        });
    }

    /**
     * Retrieves the trimmed text from a {@link TextInputEditText}.
     *
     * @param textInputEditText The input field to retrieve text from.
     * @return The trimmed text or an empty string if the input is null.
     */
    private String getTextFromInput(TextInputEditText textInputEditText) {
        return textInputEditText.getText() != null ?
                textInputEditText.getText().toString().trim() : "";
    }

    /**
     * Sets up the type dropdown menu with options from the {@link DeviceType} enum.
     */
    private void setUpTypeSpinner() {
        String[] deviceTypeNames = new String[DeviceType.values().length];
        for (int i = 0; i < DeviceType.values().length; i++) {
            deviceTypeNames[i] = DeviceType.values()[i].name();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                deviceTypeNames
        );

        spinneType.setAdapter(adapter);
    }

    /**
     * Sets up the condition dropdown menu with options from the {@link DeviceCondition} enum.
     */
    private void setUpConditionSpinner() {
        String[] deviceConditionNames = new String[DeviceCondition.values().length];
        for (int i = 0; i < DeviceCondition.values().length; i++) {
            deviceConditionNames[i] = DeviceCondition.values()[i].name();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                deviceConditionNames
        );
        spinnerCondition.setAdapter(adapter);
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Retrieves the {@link OnSaveAddDevice} listener from the activity context.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        saveAddDevice = (OnSaveAddDevice) requireActivity();
    }
}