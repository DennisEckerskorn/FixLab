package com.app.fixlab.adapters.technicianadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.listeners.IOnItemRepairClickListener;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.models.persons.Person;
import com.app.fixlab.models.persons.Technician;

import java.util.List;

/**
 * Adapter for managing a list of technicians in a RecyclerView.
 * This class binds data related to technicians to views and handles interactions with them.
 */
public class TechnicianAdapter extends RecyclerView.Adapter<TechnicianAdapter.TechnicianViewHolder> {

    private final List<Person> technicians;
    private IonItemClickListenerGeneric<Person> technicianListener;
    private IOnItemRepairClickListener repairListener;
    private final boolean disableUnavailableSelection;

    /**
     * Constructor for the TechnicianAdapter.
     *
     * @param technicians              the list of technicians to display.
     * @param disableUnavailableSelection flag to determine if unavailable technicians can be selected.
     */
    public TechnicianAdapter(List<Person> technicians, boolean disableUnavailableSelection) {
        this.technicians = technicians;
        this.disableUnavailableSelection = disableUnavailableSelection;
    }

    /**
     * Sets the listener for technician item clicks.
     *
     * @param technicianListener the listener to handle item clicks.
     */
    public void setListenerTechnicians(IonItemClickListenerGeneric<Person> technicianListener) {
        this.technicianListener = technicianListener;
    }

    /**
     * Sets the listener for repair item clicks.
     *
     * @param repairListener the listener to handle repair item clicks.
     */
    public void setRepairListener(IOnItemRepairClickListener repairListener) {
        this.repairListener = repairListener;
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

    /**
     * ViewHolder for the RecyclerView to represent a technician item.
     */
    class TechnicianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView ivTechnicianPhoto;
        private final TextView tvNameTechnician;
        private final TextView tvDniTechnician;
        private final TextView tvAvailabilityTechnician;
        private Person technician;

        /**
         * Constructor for TechnicianViewHolder.
         *
         * @param itemView the view for the technician item.
         */
        public TechnicianViewHolder(@NonNull View itemView) {
            super(itemView);
            ivTechnicianPhoto = itemView.findViewById(R.id.ivTechnicianPhoto);
            tvNameTechnician = itemView.findViewById(R.id.tvTechnicianName);
            tvDniTechnician = itemView.findViewById(R.id.tvTechnicianDni);
            tvAvailabilityTechnician = itemView.findViewById(R.id.tvTechnicianAvailability);
            itemView.setOnClickListener(this);
        }

        /**
         * Binds a technician's data to the view.
         *
         * @param technician the technician whose data is to be displayed.
         */
        private void bindTechnician(Person technician) {
            this.technician = technician;
            ivTechnicianPhoto.setImageResource(R.drawable.ic_launcher_foreground);
            tvNameTechnician.setText(technician.getName());
            tvDniTechnician.setText(technician.getDni());

            if (technician instanceof Technician) {
                Technician.Availability availability = ((Technician) technician).getAvailability();
                tvAvailabilityTechnician.setText(availability.toString());

                if (availability == Technician.Availability.AVAILABLE) {
                    tvAvailabilityTechnician.setTextColor(ContextCompat.getColor(tvAvailabilityTechnician.getContext(), R.color.neon_green));
                    itemView.setAlpha(1.0f);
                } else {
                    tvAvailabilityTechnician.setTextColor(ContextCompat.getColor(tvAvailabilityTechnician.getContext(), R.color.red));
                    itemView.setAlpha(disableUnavailableSelection ? 0.5f : 1.0f);
                }
            }
        }

        @Override
        public void onClick(View view) {
            if (technician instanceof Technician) {
                Technician.Availability availability = ((Technician) technician).getAvailability();
                if (disableUnavailableSelection && availability == Technician.Availability.UNAVAILABLE) {
                    return;
                }
            }

            if (technicianListener != null) {
                technicianListener.onItemClick(technician);
            }

            if (repairListener != null) {
                repairListener.onItemClickRepair(technician);
            }
        }
    }
}
