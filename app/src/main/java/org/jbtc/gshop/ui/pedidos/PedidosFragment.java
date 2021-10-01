package org.jbtc.gshop.ui.pedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;


import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentPedidosBinding;

public class PedidosFragment extends Fragment {

    private FragmentPedidosBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPedidosBinding.inflate(inflater, container, false);
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_pedidos, container, false);
    }
}


