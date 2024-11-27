package com.app.fixlab.ui.fragments.devicefragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.fixlab.R;
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.ui.fragments.clientfragments.ClientDetailFragment;

public class DeviceDetailFragment extends Fragment {
    public interface IDeviceDetailFragmentListener {
        Device getDevice();
    }

    private Device selectedDevice;
    private TextView tvDeviceModel;
    private TextView tvDeviceBrand;
    private TextView tvDeviceSerialNumber;
    private TextView tvDeviceCondition;
    private TextView tvDeviceStatus;
    private TextView tvDeviceDescription;

    //TODO: Implement device list
    //private TextView tvClientDevicesDetailValue;
    private IDeviceDetailFragmentListener listener;
    public DeviceDetailFragment() {
        super(R.layout.detail_client_item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDeviceModel = view.findViewById(R.id.tvDeviceModel);
        tvDeviceBrand = view.findViewById(R.id.tvDeviceBrand);
        tvDeviceSerialNumber = view.findViewById(R.id.tvDeviceSerialNumber);
        tvDeviceCondition = view.findViewById(R.id.tvDeviceCondition);
        tvDeviceStatus = view.findViewById(R.id.tvDeviceStatus);
        tvDeviceDescription = view.findViewById(R.id.tvDeviceDescription);

        tvDeviceModel.setText(selectedDevice.getModel());
        tvDeviceBrand.setText(selectedDevice.getBrand());
        tvDeviceSerialNumber.setText(selectedDevice.getSerialNumber());
        tvDeviceCondition.setText(selectedDevice.getConditionString());
        tvDeviceStatus.setText(selectedDevice.getStatusString());
        tvDeviceDescription.setText(selectedDevice.getDescription());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (IDeviceDetailFragmentListener) requireActivity();
        selectedDevice = listener.getDevice();
        Toast.makeText(context, "Device selected: " + selectedDevice.getModel(), Toast.LENGTH_SHORT).show();
    }
}
