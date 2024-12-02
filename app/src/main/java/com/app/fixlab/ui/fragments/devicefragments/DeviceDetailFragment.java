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
import com.app.fixlab.models.devices.Device;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.ui.MainActivity;
import com.app.fixlab.ui.fragments.clientfragments.ClientDetailFragment;

public class DeviceDetailFragment extends Fragment {

    private Device selectedDevice;

    private TextView tvDeviceModel;
    private TextView tvDeviceBrand;
    private TextView tvDeviceSerialNumber;
    private TextView tvDeviceCondition;
    private TextView tvDeviceStatus;
    private TextView tvDeviceDescription;

    //TODO: Implement device list
    //private TextView tvClientDevicesDetailValue;
    public DeviceDetailFragment() {
        super(R.layout.detail_device_item);
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

        //TODO: ALGUN TEXTVIEW FALLA Y DA NULL POINTER EN ESTA PARTE:
        if (selectedDevice != null) {
            tvDeviceModel.setText(selectedDevice.getModel());
            tvDeviceBrand.setText(selectedDevice.getBrand());
            tvDeviceSerialNumber.setText(selectedDevice.getSerialNumber());
            tvDeviceCondition.setText(selectedDevice.getConditionString());
            tvDeviceStatus.setText(selectedDevice.getStatusString());
            tvDeviceDescription.setText(selectedDevice.getDescription());
        }
        //TODO: Implement device list



        //Implementacion del boton de modificar
        Button btnModify = view.findViewById(R.id.btnModifyDevice);
        btnModify.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity){
                ((MainActivity) getActivity()).navigateToFragment(new DeviceModifyFragment(), true);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        selectedDevice = dataProvider.getDevice();
        Toast.makeText(context, "Device selected: " + selectedDevice.getModel(), Toast.LENGTH_SHORT).show();
    }
}
