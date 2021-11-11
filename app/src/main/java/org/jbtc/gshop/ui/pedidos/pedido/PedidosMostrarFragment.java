package org.jbtc.gshop.ui.pedidos.pedido;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jbtc.gshop.databinding.FragmentPedidosMostrarBinding;
import org.jbtc.gshop.db.entity.Pedido;
import org.jbtc.gshop.db.viewmodel.PedidosViewModel;


public class PedidosMostrarFragment extends Fragment {
    private FragmentPedidosMostrarBinding binding;
    private PedidosViewModel pedidosViewModel;
    private Pedido pedido;

    //   mostrar los datos del pedido
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPedidosMostrarBinding.inflate(inflater, container, false);
        return binding.getRoot();
     }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pedidosViewModel = new ViewModelProvider(this).get(PedidosViewModel.class);

        super.onViewCreated(view, savedInstanceState);
        Bundle b = getArguments();
        if(b!=null){
            long id=b.getLong("id",0);
            pedidosViewModel.getPedido(id)
                    .subscribe((p, throwable) -> {
                        pedido = p;
                        setPedidoToLayout(p);
                    });
        }
    }

    private void setPedidoToLayout(Pedido p) {
        binding.etCodigoPedido.setText(p.key);
        binding.etFechaPedido.setText(p.timestamp.toString());
    }
}