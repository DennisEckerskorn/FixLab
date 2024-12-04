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
import com.app.fixlab.adapters.DiagnosisCheckListAdapter;
import com.app.fixlab.listeners.IOnItemRepairClickListener;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.models.repair.Repair;

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
        Button btnSummaryCompleteRepair = view.findViewById(R.id.btnSummaryCompleteRepair);


        if (currentRepair != null) {
            if (currentRepair.getTechnician() != null) {
                tvSummaryTechnician.setText("Técnico: " + currentRepair.getTechnician().getName());
            }
            //if(currentRepair.getClient() != null) {
            //  tvSummaryClient.setText("Cliente: " + currentRepair.getClient().getName());
            //}

            if (currentRepair.getDevice() != null) {
                tvSummaryDevice.setText("Dispositivo: " + currentRepair.getDevice().getModel());
            }

        }


        DiagnosisCheckListAdapter diagnosisCheckListAdapter = new DiagnosisCheckListAdapter();
        if (currentRepair != null && currentRepair.getDiagnosis() != null) {
            diagnosisCheckListAdapter.setOnCheckChangedListener(null); //No hacen falta cambios
            diagnosisCheckListAdapter.notifyDataSetChanged();
        }
        rvSummaryDiagnosis.setAdapter(diagnosisCheckListAdapter);
        rvSummaryDiagnosis.setHasFixedSize(true);
        rvSummaryDiagnosis.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        //Boton
        btnSummaryCompleteRepair.setOnClickListener(v -> {
            String repairResult = etSummaryRepairResult.getText().toString();
            if (currentRepair.getRepairResult().isEmpty()) {
                Toast.makeText(getContext(), "Debes escribir una descripción de la reparación", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Reparación completada", Toast.LENGTH_SHORT).show();
            }

            if (currentRepair != null) {
                currentRepair.setRepairResult(repairResult);
                currentRepair.setStatus(Repair.RepairStatus.COMPLETED);
                Toast.makeText(getContext(), "Reparación completada", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        currentRepair = dataProvider.getRepair();

    }
}
