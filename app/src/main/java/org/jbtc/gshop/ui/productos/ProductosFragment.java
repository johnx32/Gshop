package org.jbtc.gshop.ui.productos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jbtc.gshop.databinding.FragmentProductosBinding;

public class ProductosFragment extends Fragment {
    private FragmentProductosBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
