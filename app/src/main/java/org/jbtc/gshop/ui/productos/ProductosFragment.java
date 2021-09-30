package org.jbtc.gshop.ui.productos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import org.jbtc.gshop.databinding.FragmentProductosBinding;

public class ProductosFragment extends Fragment {
    private FragmentProductosBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductosBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}