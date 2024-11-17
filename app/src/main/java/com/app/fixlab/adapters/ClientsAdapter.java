package com.app.fixlab.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.listeners.OnClickListenerClients;
import com.app.fixlab.models.persons.Person;

import java.util.List;

public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientViewHolder> {
    private final List<Person> clients;
    private OnClickListenerClients clientListener;


    public ClientsAdapter(List<Person> clients) {
        this.clients = clients;
    }

    public void setListener(OnClickListenerClients clientListener) {
        this.clientListener = clientListener;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_client, parent, false);
        return new ClientViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        holder.bindClient(clients.get(position));
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }


    class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView tvClientName;
        private final TextView tvClientSurname;
        private Person client;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvClientName = itemView.findViewById(R.id.tvClientName);
            tvClientSurname = itemView.findViewById(R.id.tvClientSurname);
            itemView.setOnClickListener(this);
        }

        private void bindClient(Person client) {
            this.client = client;
            tvClientName.setText(client.getName());
            tvClientSurname.setText(client.getSurname());
        }

        @Override
        public void onClick(View view) {
            if (clientListener != null) {
                clientListener.onClick(client);
            }
        }
    }
}
