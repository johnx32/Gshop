package org.jbtc.gshop.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentHomeBinding;
import org.jbtc.gshop.interfaz.icomunicaFragments;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        final TextView textView = binding.tvMenuName;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);
        binding.btnGoCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.categoriasFragment);
                }
        });

        binding.btnGoProductos.setOnClickListener(v ->
                navController.navigate(R.id.productosFragment
                ));

        binding.btnGoPedidos.setOnClickListener(v ->
                navController.navigate(R.id.pedidosFragment
                ));
    }
    //TODO: COMUNICARSE CON EL ICOMUNICAFRAGTMENT Y HOMEFRAGMENT XML


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}