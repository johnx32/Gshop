package org.jbtc.gshop.ui.clientes.cliente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import org.jbtc.gshop.databinding.FragmentClienteEditBinding;

public class ClienteEditFragment extends Fragment {
    private FragmentClienteEditBinding binding;


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentClienteEditBinding.inflate(inflater, container,false);
        return binding.getRoot();
    }
}
