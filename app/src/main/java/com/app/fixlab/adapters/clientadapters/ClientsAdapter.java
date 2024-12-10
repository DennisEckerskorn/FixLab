package com.app.fixlab.adapters.clientadapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fixlab.R;
import com.app.fixlab.listeners.IonItemClickListenerGeneric;
import com.app.fixlab.models.persons.Person;

import java.util.List;

/**
 * Adapter class for displaying a list of clients in a {@link RecyclerView}.
 * This adapter binds client data to each item view in the list, including displaying
 * the client's name, phone number, and email.
 * <p>
 * The adapter also handles item click events via the {@link IonItemClickListenerGeneric}
 * interface, which is passed from the hosting activity or fragment to respond to item clicks.
 * </p>
 *
 * @see IonItemClickListenerGeneric
 * @see Person
 */
public class ClientsAdapter extends RecyclerView.Adapter<ClientsAdapter.ClientViewHolder> {
    private final List<Person> clients;
    private IonItemClickListenerGeneric<Person> clientListener;

    /**
     * Constructor for the adapter.
     *
     * @param clients List of {@link Person} objects representing the clients to display.
     */
    public ClientsAdapter(List<Person> clients) {
        this.clients = clients;
    }


    /**
     * Sets the listener for item click events.
     *
     * @param clientListener The listener to handle item clicks.
     */
    public void setListener(IonItemClickListenerGeneric<Person> clientListener) {
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

    /**
     * ViewHolder class for client items in the RecyclerView.
     * It holds the views representing the client's data and sets up the click listener.
     */
    class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView ivClientPhoto;
        private final TextView tvNameClient;
        private final TextView tvPhoneClient;
        private final TextView tvEmailClient;

        private Person client;

        /**
         * Constructor for the {@link ClientViewHolder}.
         * Initializes the views and sets the item view's click listener.
         *
         * @param itemView The item view for this client.
         */
        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            ivClientPhoto = itemView.findViewById(R.id.ivClientPhoto);
            tvNameClient = itemView.findViewById(R.id.tvNameClient);
            tvPhoneClient = itemView.findViewById(R.id.tvPhoneClient);
            tvEmailClient = itemView.findViewById(R.id.tvEmailClient);
            itemView.setOnClickListener(this);
        }

        /**
         * Binds a {@link Person} client to the views in this ViewHolder.
         *
         * @param client The client to bind to the views.
         */
        private void bindClient(Person client) {
            this.client = client;
            ivClientPhoto.setImageResource(R.drawable.profile);
            tvNameClient.setText(client.getName());
            tvPhoneClient.setText(client.getPhoneNumber());
            tvEmailClient.setText(client.getEmail());
        }

        /**
         * Handles click events on the client item. Calls the listener's {@link IonItemClickListenerGeneric#onItemClick(Object)} method.
         *
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
            if (clientListener != null) {
                clientListener.onItemClick(client);
            }
        }
    }
}