package org.jbtc.gshop.ui.categorias.categoria;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentCategoriaEditBinding;
import org.jbtc.gshop.db.entity.Categoria;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.CategoriasViewModel;
import org.jbtc.gshop.db.viewmodel.ProductosViewModel;

import io.reactivex.functions.BiConsumer;

public class CategoriaEditFragment extends Fragment {
    private static final String TAG = "crmsl";
    private FragmentCategoriaEditBinding binding;
    private CategoriasViewModel categoriasViewModel;
    private Categoria categoria;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =  FragmentCategoriaEditBinding.inflate(inflater,container,false);
        categoriasViewModel = new ViewModelProvider(this).get(CategoriasViewModel.class);
        getBundle();
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    private void getBundle() {
        Bundle b = getArguments();
        if(b!=null){
            long id = b.getLong("id");
            categoriasViewModel.getCategoriaById(id)
                    .subscribe(new BiConsumer<Categoria, Throwable>() {
                        @Override
                        public void accept(Categoria c, Throwable throwable) throws Exception {
                            if (throwable==null){
                                categoria=c;
                                setCategoriaToLayout(c);
                            }else Log.e(TAG, "accept: ",throwable );
                        }
                    });
        }
    }

    private void setCategoriaToLayout(Categoria c) {

        binding.etCatEditName.setText(c.nombre);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_op_basic,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
