package com.app.fixlab.adapters;

import static android.provider.Settings.System.getString;

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
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
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
        private final TextView tvNameClient;
        private final TextView tvPhoneClient;
        private final TextView tvEmailClient;

        private Person client;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameClient = itemView.findViewById(R.id.tvNameClient);
            tvPhoneClient = itemView.findViewById(R.id.tvPhoneClient);
            tvEmailClient = itemView.findViewById(R.id.tvEmailClient);
            itemView.setOnClickListener(this);
        }

        private void bindClient(Person client) {
            this.client = client;
            tvNameClient.setText(client.getName());
            tvPhoneClient.setText(client.getPhoneNumber());
            tvEmailClient.setText(client.getEmail());
        }

        @Override
        public void onClick(View view) {
            if (clientListener != null) {
                clientListener.onClick(client);
            }
        }
    }
}
