package org.jbtc.gshop.ui.productos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentProductosBinding;

public class ProductosFragment extends Fragment {
    private FragmentProductosBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProductosBinding.inflate(inflater,container, false);
//        return inflater.inflate(R.layout.fragment_productos, container, false);
        return binding.getRoot();
    }
}