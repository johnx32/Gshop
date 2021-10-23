package org.jbtc.gshop.ui.pedidos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jbtc.gshop.R;
import org.jbtc.gshop.adapter.PedidosAdapter;
import org.jbtc.gshop.databinding.FragmentPedidosBinding;
import org.jbtc.gshop.db.entity.Pedido;
import org.jbtc.gshop.db.viewmodel.PedidosViewModel;

import java.util.List;

import io.reactivex.functions.BiConsumer;

public class PedidosFragment extends Fragment {
    private FragmentPedidosBinding binding;
    private PedidosAdapter adapter;
    private PedidosViewModel pedidosViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPedidosBinding.inflate(inflater,container,false);

        pedidosViewModel = new ViewModelProvider(this).get(PedidosViewModel.class);
        initAdapter();

        pedidosViewModel.getAllPedido()

                .subscribe(new BiConsumer<List<Pedido>, Throwable>() {
                    @Override
                    public void accept(List<Pedido> pedidos, Throwable throwable) throws Exception {
                        if (throwable==null)
                            adapter.setItems(pedidos);
                        else Log.e("TAG", "accept: Error: ", throwable);
                    }
                });

        return binding.getRoot();
    }

    private void initAdapter() {
        binding.rvPedList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvPedList.setLayoutManager(linearLayoutManager);
        adapter = new PedidosAdapter(new PedidosAdapter.OnClick() {

            @Override
            public void onClickCard(Pedido pedido) {
                Bundle b = new Bundle();
                b.putLong("id",pedido.id);
                NavHostFragment.findNavController(PedidosFragment.this)
                        .navigate(R.id.action_nav_pedidos_to_nav_clientes,b);
            }
        });
        binding.rvPedList.setAdapter(adapter);
    }


}
