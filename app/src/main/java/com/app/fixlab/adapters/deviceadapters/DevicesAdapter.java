package com.app.fixlab.adapters.deviceadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.listeners.IOnItemRepairClickListener;
import com.app.fixlab.listeners.OnDeviceClickListener;
import com.app.fixlab.models.devices.Device;

import java.util.List;

/**
 * Adapter for displaying a list of {@link Device} objects in a {@link RecyclerView}.
 *
 * <p>This adapter binds {@link Device} data to a custom item layout and provides click listeners
 * for interacting with device items.</p>
 */
public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder> {

    /**
     * List of devices to be displayed in the RecyclerView.
     */
    private final List<Device> devices;

    /**
     * Listener for handling clicks on device items.
     */
    private OnDeviceClickListener deviceListener;

    /**
     * Listener for handling repair action clicks on device items.
     */
    private IOnItemRepairClickListener repairDeviceClickListener;

    /**
     * Constructs a new {@link DevicesAdapter}.
     *
     * @param devices The list of {@link Device} objects to display.
     */
    public DevicesAdapter(List<Device> devices) {
        this.devices = devices;
    }

    /**
     * Sets the listener for handling device item clicks.
     *
     * @param deviceListener The {@link OnDeviceClickListener} to set.
     */
    public void setListener(OnDeviceClickListener deviceListener) {
        this.deviceListener = deviceListener;
    }

    /**
     * Sets the listener for handling repair action clicks on device items.
     *
     * @param repairDeviceClickListener The {@link IOnItemRepairClickListener} to set.
     */
    public void setRepairDeviceClickListener(IOnItemRepairClickListener repairDeviceClickListener) {
        this.repairDeviceClickListener = repairDeviceClickListener;
    }

    /**
     * Inflates the item layout and creates a new {@link DeviceViewHolder}.
     *
     * @param parent   The parent view group.
     * @param viewType The view type of the new view.
     * @return A new {@link DeviceViewHolder}.
     */
    @NonNull
    @Override
    public DevicesAdapter.DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        return new DevicesAdapter.DeviceViewHolder(layout);
    }

    /**
     * Binds a {@link Device} object to a {@link DeviceViewHolder}.
     *
     * @param holder   The holder to bind the data to.
     * @param position The position of the {@link Device} in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull DevicesAdapter.DeviceViewHolder holder, int position) {
        holder.bindDevice(devices.get(position));
    }

    /**
     * Returns the total number of items in the list.
     *
     * @return The number of devices.
     */
    @Override
    public int getItemCount() {
        return devices.size();
    }

    /**
     * ViewHolder class for managing individual device item views.
     */
    class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        /**
         * ImageView for displaying the device type.
         */
        private final ImageView ivDeviceType;

        /**
         * TextView for displaying the device model.
         */
        private final TextView tvDeviceModel;

        /**
         * TextView for displaying the device brand.
         */
        private final TextView tvDeviceBrand;

        /**
         * TextView for displaying the device status.
         */
        private final TextView tvDeviceStatus;

        /**
         * The {@link Device} instance bound to this ViewHolder.
         */
        private Device device;

        /**
         * Constructs a new {@link DeviceViewHolder}.
         *
         * @param itemView The view representing a single device item.
         */
        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeviceModel = itemView.findViewById(R.id.tvModel);
            tvDeviceBrand = itemView.findViewById(R.id.tvBrand);
            tvDeviceStatus = itemView.findViewById(R.id.tvStatus);
            ivDeviceType = itemView.findViewById(R.id.ivDeviceType);
            itemView.setOnClickListener(this);
        }

        /**
         * Binds a {@link Device} object to the item view.
         *
         * @param device The {@link Device} to bind.
         */
        private void bindDevice(Device device) {
            this.device = device;
            tvDeviceModel.setText(device.getModel());
            tvDeviceBrand.setText(device.getBrand());
            tvDeviceStatus.setText(device.getStatusString());
            ivDeviceType.setImageResource(device.getTypeString());
        }

        /**
         * Handles click events on the device item view.
         *
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
            if (deviceListener != null) {
                deviceListener.onDeviceClick(device);
            }

            if (repairDeviceClickListener != null) {
                repairDeviceClickListener.onRepairedDeviceClick(device);
            }
        }
    }
}
