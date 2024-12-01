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
import com.app.fixlab.adapters.TechnicianAdapter;
import com.app.fixlab.listeners.OnClickListenerTechnicianRepairSelection;
import com.app.fixlab.listeners.OnClickRepairTechnician;
import com.app.fixlab.models.persons.Person;

import java.util.List;

public class TechnicianSelectionFragment extends Fragment {
    public interface ITechnicianSelectionFragmentListener {
        List<Person> getTechniciansForSelection();
    }

    private List<Person> technicians;
    private ITechnicianSelectionFragmentListener fragmentListener;
    private OnClickRepairTechnician clickListener;

    public TechnicianSelectionFragment() {
        super(R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvList = view.findViewById(R.id.rvList);

        TechnicianAdapter technicianAdapter = new TechnicianAdapter(technicians);
        technicianAdapter.setListenerRepairSelection((OnClickListenerTechnicianRepairSelection) clickListener);
        rvList.setAdapter(technicianAdapter);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        fragmentListener = (ITechnicianSelectionFragmentListener) requireActivity();
        clickListener = (OnClickRepairTechnician) requireActivity();
        technicians = fragmentListener.getTechniciansForSelection();
    }
}
