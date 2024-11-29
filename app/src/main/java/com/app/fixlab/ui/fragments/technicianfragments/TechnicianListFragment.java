package com.app.fixlab.ui.fragments.technicianfragments;

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
import com.app.fixlab.listeners.OnClickListenerTechnicians;
import com.app.fixlab.listeners.OnClickRepairTechnician;
import com.app.fixlab.models.persons.Person;

import java.util.List;

public class TechnicianListFragment extends Fragment {

    public interface ITechnicianListFragmentListener {
        List<Person> getTechnicians();
    }

    private List<Person> technicians;
    private ITechnicianListFragmentListener fragmentListener;
    private OnClickListenerTechnicians clickListener;

    public TechnicianListFragment(){
        super(R.layout.fragment_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvList = view.findViewById(R.id.rvList);

        // Set the adapter for the recycler view
        TechnicianAdapter technicianAdapter = new TechnicianAdapter(technicians);
        technicianAdapter.setListenerTechnicians(clickListener);
        rvList.setAdapter(technicianAdapter);
        rvList.setHasFixedSize(true);
        rvList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentListener = (ITechnicianListFragmentListener) requireActivity();
            clickListener = (OnClickListenerTechnicians) requireActivity();
            technicians = fragmentListener.getTechnicians();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement both ITechnicianListFragmentListener and OnClickListenerTechnicians");
        }
    }
}