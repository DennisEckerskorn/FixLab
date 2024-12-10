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

public class DevicesFormAddFragment extends Fragment {
    private OnSaveAddDevice saveAddDevice;
    private TextInputEditText tilDeviceModel;
    private TextInputEditText tilDeviceBrand;
    private TextInputEditText tilDeviceSerialNumber;
    private TextInputEditText tilDeviceDescription;
    private AutoCompleteTextView spinneType;
    private AutoCompleteTextView spinnerCondition;
    private Device device;
    private Button btnSaveDevice;

    public DevicesFormAddFragment() {
        super(R.layout.activity_add_device);
    }

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

                Device newDevice = new Device(model,serialNumber,desc,brand,deviceType,deviceCondition);

                saveAddDevice.onSaveAddDevice(newDevice);
            }
        });
    }

    private String getTextFromInput(TextInputEditText textInputEditText) {
        return textInputEditText.getText() != null ?
                textInputEditText.getText().toString().trim() : "";
    }

    private void setUpTypeSpinner() {
        // Convertir los valores del enum DeviceType en una lista de nombres
        String[] deviceTypeNames = new String[DeviceType.values().length];
        for (int i = 0; i < DeviceType.values().length; i++) {
            deviceTypeNames[i] = DeviceType.values()[i].name();
        }

        // Crear el adaptador con los nombres
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                deviceTypeNames
        );

        // Configurar el adaptador al Spinner
        spinneType.setAdapter(adapter);
    }


    private void setUpConditionSpinner() {
        String [] deviceConditionNames = new String[DeviceCondition.values().length];
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
       saveAddDevice = (OnSaveAddDevice) requireActivity();
    }


}
