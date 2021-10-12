package org.jbtc.gshop.ui.pedidos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentPedidosEditBinding;


public class PedidosEditFragment extends Fragment {
    private @NonNull FragmentPedidosEditBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPedidosEditBinding.inflate(inflater, container, false);
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_pedidos_edit, container, false);
    }
}