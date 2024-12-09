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

/**
 * DiagnosisFragment facilitates the creation and management of a repair diagnosis.
 *
 * <p>This fragment allows users to interact with a checklist, provide a detailed diagnosis,
 * estimate costs, and specify the time required for repairs. It communicates with the
 * hosting activity to manage data and handle user actions.</p>
 *
 * <p>The layout for this fragment is defined in {@code R.layout.fragment_diagnosis}.</p>
 *
 * <p>Key Features:
 * <ul>
 *   <li>Displays a checklist of diagnosis items using {@link DiagnosisCheckListAdapter}.</li>
 *   <li>Allows users to input a description, estimated cost, and estimated repair time.</li>
 *   <li>Enables or disables fields based on checklist selection.</li>
 *   <li>Validates user inputs before saving the diagnosis.</li>
 * </ul>
 * </p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link IdataProvider} interface for retrieving the current repair data.</li>
 *   <li>{@link OnCheckedChangeListener} interface for handling checklist item state changes.</li>
 *   <li>{@link OnSaveDiagnosis} interface for saving the diagnosis data.</li>
 * </ul>
 * </p>
 *
 * @author [Dennis, Borja]
 * @version 1.0
 */
public class DiagnosisFragment extends Fragment {
    private Repair currentRepair;
    private OnCheckedChangeListener checkedChangeListener;
    private OnSaveDiagnosis saveDiagnosisListener;

    /**
     * Default constructor. Initializes the fragment with its layout resource.
     */
    public DiagnosisFragment() {
        super(R.layout.fragment_diagnosis);
    }

    /**
     * Called when the view hierarchy for this fragment is created.
     * Sets up the RecyclerView, input fields, and save button.
     *
     * @param view               The root view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvCheckList = view.findViewById(R.id.rvCheckList);
        EditText edDiagDescription = view.findViewById(R.id.edDiagDescription);
        EditText edEstimatedCost = view.findViewById(R.id.edEstimatedCost);
        EditText edEstimatedTime = view.findViewById(R.id.edEstimatedTime);
        Button btnSaveDiagnosis = view.findViewById(R.id.btnSaveDiagnosis);

        // Set up the checklist RecyclerView with its adapter
        DiagnosisCheckListAdapter adapter = new DiagnosisCheckListAdapter();
        adapter.setOnCheckChangedListener((item, isChecked) -> {
            if (checkedChangeListener != null) {
                checkedChangeListener.onCheckedChange(item, isChecked);
            }
            // Enable or disable fields based on checklist selection
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

        // Save button click listener
        btnSaveDiagnosis.setOnClickListener(v -> {

            String description = edDiagDescription.getText().toString();
            String estimatedCost = edEstimatedCost.getText().toString();
            String estimatedTime = edEstimatedTime.getText().toString();

            if (validateInputs(description, estimatedCost, estimatedTime)) {
                // Update the current repair's diagnosis
                currentRepair.getDiagnosis().setDescription(description);
                currentRepair.getDiagnosis().setEstimatedCost(estimatedCost);
                currentRepair.getDiagnosis().setEstimatedTime(estimatedTime);

                // Notify the listener
                if (saveDiagnosisListener != null) {
                    saveDiagnosisListener.onSaveDiagnosis(currentRepair.getDiagnosis());
                }
            }
        });

    }

    /**
     * Validates the user inputs for the diagnosis fields.
     *
     * @param description   The diagnosis description.
     * @param estimatedCost The estimated cost of the repair.
     * @param estimatedTime The estimated time for the repair.
     * @return {@code true} if all fields are valid, {@code false} otherwise.
     */
    private boolean validateInputs(String description, String estimatedCost, String
            estimatedTime) {
        if (description.isEmpty() || estimatedCost.isEmpty() || estimatedTime.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Called when the fragment is attached to its host activity.
     * Initializes listeners and retrieves the current repair data.
     *
     * @param context The context of the host activity.
     * @throws ClassCastException if the host activity does not implement required interfaces.
     */
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
