package com.app.fixlab.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.listeners.OnClickListenerDevices;
import com.app.fixlab.models.devices.Device;

import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder> {
    private List<Device> devices;
    private OnClickListenerDevices deviceListener;


    public DevicesAdapter(List<Device>devices) {
        this.devices = devices;
    }

    public void setListener(OnClickListenerDevices deviceListener) {
        this.deviceListener = deviceListener;
    }

    @NonNull
    @Override
    public DevicesAdapter.DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
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
        private final TextView tvClientName;
        private final TextView tvClientSurname;
        private Device device;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClientName = itemView.findViewById(R.id.tvClientName);
            tvClientSurname = itemView.findViewById(R.id.tvClientSurname);
            itemView.setOnClickListener(this);
        }

        private void bindDevice(Device device) {
            this.device = device;
//            tvClientName.setText(device.getName());
//            tvClientSurname.setText(device.getSurname());
        }

        @Override
        public void onClick(View view) {
            if (deviceListener != null) {
                deviceListener.onClick(device);
            }
        }
    }
}
