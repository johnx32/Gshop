package org.jbtc.gshop.ui.categorias;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//<<<<<<< HEAD
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
//=======
import androidx.navigation.fragment.NavHostFragment;
//>>>>>>> devJorgeEditCategoria

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentCategoriasBinding;
import org.jbtc.gshop.adapter.CategoriasAdapter;
import org.jbtc.gshop.db.entity.Categoria;
import org.jbtc.gshop.db.viewmodel.CategoriaViewModel;

import java.util.List;

import io.reactivex.functions.BiConsumer;

public class CategoriasFragment extends Fragment {
    private FragmentCategoriasBinding binding;
    private CategoriasAdapter adapter;
    private CategoriaViewModel categoriaViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriasBinding.inflate(inflater,container,false);
        //<<<<<<< HEAD
        categoriaViewModel = new ViewModelProvider(this).get(CategoriaViewModel.class);

        initApater();

        categoriaViewModel.getAllCategoria()
                .subscribe(new BiConsumer<List<Categoria>, Throwable>() {
                    @Override
                    public void accept(List<Categoria> categorias, Throwable throwable) throws Exception {
                        if(throwable==null)
                            adapter.setItems(categorias);
                        else Log.e("TAG", "accept: Error: ", throwable);
                    }
                });
        //=======

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_nav_categorias_to_categoriaEditFragment);
        //>>>>>>> devJorgeEditCategoria

        return binding.getRoot();
    }

    private void initApater() {
        binding.rvCatList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.rvCatList.setLayoutManager(linearLayoutManager);
        adapter = new CategoriasAdapter();
        binding.rvCatList.setAdapter(adapter);
    }

}
