package com.app.fixlab.ui.fragments.repairfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.adapters.repairadapters.CompletedRepairAdapter;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.models.repair.Repair;

import java.util.List;

public class CompletedRepairListFragment extends Fragment {
    private List<Repair> completedRepairs;
    private IonItemClickListenerGeneric<Repair> itemClickListener;

    public CompletedRepairListFragment() {
        super(R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = view.findViewById(R.id.rvList);
        CompletedRepairAdapter repairAdapter = new CompletedRepairAdapter(completedRepairs);
        repairAdapter.setListener(itemClickListener);
        rvList.setAdapter(repairAdapter);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider dataProvider = (IdataProvider) requireActivity();
        itemClickListener = (IonItemClickListenerGeneric<Repair>) requireActivity();
        completedRepairs = dataProvider.getCompletedRepairs();
    }
}
