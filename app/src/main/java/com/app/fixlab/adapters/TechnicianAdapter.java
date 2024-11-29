package com.app.fixlab.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.listeners.OnClickListenerTechnicianRepairSelection;
import com.app.fixlab.listeners.OnClickListenerTechnicians;
import com.app.fixlab.models.persons.Person;

import java.util.List;

public class TechnicianAdapter extends RecyclerView.Adapter<TechnicianAdapter.TechnicianViewHolder> {
    private final List<Person> technicians;
    private OnClickListenerTechnicians technicianListener;
    private OnClickListenerTechnicianRepairSelection repairSelectionListener;


    public TechnicianAdapter(List<Person> technicians) {
        this.technicians = technicians;
    }

    public void setListenerTechnicians(OnClickListenerTechnicians technicianListener) {
        this.technicianListener = technicianListener;
    }

    public void setListenerRepairSelection(OnClickListenerTechnicianRepairSelection repairSelectionListener) {
        this.repairSelectionListener = repairSelectionListener;
    }

    @NonNull
    @Override
    public TechnicianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.technician_item, parent, false);
        return new TechnicianViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull TechnicianViewHolder holder, int position) {
        holder.bindTechnician(technicians.get(position));
    }

    @Override
    public int getItemCount() {
        return technicians.size();
    }


    class TechnicianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvNameTechnician;
        private final TextView tvDniTechnician;
        private final TextView tvAvailabilityTechnician;

        private Person technician;;

        public TechnicianViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameTechnician = itemView.findViewById(R.id.tvTechnicianName);
            tvDniTechnician = itemView.findViewById(R.id.tvTechnicianDni);
            tvAvailabilityTechnician = itemView.findViewById(R.id.tvTechnicianAvailability);
            itemView.setOnClickListener(this);
        }

        private void bindTechnician(Person technician) {
            this.technician = technician;
            tvNameTechnician.setText(technician.getName());
            tvDniTechnician.setText(technician.getDni());
            //tvAvailabilityTechnician.setText(technician.getAvailability());
        }

        @Override
        public void onClick(View view) {
            if (technicianListener != null) {
                technicianListener.onTechniciansClick(technician);
            }
        }
    }
}
