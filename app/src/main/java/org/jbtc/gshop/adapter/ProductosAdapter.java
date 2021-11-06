package org.jbtc.gshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jbtc.gshop.R;
import org.jbtc.gshop.db.entity.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ProductosViewHolder> {

    private List<Producto> items = new ArrayList<>();


    public ProductosAdapter(OnClick onClick) {
        this.onClick = onClick;
    }

    public void setItems(List<Producto> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_producto,parent,false);
        ProductosViewHolder productosViewHolder = new ProductosViewHolder(v);
        return productosViewHolder;
    }

    @Override
    public void onBindViewHolder(ProductosViewHolder holder, int position) {
        holder.name.setText( items.get(position).nombre );
        holder.descripcion.setText(items.get(position).descripcion);
        holder.precio.setText(String.valueOf(items.get(position).precio));
        Picasso.get()
                .load(items.get(position).url)
                .into(holder.img);
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ProductosViewHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name;
        TextView descripcion;
        TextView precio;
        public ProductosViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.iv_cv_prod_img);
            name=itemView.findViewById(R.id.tv_cv_prod_name);
            descripcion=itemView.findViewById(R.id.tv_cv_prod_descripcion);
            precio = itemView.findViewById(R.id.tv_cv_prod_precio);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClickCard(items.get(getAdapterPosition()));
                }
            });
            
        }
    }
    private OnClick onClick;
    public interface OnClick{
        void onClickCard(Producto producto);
    }
}
