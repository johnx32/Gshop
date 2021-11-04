package org.jbtc.gshop.ui.pedidos.pedido;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.jbtc.gshop.databinding.FragmentPedidosMostrarBinding;


public class PedidosMostrarFragment extends Fragment {
    private  FragmentPedidosMostrarBinding binding;
    //   mostrar los datos del pedido
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPedidosMostrarBinding.inflate(inflater, container, false);
        return binding.getRoot();
     }
}