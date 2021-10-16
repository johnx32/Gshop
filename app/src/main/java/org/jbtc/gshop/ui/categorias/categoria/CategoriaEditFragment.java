package org.jbtc.gshop.ui.categorias.categoria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentCategoriaEditBinding;

public class CategoriaEditFragment extends Fragment {
    private FragmentCategoriaEditBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentCategoriaEditBinding.inflate(inflater,container,false);
        getActivity().setTitle("Editar Categoria");
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
        inflater.inflate(R.menu.menu_op_basic,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
