package com.app.fixlab.adapters.repairadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.listeners.OnCheckedChangeListener;
import com.app.fixlab.models.repair.Diagnosis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagnosisCheckListAdapter extends RecyclerView.Adapter<DiagnosisCheckListAdapter.DiagnosisCheckListViewHolder> {
    private final List<Diagnosis.DiagnosisCheckItem> checkItems;
    private final Map<Diagnosis.DiagnosisCheckItem, Boolean> checkedItems;
    private OnCheckedChangeListener onCheckedChangeListener;

    public DiagnosisCheckListAdapter() {
        this.checkItems = new ArrayList<>(Arrays.asList(Diagnosis.DiagnosisCheckItem.values()));
        this.checkedItems = new HashMap<>();

        for (Diagnosis.DiagnosisCheckItem item : checkItems) {
            checkedItems.put(item, false);
        }
    }

    public void setOnCheckChangedListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.onCheckedChangeListener = onCheckedChangeListener;
    }

    @NonNull
    @Override
    public DiagnosisCheckListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diagnosis_checklist, parent, false);
        return new DiagnosisCheckListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiagnosisCheckListViewHolder holder, int position) {
        Diagnosis.DiagnosisCheckItem item = checkItems.get(position);
        holder.bindCheckItem(item);
    }

    @Override
    public int getItemCount() {
        return checkedItems.size();
    }

    class DiagnosisCheckListViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;

        public DiagnosisCheckListViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBoxDiagnosis);
        }

        private void bindCheckItem(Diagnosis.DiagnosisCheckItem item) {
            String displayText = item.toString().replace("_", " ").toLowerCase();
            displayText = displayText.substring(0, 1).toUpperCase() + displayText.substring(1);
            checkBox.setText(displayText);
            checkBox.setChecked(Boolean.TRUE.equals(checkedItems.get(item)));

            checkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                checkedItems.put(item, isChecked);
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChange(item, isChecked);
                }
            });

        }

    }
}
