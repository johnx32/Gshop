package org.jbtc.gshop.ui.clientes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.jbtc.gshop.databinding.FragmentClientesBinding;
import org.jbtc.gshop.adapter.ClienteAdapter;
import org.jbtc.gshop.db.entity.Cliente;
import org.jbtc.gshop.db.viewmodel.ClienteViewModel;

import java.util.List;

import io.reactivex.functions.BiConsumer;


public class ClientesFragment extends Fragment {

    private FragmentClientesBinding binding;
    private ClienteAdapter adapter;
    private ClienteViewModel clienteViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentClientesBinding.inflate(inflater,container,false);
        clienteViewModel = new ViewModelProvider(this).get(ClienteViewModel.class);

        initAdapter();

        clienteViewModel.getAllCliente()
                .subscribe(new BiConsumer<List<Cliente>, Throwable>() {
                    @Override
                    public void accept(List<Cliente> clientes, Throwable throwable) throws Exception {
                        if (throwable==null)
                            adapter.setItems(clientes);
                        else Log.e("TAG", "accept: Error: ", throwable);
                    }
                });

        return binding.getRoot();
    }

    private void initAdapter() {
        binding.rvClientList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvClientList.setLayoutManager(linearLayoutManager);
        adapter = new ClienteAdapter();
        binding.rvClientList.setAdapter(adapter);
    }

}