package org.jbtc.gshop.ui.productos.producto;

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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentCategoriaEditBinding;
import org.jbtc.gshop.databinding.FragmentProductoAddBinding;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.CategoriasViewModel;
import org.jbtc.gshop.db.viewmodel.ProductosViewModel;
import org.jbtc.gshop.ui.categorias.categoria.CategoriaEditFragment;

import io.reactivex.functions.BiConsumer;

public class ProductoAddFragment extends Fragment {

    private FragmentProductoAddBinding binding;
    private Producto producto;
    private ProductosViewModel productosViewModel;
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
    }

    private Producto getProductoFromLayout() {
        Producto p = new Producto();
        p.nombre = binding.etProdAddName.getText().toString();
        p.descripcion = binding.etProdAddDescip.getText().toString();
        p.precio = Integer.parseInt(binding.etProdAddPrecio.getText().toString());
        p.url = binding.etProdAddUrl.getText().toString();
        p.name_categoria = "ropa";

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
                            builder.setMessage("el Producto se ha insertado con exito")
                                    .setTitle("Insertar")
                                    .setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("No se pudo insertar el producto")
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
                builder.setMessage("Esta seguro que desea agregar el producto")
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