package org.jbtc.gshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jbtc.gshop.R;
import org.jbtc.gshop.db.entity.ProductoCantidad;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    private List<ProductoCantidad> items = new ArrayList<>();

    public void setItems(List<ProductoCantidad> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_item_producto,parent,false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.cantidad.setText(String.valueOf(items.get(position).cantidad));
        holder.nombre.setText(items.get(position).producto.nombre);
        holder.price.setText(items.get(position).producto.precio+" $");
        holder.pricePartial.setText(items.get(position).cantidad*items.get(position).producto.precio+" $");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView cantidad,nombre,price,pricePartial;
        public ItemViewHolder(View itemView) {
            super(itemView);
            cantidad = itemView.findViewById(R.id.tv_cv_item_cantidad);
            nombre = itemView.findViewById(R.id.tv_cv_item_producto_name);
            price = itemView.findViewById(R.id.tv_cv_item_price);
            pricePartial = itemView.findViewById(R.id.tv_cv_item_price_partial);
        }
    }

}
