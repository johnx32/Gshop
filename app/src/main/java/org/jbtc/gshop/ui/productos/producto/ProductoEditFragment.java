package org.jbtc.gshop.ui.productos.producto;

import android.os.Bundle;
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
import org.jbtc.gshop.databinding.FragmentProductoEditBinding;
import org.jbtc.gshop.db.entity.Categoria;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.CategoriasViewModel;
import org.jbtc.gshop.db.viewmodel.ProductosViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProductoEditFragment extends Fragment {
    private static final String TAG = "crmsl";
    private FragmentProductoEditBinding binding;
    private ProductosViewModel productosViewModel;
    private CategoriasViewModel categoriasViewModel;
    private Producto producto;
    private List<Categoria> categoriaList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentProductoEditBinding.inflate(inflater,container,false);
        productosViewModel = new ViewModelProvider(this).get(ProductosViewModel.class);
        categoriasViewModel = new ViewModelProvider(this).get(CategoriasViewModel.class);
        getBundle();
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productosViewModel.deleteProductoResult()
                .observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer > 0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("El producto se elmino exitosamente")
                                    .setTitle("Eliminar")
                                    .setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            NavHostFragment.findNavController(ProductoEditFragment.this)
                                    .popBackStack();
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Por un error no se pudo eliminar el Producto")
                                    .setTitle("Error")
                                    .setPositiveButton("Entendido", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
        productosViewModel.updateProductoResult()
                .observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer > 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("El producto se Actualizo con exito")
                                    .setTitle("Actualizado")
                                    .setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            NavHostFragment.findNavController(ProductoEditFragment.this)
                                    .popBackStack();
                        } else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("El producto se no se Actualizo")
                                    .setTitle("Error")
                                    .setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                    }
                });
    }

    private Producto getProductoFromLayout(){

        //p.id=producto.id;
        producto.nombre = binding.etProdEditName.getText().toString();
        producto.descripcion = binding.etProdEditDescip.getText().toString();
        producto.precio = Integer.parseInt(binding.etProdEditPrecio.getText().toString());
        //todo:agregar en el XML edittext para url, agregar combobox/spinner
        //p.url = binding.imgProdEdit.getDrawable().toString();
        producto.name_categoria = categoriaList.get( binding.spProdEditCategorias.getSelectedItemPosition() ).nombre;

        return producto;
    }

    private void getBundle() {
        Bundle b = getArguments();
        if(b!=null){
            long id = b.getLong("id");
            productosViewModel.getProducto(id)
                    .flatMap(producto1 -> {
                        producto=producto1;
                        setProductoToLayout(producto1);
                        return categoriasViewModel.getAllCategoria();
                    })
                    .subscribe((categorias, throwable) -> {
                        if (throwable==null){
                            categoriaList=categorias;
                            setCategoriasToSpinner(categorias);
                        }else Log.e(TAG, "accept: ",throwable );
                    });
        }
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
            binding.spProdEditCategorias.setAdapter(adapter);

            binding.spProdEditCategorias.setSelection(categoriaSelected);
        }else{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1, new String[]{"Sin Categoria"});

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spProdEditCategorias.setAdapter(adapter);
        }
    }

    private void setProductoToLayout(Producto p) {

        binding.etProdEditName.setText( p.nombre );
        binding.etProdEditDescip.setText(p.descripcion);
        binding.etProdEditPrecio.setText(String.valueOf(p.precio));
        Picasso.get()
                .load(p.url)
                .into(binding.imgProdEdit);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_op_basic,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_delete:
                productosViewModel.deleteProductoForResult(producto);
                break;
            case R.id.action_checkout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Esta seguro que desea guardar los cambios en el producto")
                        .setTitle("Actualizar")
                        .setNegativeButton("NO",null)
                        .setPositiveButton("SI",(dialog, which) -> {
                            producto = getProductoFromLayout();
                            productosViewModel.updateProductoForResult(producto);
//                            productosViewModel.insertProductosForResult(producto);
                        });
                AlertDialog dialog = builder.create();
                dialog.show();


                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
