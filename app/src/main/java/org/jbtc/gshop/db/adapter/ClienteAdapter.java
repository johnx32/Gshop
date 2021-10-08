package org.jbtc.gshop.db.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jbtc.gshop.R;
import org.jbtc.gshop.db.entity.Cliente;
import org.jbtc.gshop.db.entity.Producto;

import java.util.ArrayList;
import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClientesViewHolder>{

    private List<Cliente> items = new ArrayList<>();

    public void setItems(List<Cliente> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClientesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_cliente,parent,false);
        ClienteAdapter.ClientesViewHolder clientesViewHolder = new ClientesViewHolder(v);
        return clientesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientesViewHolder holder, int position) {
        holder.name.setText(items.get(position).nombre);
        holder.email.setText(items.get(position).email);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ClientesViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        TextView email;
        public ClientesViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.iv_cv_client_img);
            name=itemView.findViewById(R.id.tv_cv_client_name);
            email=itemView.findViewById(R.id.tv_cv_client_email);
        }
    }
}
