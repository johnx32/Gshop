package org.jbtc.gshop.ui.pedidos.pedido;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jbtc.gshop.adapter.ItemsAdapter;
import org.jbtc.gshop.databinding.FragmentPedidosMostrarBinding;
import org.jbtc.gshop.db.entity.Item;
import org.jbtc.gshop.db.entity.Pedido;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.ItemViewModel;
import org.jbtc.gshop.db.viewmodel.PedidosViewModel;
import org.jbtc.gshop.db.viewmodel.ProductosViewModel;

import java.util.List;


public class PedidosMostrarFragment extends Fragment {
    private FragmentPedidosMostrarBinding binding;
    private PedidosViewModel pedidosViewModel;
    private ItemViewModel itemViewModel;
    private ProductosViewModel productosViewModel;
    private ItemsAdapter adapter;
    private Pedido pedido;
    private List<Item> itemList;
    private List<Producto> productoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPedidosMostrarBinding.inflate(inflater, container, false);
        return binding.getRoot();
     }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pedidosViewModel = new ViewModelProvider(this).get(PedidosViewModel.class);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        productosViewModel = new ViewModelProvider(this).get(ProductosViewModel.class);

        initRecycler();

        loadArguments();
    }

    private void loadArguments() {
        Bundle b = getArguments();
        if(b!=null){
            long id = b.getLong("id",0);
            pedidosViewModel.getPedido(id)
                    .flatMap(pedido1 -> {
                        pedido=pedido1;
                        setPedidoToLayout(pedido1);
                        return productosViewModel.getProductosByPedido(pedido1.key);
                    })
                    .subscribe((productoCantidadList, throwable) -> {
                       if(throwable==null)
                           adapter.setItems(productoCantidadList);
                    });
        }
    }

    private void initRecycler() {
            binding.rvPedMostrarItems.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            binding.rvPedMostrarItems.setLayoutManager(linearLayoutManager);
            adapter = new ItemsAdapter();
            binding.rvPedMostrarItems.setAdapter(adapter);
        }

    private void setPedidoToLayout(Pedido p) {
        binding.etCodigoPedido.setText(p.key);
        binding.etFechaPedido.setText(p.timestamp.toString());
    }
}