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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentProductoEditBinding;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.ProductoViewModel;

import io.reactivex.functions.BiConsumer;

public class ProductoEditFragment extends Fragment {
    private static final String TAG = "crmsl";
    private FragmentProductoEditBinding binding;
    private ProductoViewModel productoViewModel;
    private Producto producto;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentProductoEditBinding.inflate(inflater,container,false);
        productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        getBundle();
        return binding.getRoot();
    }

    private void getBundle() {
        Bundle b = getArguments();
        if(b!=null){
            long id = b.getLong("id");
            productoViewModel.getProducto(id)
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
                
                break;
            case R.id.action_checkout:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
