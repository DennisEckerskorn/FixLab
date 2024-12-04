package com.app.fixlab.ui.fragments.repairfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.adapters.CompletedCheckListAdapter;
import com.app.fixlab.adapters.DiagnosisCheckListAdapter;
import com.app.fixlab.listeners.IOnItemRepairClickListener;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.models.repair.Diagnosis;
import com.app.fixlab.models.repair.Repair;

import java.util.List;

public class RepairSummaryFragment extends Fragment {
    private Repair currentRepair;
    private IOnItemRepairClickListener repairClickListener;


    public RepairSummaryFragment() {
        super(R.layout.fragment_repair_summary);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvSummaryTechnician = view.findViewById(R.id.tvSummaryTechnician);
        TextView tvSummaryClient = view.findViewById(R.id.tvSummaryClient);
        TextView tvSummaryDevice = view.findViewById(R.id.tvSummaryDevice);
        RecyclerView rvSummaryDiagnosis = view.findViewById(R.id.rvSummaryDiagnosis);
        TextView etSummaryRepairResult = view.findViewById(R.id.etSummaryRepairResult);
        TextView tvDiagnosisDescriptionSummary = view.findViewById(R.id.tvDiagnosisDescriptionSummary);
        TextView tvDiagnosisCostSummary = view.findViewById(R.id.tvDiagnosisCostSummary);
        TextView tvDiagnosisTimeSummary = view.findViewById(R.id.tvDiagnosisTimeSummary);
        Button btnSummaryCompleteRepair = view.findViewById(R.id.btnSummaryCompleteRepair);


        if (currentRepair != null) {
            if (currentRepair.getTechnician() != null) {
                tvSummaryTechnician.setText(getString(R.string.technician) + ": " + currentRepair.getTechnician().getName());
            }

            if (currentRepair.getClient() != null) {
                tvSummaryClient.setText(getString(R.string.client) + ": " + currentRepair.getClient().getName());
            }

            if (currentRepair.getDevice() != null) {
                tvSummaryDevice.setText(getString(R.string.device) + currentRepair.getDevice().getModel());
            }

            if (currentRepair.getDiagnosis() != null) {
                tvDiagnosisDescriptionSummary.setText(getString(R.string.description) + ": " + currentRepair.getDiagnosis().getDescription());
                tvDiagnosisCostSummary.setText(getString(R.string.cost) + ": " + currentRepair.getDiagnosis().getEstimatedCost());
                tvDiagnosisTimeSummary.setText(getString(R.string.time_needed) + ": " + currentRepair.getDiagnosis().getEstimatedTime());
            }

        }

        if (currentRepair != null && currentRepair.getDiagnosis() != null) {
            List<Diagnosis.DiagnosisCheckItem> completedCheckItems = currentRepair.getDiagnosis().getCompletedCheckItems();
            CompletedCheckListAdapter adapter = new CompletedCheckListAdapter(completedCheckItems);
            rvSummaryDiagnosis.setAdapter(adapter);
            rvSummaryDiagnosis.setHasFixedSize(true);
            rvSummaryDiagnosis.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }

        //Boton
        btnSummaryCompleteRepair.setOnClickListener(v -> {
            String repairResult = etSummaryRepairResult.getText().toString();

            if (repairResult.isEmpty()) {
                Toast.makeText(getContext(), "Debes escribir una descripción de la reparación", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentRepair != null) {
                currentRepair.setRepairResult(repairResult);
            }

            if (repairClickListener != null) {
                repairClickListener.OnRepairCompleted();
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        repairClickListener = (IOnItemRepairClickListener) requireActivity();
        currentRepair = dataProvider.getRepair();

    }
}
