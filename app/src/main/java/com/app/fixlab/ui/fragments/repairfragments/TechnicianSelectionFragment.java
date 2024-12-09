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
import com.app.fixlab.adapters.technicianadapters.TechnicianAdapter;
import com.app.fixlab.listeners.IOnItemRepairClickListener;
import com.app.fixlab.listeners.IdataProvider;
import com.app.fixlab.models.persons.Person;

import java.util.List;

public class TechnicianSelectionFragment extends Fragment {
    private List<Person> technicians;
    private IOnItemRepairClickListener itemClickListener;

    public TechnicianSelectionFragment() {
        super(R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvList = view.findViewById(R.id.rvList);

        TechnicianAdapter technicianAdapter = new TechnicianAdapter(technicians);
        technicianAdapter.setRepairListener(itemClickListener);
        rvList.setAdapter(technicianAdapter);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IdataProvider idataProvider = (IdataProvider) requireActivity();
        itemClickListener = (IOnItemRepairClickListener) requireActivity();
        technicians = idataProvider.getTechnicianData();
    }
}
