package com.app.fixlab.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.listeners.OnDeviceClickListener;
import com.app.fixlab.models.devices.Device;

import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder> {
    private final List<Device> devices;
    private OnDeviceClickListener deviceListener;


    public DevicesAdapter(List<Device>devices) {
        this.devices = devices;
    }

    public void setListener(OnDeviceClickListener deviceListener) {
        this.deviceListener = deviceListener;
    }

    @NonNull
    @Override
    public DevicesAdapter.DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new DevicesAdapter.DeviceViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull DevicesAdapter.DeviceViewHolder holder, int position) {
        holder.bindDevice(devices.get(position));
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }


    class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //private final ImageView ivDeviceType;
        private final TextView tvDeviceModel;
        private final TextView tvDeviceBrand;
        //private final TextView tvDeviceStatus;

        private Device device;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeviceModel = itemView.findViewById(R.id.tvModel);
            tvDeviceBrand = itemView.findViewById(R.id.tvBrand);
           // tvDeviceStatus = itemView.findViewById(R.id.tvStatus);
            //ivDeviceType = itemView.findViewById(R.id.ivDeviceType);
            itemView.setOnClickListener(this);
        }

        private void bindDevice(Device device) {
            this.device = device;
            tvDeviceModel.setText(device.getModel());
            tvDeviceBrand.setText(device.getBrand());
           // tvDeviceStatus.setText(device.getStatusString());
            //ivDeviceType.setImageResource(device.getTypeString());
            }

        @Override
        public void onClick(View view) {
            if (deviceListener != null) {
                deviceListener.onDeviceClick(device);
            }
        }
    }
}
