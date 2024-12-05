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
import com.app.fixlab.models.devices.DeviceCondition;
import com.app.fixlab.models.devices.DeviceType;
import com.app.fixlab.ui.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

public class DeviceModifyFragment extends Fragment {
    private Device selectedDevice;
    private TextInputEditText etModel, etBrand, etSerialNumber, etDescription;
    private OnModifyListener modifyListener;

    public DeviceModifyFragment() {
        super(R.layout.fragment_modify_device);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setupInitialData();
    }

    private void initViews(View view) {
        Button btnSave = view.findViewById(R.id.btnSaveDeviceEdit);
        btnSave.setOnClickListener(v -> {
            if (validateFields()) {
                saveChanges();
            }
        });
        etModel = view.findViewById(R.id.etEditDeviceModel);
        etBrand = view.findViewById(R.id.etEditDeviceBrand);
        etSerialNumber = view.findViewById(R.id.etEditDeviceSerialNumber);
        etDescription = view.findViewById(R.id.etEditDeviceDescription);
    }

    private void setupInitialData() {
        if (selectedDevice != null) {
            etModel.setText(selectedDevice.getModel());
            etBrand.setText(selectedDevice.getBrand());
            etSerialNumber.setText(selectedDevice.getSerialNumber());
            etDescription.setText(selectedDevice.getDescription());
        }
    }

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

    private void saveChanges() {
        if (modifyListener != null && selectedDevice != null) {
            Device updatedDevice = new Device(
                    etModel.getText().toString(),
                    etSerialNumber.getText().toString(),
                    etDescription.getText().toString(),
                    etBrand.getText().toString(),
                    selectedDevice.getType(),
                    selectedDevice.getCondition()
            );
            modifyListener.onDeviceModify(updatedDevice);

            //Volvemos al fragment anterior
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }
    }

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