package org.jbtc.gshop.ui.categorias.categoria;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentCategoriaAddBinding;
import org.jbtc.gshop.databinding.FragmentProductoAddBinding;
import org.jbtc.gshop.db.entity.Categoria;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.CategoriasViewModel;
import org.jbtc.gshop.db.viewmodel.ProductosViewModel;
import org.jbtc.gshop.ui.productos.producto.ProductoAddFragment;

import java.util.List;

public class CategoriaAddFragment extends Fragment {

    private static final String TAG = "crmsl";
    private FragmentCategoriaAddBinding binding;
    private CategoriasViewModel categoriasViewModel;
    private Categoria categoria;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentCategoriaAddBinding.inflate(inflater,container,false);
        categoriasViewModel = new ViewModelProvider(this).get(CategoriasViewModel.class);

        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLiveData();
        binding.etCatAddName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforeTextChanged: before "+s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged:after  s: "+s.toString());
            }
        });
    }

    private Categoria getCategoriaFromLayout() {
        Categoria c = new Categoria();
        c.nombre = binding.etCatAddName.getText().toString();

        return c;
    }

    private void initLiveData() {
        categoriasViewModel.insertCategoriaResult()
                .observe(getViewLifecycleOwner(), new Observer<Long>() {
                    @Override
                    public void onChanged(Long long1) {
                        if(long1>0) {
                            NavHostFragment.findNavController(CategoriaAddFragment.this)
                                    .popBackStack();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("La Categoria se ha insertado con éxito")
                                    .setTitle("Insertar")
                                    .setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("No se pudo insertar la Categoría")
                                    .setTitle("Insertar")
                                    .setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_done,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_checkout: AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Esta seguro que desea agregar la Categoría?")
                        .setTitle("Agregar")
                        .setNegativeButton("NO", null)
                        .setPositiveButton("SI",(dialog, which) -> {
                            categoria = getCategoriaFromLayout();
                            categoriasViewModel.insertCategoriaResult(categoria);
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}