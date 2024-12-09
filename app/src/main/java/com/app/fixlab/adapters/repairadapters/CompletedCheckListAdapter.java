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

/**
 * Adapter class for displaying a checklist of completed diagnosis items in a {@link RecyclerView}.
 * This adapter binds the list of completed diagnosis check items to each item view in the list,
 * displaying each item's name along with a checked checkbox to indicate its completion status.
 * <p>
 * The checklist is typically displayed for a diagnosis process, where each item represents
 * a test or action performed during the diagnosis.
 * </p>
 *
 * @see Diagnosis.DiagnosisCheckItem
 */
public class CompletedCheckListAdapter extends RecyclerView.Adapter<CompletedCheckListAdapter.ViewHolder> {
    private final List<Diagnosis.DiagnosisCheckItem> completedItems;

    /**
     * Constructor for the adapter.
     *
     * @param completedItems List of {@link Diagnosis.DiagnosisCheckItem} objects that are completed.
     */
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

    /**
     * ViewHolder class for diagnosis checklist items in the RecyclerView.
     * It holds the views representing the checkbox and the name of the diagnosis item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox tvCheckBoxDiagnosis;
        TextView tvDiagnosisItem;


        /**
         * Constructor for the {@link ViewHolder}.
         * Initializes the views for the checkbox and diagnosis item name.
         *
         * @param itemView The item view for this diagnosis checklist item.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDiagnosisItem = itemView.findViewById(R.id.tvDiagnosisItem);
            tvCheckBoxDiagnosis = itemView.findViewById(R.id.checkBoxDiagnosis);
        }
    }
}