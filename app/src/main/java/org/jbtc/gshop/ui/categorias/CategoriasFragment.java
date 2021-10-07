package org.jbtc.gshop.ui.categorias;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentCategoriasBinding;

public class CategoriasFragment extends Fragment {
    private FragmentCategoriasBinding binding;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCategoriasBinding.inflate(inflater,container,false);

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_categorias_to_categoriaEditFragment);

        return binding.getRoot();
    }

}
