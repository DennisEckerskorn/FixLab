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

/**
 * Adapter class for displaying a checklist of diagnosis items in a {@link RecyclerView}.
 * Each diagnosis item is represented by a {@link CheckBox} that the user can check or uncheck.
 * This adapter manages the state of the checkboxes and notifies the listener when an item is checked or unchecked.
 * <p>
 * The adapter binds the diagnosis check items from the {@link Diagnosis.DiagnosisCheckItem} enum
 * to the checkboxes in the list. It allows the user to interact with the items and update their state.
 * </p>
 *
 * @see Diagnosis.DiagnosisCheckItem
 * @see OnCheckedChangeListener
 */
public class DiagnosisCheckListAdapter extends RecyclerView.Adapter<DiagnosisCheckListAdapter.DiagnosisCheckListViewHolder> {
    private final List<Diagnosis.DiagnosisCheckItem> checkItems;
    private final Map<Diagnosis.DiagnosisCheckItem, Boolean> checkedItems;
    private OnCheckedChangeListener onCheckedChangeListener;

    /**
     * Constructor for the adapter.
     * Initializes the list of diagnosis check items and the map for their checked state.
     */
    public DiagnosisCheckListAdapter() {
        this.checkItems = new ArrayList<>(Arrays.asList(Diagnosis.DiagnosisCheckItem.values()));
        this.checkedItems = new HashMap<>();

        // Set all items as unchecked initially
        for (Diagnosis.DiagnosisCheckItem item : checkItems) {
            checkedItems.put(item, false);
        }
    }

    /**
     * Sets the listener for handling checkbox state change events.
     *
     * @param onCheckedChangeListener The listener to notify when a checkbox is checked or unchecked.
     */
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

    /**
     * ViewHolder class for each checklist item in the diagnosis check list.
     * It contains a {@link CheckBox} for each diagnosis check item.
     */
    class DiagnosisCheckListViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;

        /**
         * Constructor for the {@link DiagnosisCheckListViewHolder}.
         * Initializes the {@link CheckBox} for each diagnosis check item.
         *
         * @param itemView The item view for this checklist item.
         */
        public DiagnosisCheckListViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBoxDiagnosis);
        }

        /**
         * Binds a diagnosis check item to the {@link CheckBox}.
         * Sets the checkbox text and the checked state, and also sets a listener for state changes.
         *
         * @param item The diagnosis check item to be bound to the checkbox.
         */
        private void bindCheckItem(Diagnosis.DiagnosisCheckItem item) {
            String displayText = item.toString().replace("_", " ").toLowerCase();
            displayText = displayText.substring(0, 1).toUpperCase() + displayText.substring(1);
            checkBox.setText(displayText);

            checkBox.setOnCheckedChangeListener(null);
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
