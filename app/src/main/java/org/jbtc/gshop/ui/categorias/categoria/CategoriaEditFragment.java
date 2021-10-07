package org.jbtc.gshop.ui.categorias.categoria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jbtc.gshop.databinding.FragmentCategoriaEditBinding;

public class CategoriaEditFragment extends Fragment {
    private FragmentCategoriaEditBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentCategoriaEditBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}
