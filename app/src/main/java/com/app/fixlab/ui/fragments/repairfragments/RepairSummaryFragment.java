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
import com.app.fixlab.adapters.repairadapters.CompletedCheckListAdapter;
import com.app.fixlab.listeners.IOnItemRepairClickListener;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.models.repair.Diagnosis;
import com.app.fixlab.models.repair.Repair;

import java.util.List;

/**
 * RepairSummaryFragment displays a summary of a repair, including technician details,
 * client information, device details, diagnosis description, and a list of completed checklist items.
 *
 * <p>This fragment allows the user to view the repair information and complete the repair process
 * by providing a repair result description.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.fragment_repair_summary}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Displays technician, client, and device information.</li>
 *   <li>Shows the diagnosis details such as description, estimated cost, and time.</li>
 *   <li>Displays a list of completed checklist items using {@link CompletedCheckListAdapter}.</li>
 *   <li>Provides an option to enter a repair result description and complete the repair.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link IdataProvider} interface for retrieving the current repair data.</li>
 *   <li>{@link IOnItemRepairClickListener} interface for handling repair completion events.</li>
 * </ul>
 * </p>
 *
 * @author [Dennis Eckerskorn]
 */
public class RepairSummaryFragment extends Fragment {
    private Repair currentRepair;
    private IOnItemRepairClickListener repairClickListener;
    private boolean showFields = true;

    /**
     * Default constructor. Initializes the fragment with its layout resource.
     */
    public RepairSummaryFragment() {
        super(R.layout.fragment_repair_summary);
    }

    /**
     * Sets whether the fields related to repair completion should be shown.
     *
     * @param showFields True to show the fields, false to hide them.
     */
    public void setShowFields(boolean showFields) {
        this.showFields = showFields;
    }

    /**
     * Called when the fragment's view hierarchy is created.
     * Sets up the UI elements with data from the current repair object and sets up the RecyclerView.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvRepairRealizedLabel = view.findViewById(R.id.tvRepairRealizedLabel);
        TextView tvSummaryTechnician = view.findViewById(R.id.tvSummaryTechnician);
        TextView tvSummaryClient = view.findViewById(R.id.tvSummaryClient);
        TextView tvSummaryDevice = view.findViewById(R.id.tvSummaryDevice);
        RecyclerView rvSummaryDiagnosis = view.findViewById(R.id.rvSummaryDiagnosis);
        TextView etSummaryRepairResult = view.findViewById(R.id.etSummaryRepairResult);
        TextView tvDiagnosisDescriptionSummary = view.findViewById(R.id.tvDiagnosisDescriptionSummary);
        TextView tvDiagnosisCostSummary = view.findViewById(R.id.tvDiagnosisCostSummary);
        TextView tvDiagnosisTimeSummary = view.findViewById(R.id.tvDiagnosisTimeSummary);
        Button btnSummaryCompleteRepair = view.findViewById(R.id.btnSummaryCompleteRepair);

        // Hide fields if showFields is false
        if (!showFields) {
            tvRepairRealizedLabel.setVisibility(View.GONE);
            etSummaryRepairResult.setVisibility(View.GONE);
            btnSummaryCompleteRepair.setVisibility(View.GONE);
        }

        // Populate UI elements with data from the currentRepair object
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

        // Set up RecyclerView to display the completed checklist items
        if (currentRepair != null && currentRepair.getDiagnosis() != null) {
            List<Diagnosis.DiagnosisCheckItem> completedCheckItems = currentRepair.getDiagnosis().getCompletedCheckItems();
            CompletedCheckListAdapter adapter = new CompletedCheckListAdapter(completedCheckItems);
            rvSummaryDiagnosis.setAdapter(adapter);
            rvSummaryDiagnosis.setHasFixedSize(true);
            rvSummaryDiagnosis.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }

        // Handle the "Complete Repair" button click
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
                repairClickListener.onRepairCompleted();
            }
        });
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Initializes the data provider and repair click listener.
     *
     * @param context The context of the host activity.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        
        // Retrieve the data provider and repair click listener from the host activity
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        repairClickListener = (IOnItemRepairClickListener) requireActivity();
        currentRepair = dataProvider.getRepair();
    }
}
