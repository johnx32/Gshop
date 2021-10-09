package org.jbtc.gshop.ui.productos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentProductosBinding;
import org.jbtc.gshop.adapter.ProductosAdapter;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.ProductoViewModel;

import java.util.List;

import io.reactivex.functions.BiConsumer;

public class ProductosFragment extends Fragment {
    private FragmentProductosBinding binding;
    private ProductosAdapter adapter;
    private ProductoViewModel productoViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductosBinding.inflate(inflater,container,false);
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);

        initAdapter();

        productoViewModel.getAllProducto()
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

        return binding.getRoot();
    }

    private void initAdapter() {
        binding.rvProdList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvProdList.setLayoutManager(linearLayoutManager);
        adapter = new ProductosAdapter();
        binding.rvProdList.setAdapter(adapter);
    }


}
