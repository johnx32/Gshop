package org.jbtc.gshop.ui.categorias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import org.jbtc.gshop.databinding.FragmentCategoriasBinding;

public class CategoriasFragment extends Fragment {
    private FragmentCategoriasBinding binding;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoriasBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

}
