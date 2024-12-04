package com.app.fixlab.ui.fragments.repairfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.adapters.DiagnosisCheckListAdapter;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.models.repair.Diagnosis;
import com.app.fixlab.models.repair.Repair;

public class DiagnosisFragment extends Fragment {
    private Repair currentRepair;

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

        // Set the adapter for the recycler view
        DiagnosisCheckListAdapter adapter = new DiagnosisCheckListAdapter();
        adapter.setOnCheckChangedListener((item, isChecked) -> {
            //TODO: ADAPT
            if (isChecked) {
                edDiagDescription.setEnabled(true);
                edEstimatedCost.setEnabled(true);
                edEstimatedTime.setEnabled(true);
            }
        });
        rvCheckList.setAdapter(adapter);
        rvCheckList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvCheckList.setHasFixedSize(true);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        currentRepair = dataProvider.getRepair();
        if (currentRepair != null && currentRepair.getDiagnosis() == null) {
            currentRepair.setDiagnosis(new Diagnosis());
        }

    }

}
