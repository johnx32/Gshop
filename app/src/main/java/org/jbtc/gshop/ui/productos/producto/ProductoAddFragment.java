package org.jbtc.gshop.ui.productos.producto;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.squareup.picasso.Picasso;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentProductoAddBinding;
import org.jbtc.gshop.db.entity.Categoria;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.CategoriasViewModel;
import org.jbtc.gshop.db.viewmodel.ProductosViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductoAddFragment extends Fragment {

    private FragmentProductoAddBinding binding;
    private Producto producto;
    private ProductosViewModel productosViewModel;
    private CategoriasViewModel categoriasViewModel;
    private List<Categoria> categoriaList;
    private static final String TAG = "crmsl";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentProductoAddBinding.inflate(inflater,container,false);
        productosViewModel = new ViewModelProvider(this).get(ProductosViewModel.class);
        categoriasViewModel = new ViewModelProvider(this).get(CategoriasViewModel.class);

        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLiveData();
        binding.etProdAddUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforeTextChanged: before "+s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: text "+s);
                if(s!=null && s.length()>0)
                Picasso.get()
                        .load(s.toString())
                        .into(binding.imgProdEdit);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged:after  s: "+s.toString());
            }
        });

        categoriasViewModel.getAllCategoria()
                .subscribe((categorias, throwable) -> {
                    if(throwable==null){
                        categoriaList=categorias;
                        setCategoriasToSpinner(categorias);
                    }
                });
    }

    private Producto getProductoFromLayout() {
        Producto p = new Producto();
        p.nombre = binding.etProdAddName.getText().toString();
        p.descripcion = binding.etProdAddDescip.getText().toString();
        p.precio = Integer.parseInt(binding.etProdAddPrecio.getText().toString());
        p.url = binding.etProdAddUrl.getText().toString();
        p.name_categoria =  categoriaList.get( binding.spProdAddCategorias.getSelectedItemPosition() ).nombre;

        return p;
    }

    private void initLiveData() {
        productosViewModel.insertProductoResult()
                .observe(getViewLifecycleOwner(), new Observer<Long>() {
                    @Override
                    public void onChanged(Long long1) {
                        if(long1>0) {
                            NavHostFragment.findNavController(ProductoAddFragment.this)
                                    .popBackStack();

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("El Producto se ha insertado con éxito")
                                    .setTitle("Insertar")
                                    .setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("No se pudo insertar el Producto")
                                    .setTitle("Insertar")
                                    .setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                });
    }

    private void setCategoriasToSpinner(List<Categoria> categorias) {
        if(categorias.size()>0) {
            List<String> categorias_name = new ArrayList<>();

            int categoriaSelected = 0;
            for (int i = 0; i < categorias.size(); i++) {
                categorias_name.add(categorias.get(i).nombre);
                if (producto!=null &&
                        producto.name_categoria.equals(categorias.get(i).nombre))
                    categoriaSelected = i;
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1, categorias_name);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spProdAddCategorias.setAdapter(adapter);

            binding.spProdAddCategorias.setSelection(categoriaSelected);
        }else{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1, new String[]{"Sin Categoria"});

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spProdAddCategorias.setAdapter(adapter);
        }
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
                builder.setMessage("¿Esta seguro que desea agregar el Producto?")
                        .setTitle("Agregar")
                        .setNegativeButton("NO", null)
                        .setPositiveButton("SI",(dialog, which) -> {
                            producto = getProductoFromLayout();
                            productosViewModel.insertProductosForResult(producto);
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}