package org.jbtc.gshop.ui.productos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentProductosBinding;
import org.jbtc.gshop.adapter.ProductosAdapter;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.ProductosViewModel;
import org.jbtc.gshop.ui.productos.producto.ProductoAddFragment;

import java.util.List;

import io.reactivex.functions.BiConsumer;

public class ProductosFragment extends Fragment {

    private FragmentProductosBinding binding;
    private ProductosAdapter adapter;
    private ProductosViewModel productosViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductosBinding.inflate(inflater,container,false);
        productosViewModel = new ViewModelProvider(this).get(ProductosViewModel.class);

        initAdapter();

        productosViewModel.getAllProducto()
                .subscribe(new BiConsumer<List<Producto>, Throwable>() {
                    @Override
                    public void accept(List<Producto> productos, Throwable throwable) throws Exception {
                        if (throwable==null)
                            adapter.setItems(productos);
                        else Log.e("TAG", "accept: Error: ", throwable);
                    }
                });

        //NavHostFragment.findNavController(this)
                //.navigate(R.id.action_nav_productos_to_productoEditFragment);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ProductosFragment.this)
                        .navigate(R.id.action_nav_productos_to_productoAddFragment);
            }
        });

        return binding.getRoot();
    }

    private void initAdapter() {
        binding.rvProdList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvProdList.setLayoutManager(linearLayoutManager);
        adapter = new ProductosAdapter(new ProductosAdapter.OnClick() {
            @Override
            public void onClickCard(Producto producto) {
                Bundle b = new Bundle();
                b.putLong("id",producto.id);
                NavHostFragment.findNavController(ProductosFragment.this)
                    .navigate(R.id.action_nav_productos_to_productoEditFragment,b);
            }
        });
        binding.rvProdList.setAdapter(adapter);
    }


}
