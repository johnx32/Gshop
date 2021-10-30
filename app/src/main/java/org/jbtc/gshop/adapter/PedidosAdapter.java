package org.jbtc.gshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jbtc.gshop.R;
import org.jbtc.gshop.db.entity.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidosViewHolder> {
    private List<Pedido> items = new ArrayList<>();

    public PedidosAdapter(
            PedidosAdapter.OnClick onClick) {
        this.onClick = onClick;
    }

    public void setItems(List<Pedido> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PedidosAdapter.PedidosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pedido,parent,false);
        PedidosAdapter.PedidosViewHolder pedidosViewHolder = new PedidosAdapter.PedidosViewHolder(v);
        return pedidosViewHolder;
    }

    @Override
    public void onBindViewHolder( PedidosAdapter.PedidosViewHolder holder, int position) {
        holder.codigo.setText(items.get(position).key);
        holder.fecha.setText(items.get(position).timestamp.toString());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PedidosViewHolder extends RecyclerView.ViewHolder{
        TextView codigo;
        TextView fecha;

        public PedidosViewHolder(@NonNull View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.tv_cv_pedido_cod);
            fecha = itemView.findViewById(R.id.tv_cv_pedido_date);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickCard(items.get(getAdapterPosition()));
                }
            });
        }
    }

    private PedidosAdapter.OnClick onClick;
    public interface OnClick{
        void onClickCard(Pedido pedido);
    }
}
