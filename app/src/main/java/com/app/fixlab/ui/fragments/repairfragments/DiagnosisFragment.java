package com.app.fixlab.ui.fragments.repairfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.adapters.repairadapters.DiagnosisCheckListAdapter;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.OnCheckedChangeListener;
import com.app.fixlab.listeners.OnSaveDiagnosis;
import com.app.fixlab.models.repair.Diagnosis;
import com.app.fixlab.models.repair.Repair;

public class DiagnosisFragment extends Fragment {
    private Repair currentRepair;
    private OnCheckedChangeListener checkedChangeListener;
    private OnSaveDiagnosis saveDiagnosisListener;

    public DiagnosisFragment() {
        super(R.layout.fragment_diagnosis);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvCheckList = view.findViewById(R.id.rvCheckList);
        EditText edDiagDescription = view.findViewById(R.id.edDiagDescription);
        EditText edEstimatedCost = view.findViewById(R.id.edEstimatedCost);
        EditText edEstimatedTime = view.findViewById(R.id.edEstimatedTime);
        Button btnSaveDiagnosis = view.findViewById(R.id.btnSaveDiagnosis);

        // Set the adapter for the recycler view
        DiagnosisCheckListAdapter adapter = new DiagnosisCheckListAdapter();
        adapter.setOnCheckChangedListener((item, isChecked) -> {
            if (checkedChangeListener != null) {
                checkedChangeListener.onCheckedChange(item, isChecked);
            }

            if (isChecked) {
                edDiagDescription.setEnabled(true);
                edEstimatedCost.setEnabled(true);
                edEstimatedTime.setEnabled(true);
                btnSaveDiagnosis.setEnabled(true);
            }
        });
        rvCheckList.setAdapter(adapter);
        rvCheckList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvCheckList.setHasFixedSize(true);

        btnSaveDiagnosis.setOnClickListener(v -> {

            String description = edDiagDescription.getText().toString();
            String estimatedCost = edEstimatedCost.getText().toString();
            String estimatedTime = edEstimatedTime.getText().toString();

            if (validateInputs(description, estimatedCost, estimatedTime)) {
                currentRepair.getDiagnosis().setDescription(description);
                currentRepair.getDiagnosis().setEstimatedCost(estimatedCost);
                currentRepair.getDiagnosis().setEstimatedTime(estimatedTime);

                if (saveDiagnosisListener != null) {
                    saveDiagnosisListener.onSaveDiagnosis(currentRepair.getDiagnosis());
                }
            }
        });

    }

    private boolean validateInputs(String description, String estimatedCost, String
            estimatedTime) {
        if (description.isEmpty() || estimatedCost.isEmpty() || estimatedTime.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        checkedChangeListener = (OnCheckedChangeListener) requireActivity();
        saveDiagnosisListener = (OnSaveDiagnosis) requireActivity();
        currentRepair = dataProvider.getRepair();

        if (currentRepair != null && currentRepair.getDiagnosis() == null) {
            currentRepair.setDiagnosis(new Diagnosis());
        }

    }

}
