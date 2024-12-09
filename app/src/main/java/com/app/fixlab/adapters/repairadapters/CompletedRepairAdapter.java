package com.app.fixlab.adapters.repairadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.models.repair.Repair;

import java.util.List;

/**
 * Adapter class for displaying a list of completed repairs in a {@link RecyclerView}.
 * This adapter binds the list of completed repairs to each item view, displaying key information
 * such as the client's name, device model, and technician's name.
 * The adapter also handles item click events, which can be used to display more detailed repair information.
 * <p>
 * The items in the list represent completed repairs, and clicking on an item triggers the appropriate listener
 * to handle the repair's summary details.
 * </p>
 *
 * @see Repair
 */
public class CompletedRepairAdapter extends RecyclerView.Adapter<CompletedRepairAdapter.ViewHolder> {
    private final List<Repair> completedRepairs;
    private IonItemClickListenerGeneric<Repair> repairListener;

    /**
     * Constructor for the adapter.
     *
     * @param completedRepairs List of completed {@link Repair} objects to be displayed.
     */
    public CompletedRepairAdapter(List<Repair> completedRepairs) {
        this.completedRepairs = completedRepairs;
    }

    /**
     * Sets the listener for handling item click events.
     *
     * @param repairListener The listener that will handle repair item clicks.
     */
    public void setListener(IonItemClickListenerGeneric<Repair> repairListener) {
        this.repairListener = repairListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repair_summary_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindRepair(completedRepairs.get(position));
    }

    @Override
    public int getItemCount() {
        return completedRepairs.size();
    }

    /**
     * ViewHolder class for each item in the completed repair list.
     * It holds the views for displaying the repair summary: the client's name, device model, and technician's name.
     */
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvSummPersonCompleted;
        private final TextView tvSummDeviceCompleted;
        private final TextView tvSummTechnicianCompleted;

        private Repair repair;

        /**
         * Constructor for the {@link ViewHolder}.
         * Initializes the views for the client name, device model, and technician name.
         *
         * @param itemView The item view for this repair summary.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSummPersonCompleted = itemView.findViewById(R.id.tvSummPersonCompleted);
            tvSummDeviceCompleted = itemView.findViewById(R.id.tvSummDeviceCompleted);
            tvSummTechnicianCompleted = itemView.findViewById(R.id.tvSummTechnicianCompleted);
            itemView.setOnClickListener(this);
        }

        /**
         * Binds the completed repair data to the views in the item view.
         *
         * @param repair The {@link Repair} object to be displayed in the item.
         */
        private void bindRepair(Repair repair) {
            this.repair = repair;
            tvSummPersonCompleted.setText(repair.getClient().getName());
            tvSummDeviceCompleted.setText(repair.getDevice().getModel());
            tvSummTechnicianCompleted.setText(repair.getTechnician().getName());
        }


        /**
         * Handles the item click event. When the item is clicked, the {@link IonItemClickListenerGeneric}
         * listener's `onCompletedSummaryRepair` method is called to handle the repair's summary.
         *
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
            if (repairListener != null) {
                repairListener.onCompletedSummaryRepair(repair);
            }
        }
    }
}
