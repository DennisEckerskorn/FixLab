package com.app.fixlab.ui.fragments.devicefragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.listeners.OnSaveAddClient;
import com.app.fixlab.listeners.OnSaveAddDevice;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Client;
import com.google.android.material.textfield.TextInputLayout;

public class DevicesFormAddFragment extends Fragment {
    private OnSaveAddDevice saveAddDevice;
    private TextInputLayout tilDeviceModel;
    private TextInputLayout tilDeviceBrand;
    private TextInputLayout tilDeviceSerialNumber;
    private TextInputLayout tilDeviceDescription;
    private Button btnSaveDevice;

    public DevicesFormAddFragment() {
        super(R.layout.activity_add_device);
    }
/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilDeviceModel = view.findViewById(R.id.tilDeviceModel);
        tilDeviceBrand = view.findViewById(R.id.tilDeviceBrand);
        tilDeviceSerialNumber = view.findViewById(R.id.tilDeviceSerialNumber);
        tilDeviceDescription = view.findViewById(R.id.tilDeviceDescription);
        btnSaveDevice = view.findViewById(R.id.btnSaveDevice);

        btnSaveDevice.setOnClickListener(v -> {
            if (saveAddDevice != null) {
                // Get data from TextInputEditText views
                String name = tilDeviceModel.getEditText().getText().toString(); // Access EditText within TextInputLayout
                String email = tilDeviceBrand.getEditText().getText().toString();
                String telefono = tilDeviceSerialNumber.getEditText().getText().toString();
                String direccion = tilDeviceDescription.getEditText().getText().toString();

                //TODO: ADJUST DATA; LAYOUT NEEDS TO BE ADAPTED:


                saveAddDevice.onSaveAddDevice(new Device(null,null,null,null,null,null));
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
       saveAddDevice = (OnSaveAddDevice) requireActivity();
    }

 */
}
