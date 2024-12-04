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
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;

public class DiagnosisFragment extends Fragment {

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
        rvCheckList.setHasFixedSize(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

}
