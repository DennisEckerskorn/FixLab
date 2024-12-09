package com.app.fixlab.adapters;

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

public class CompletedRepairAdapter extends RecyclerView.Adapter<CompletedRepairAdapter.ViewHolder> {
    private final List<Repair> completedRepairs;
    private IonItemClickListenerGeneric<Repair> repairListener;

    public CompletedRepairAdapter(List<Repair> completedRepairs) {
        this.completedRepairs = completedRepairs;
    }

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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvSummPersonCompleted;
        private final TextView tvSummDeviceCompleted;
        private final TextView tvSummTechnicianCompleted;

        private Repair repair;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSummPersonCompleted = itemView.findViewById(R.id.tvSummPersonCompleted);
            tvSummDeviceCompleted = itemView.findViewById(R.id.tvSummDeviceCompleted);
            tvSummTechnicianCompleted = itemView.findViewById(R.id.tvSummTechnicianCompleted);
            itemView.setOnClickListener(this);
        }

        private void bindRepair(Repair repair) {
            this.repair = repair;
            tvSummPersonCompleted.setText(repair.getClient().getName());
            tvSummDeviceCompleted.setText(repair.getDevice().getModel());
            tvSummTechnicianCompleted.setText(repair.getTechnician().getName());
        }

        @Override
        public void onClick(View view) {
            if (repairListener != null) {
                repairListener.onCompletedSummaryRepair(repair);
            }
        }
    }
}
