package org.jbtc.gshop.db.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jbtc.gshop.R;
import org.jbtc.gshop.db.entity.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.CategoriasViewHolder> {
    private List<Categoria> items = new ArrayList<>();

    public void setItems(List<Categoria> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_categorias,parent,false);
        CategoriasViewHolder categoriasViewHolder = new CategoriasViewHolder(v);
        return categoriasViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriasViewHolder holder, int position) {
        holder.name.setText(items.get(position).nombre);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CategoriasViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;

        public CategoriasViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.iv_cv_cat_img);
            name = itemView.findViewById(R.id.tv_cv_cat_name);
        }
    }
}
