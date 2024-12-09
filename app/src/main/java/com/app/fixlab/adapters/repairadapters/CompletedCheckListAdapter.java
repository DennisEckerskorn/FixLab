package com.app.fixlab.adapters.repairadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.models.repair.Diagnosis;

import java.util.List;

public class CompletedCheckListAdapter extends RecyclerView.Adapter<CompletedCheckListAdapter.ViewHolder> {
    private final List<Diagnosis.DiagnosisCheckItem> completedItems;

    public CompletedCheckListAdapter(List<Diagnosis.DiagnosisCheckItem> completedItems) {
        this.completedItems = completedItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diagnosis_checklist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Diagnosis.DiagnosisCheckItem item = completedItems.get(position);
        holder.tvCheckBoxDiagnosis.setText(item.name().replace("_", " "));
        holder.tvDiagnosisItem.setText(item.name());

        holder.tvCheckBoxDiagnosis.setChecked(true);
        holder.tvCheckBoxDiagnosis.setEnabled(false);
    }

    @Override
    public int getItemCount() {
        return completedItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox tvCheckBoxDiagnosis;
        TextView tvDiagnosisItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDiagnosisItem = itemView.findViewById(R.id.tvDiagnosisItem);
            tvCheckBoxDiagnosis = itemView.findViewById(R.id.checkBoxDiagnosis);
        }
    }
}