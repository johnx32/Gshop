package org.jbtc.gshop.ui.pedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jbtc.gshop.databinding.FragmentPedidosBinding;

public class PedidosFragment extends Fragment {
<<<<<<< HEAD

    private FragmentPedidosBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPedidosBinding.inflate(inflater, container, false);
=======
    private FragmentPedidosBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPedidosBinding.inflate(inflater,container,false);
>>>>>>> login
        return binding.getRoot();
    }
}
