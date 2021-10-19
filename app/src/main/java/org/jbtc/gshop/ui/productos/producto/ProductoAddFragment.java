package org.jbtc.gshop.ui.productos.producto;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentCategoriaEditBinding;
import org.jbtc.gshop.databinding.FragmentProductoAddBinding;
import org.jbtc.gshop.db.viewmodel.CategoriasViewModel;

public class ProductoAddFragment extends Fragment {

    private FragmentProductoAddBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentProductoAddBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_done,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}