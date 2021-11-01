package org.jbtc.gshop.ui.productos.producto;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.ProductosViewModel;
import org.jbtc.gshop.ui.categorias.categoria.CategoriaEditFragment;

import io.reactivex.functions.BiConsumer;
import okhttp3.internal.cache.DiskLruCache;

public class ProductoEditFragment extends Fragment {
    private static final String TAG = "crmsl";
    private FragmentProductoEditBinding binding;
    private ProductosViewModel productosViewModel;
    private Producto producto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentProductoEditBinding.inflate(inflater,container,false);
        productosViewModel = new ViewModelProvider(this).get(ProductosViewModel.class);
        getBundle();
        initLiveData();
        return binding.getRoot();
    }

    private Producto getProductoFromLayout(){
        Producto p=new Producto();
        p.id=producto.id;
        //todo:p.descripcion = binding.
        p.nombre = binding.etProdEditName.getText().toString();
        p.descripcion = binding.etProdEditDescip.getText().toString();
        p.precio = Integer.parseInt(binding.etProdEditName.getText().toString());


        return p;
    }

    private void initLiveData() {
        productosViewModel.updateProductoResult()
                .observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if(integer>0) {
                            //todo: mostrar mensaje dialo
                            NavHostFragment.findNavController(ProductoEditFragment.this)
                                    .popBackStack();
                            //else
                            //todo: mensaje de error
                        }

                    }
                });
    }

    private void getBundle() {
        Bundle b = getArguments();
        if(b!=null){
            long id = b.getLong("id");
            productosViewModel.getProducto(id)
                    .subscribe(new BiConsumer<Producto, Throwable>() {
                        @Override
                        public void accept(Producto p, Throwable throwable) throws Exception {
                            if (throwable==null){
                                producto=p;
                                setProductoToLayout(p);
                            }else Log.e(TAG, "accept: ",throwable );
                        }
                    });
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
                builder.setMessage("Esta seguro que desea eliminar el producto")
                        .setTitle("Eliminar")
                        .setNegativeButton("NO",null)
                        .setPositiveButton("SI",(dialog, which) -> {
                            producto = getProductoFromLayout();
                            productosViewModel.updateProductoForResult(producto);
                        });
                AlertDialog dialog = builder.create();
                dialog.show();


                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
