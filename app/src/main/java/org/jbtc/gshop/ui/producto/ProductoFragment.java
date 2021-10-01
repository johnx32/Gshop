package org.jbtc.gshop.ui.producto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jbtc.gshop.databinding.FragmentProductosBinding;

public class ProductoFragment extends Fragment {

    private FragmentProductosBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
