package org.jbtc.gshop.ui.categorias.categoria;

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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import org.jbtc.gshop.R;
import org.jbtc.gshop.databinding.FragmentCategoriaEditBinding;
import org.jbtc.gshop.db.entity.Categoria;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.CategoriasViewModel;
import org.jbtc.gshop.ui.categorias.CategoriasFragment;

import org.jbtc.gshop.db.viewmodel.ProductosViewModel;
import org.jbtc.gshop.ui.productos.producto.ProductoEditFragment;


import io.reactivex.functions.BiConsumer;

public class CategoriaEditFragment extends Fragment {
    private static final String TAG = "crmsl";
    private FragmentCategoriaEditBinding binding;
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
        binding =  FragmentCategoriaEditBinding.inflate(inflater,container,false);

        categoriasViewModel = new ViewModelProvider(this)
                .get(CategoriasViewModel.class);

        getActivity().setTitle("Editar Categoria");
        categoriasViewModel = new ViewModelProvider(this).get(CategoriasViewModel.class);
        getBundle();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoriasViewModel.deleteCategoriaResult()
                .observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer > 0){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("La categoria se elmino exitosamente")
                                    .setTitle("Eliminar")
                                    .setPositiveButton("OK", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();

                            NavHostFragment.findNavController(CategoriaEditFragment.this)
                                    .popBackStack();
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Por un error no se pudo eliminar la categoria")
                                    .setTitle("Error")
                                    .setPositiveButton("Entendido", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();

                        }
                    }
                });

        categoriasViewModel.updateCategoriaResult()
                .observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer > 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("La categoria se actualizo exitosamente")
                            .setTitle("Update")
                            .setPositiveButton("OK", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    NavHostFragment.findNavController(CategoriaEditFragment.this).popBackStack();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Por un error no se pudo modificar la categoria")
                            .setTitle("Error")
                            .setPositiveButton("Entendido", null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }


    private void getBundle() {
        Bundle b = getArguments();
        if(b!=null){
            long id = b.getLong("id");
            categoriasViewModel.getCategoria(id)
                    .subscribe((c, throwable) -> {
                        if (throwable==null){
                            categoria=c;
                            setCategoriaToLayout(c);
                        }else Log.e(TAG, "accept: ",throwable );
                    });
        }
    }

    private void setCategoriaToLayout(Categoria c) {

        binding.etCatEditName.setText(c.nombre);
    }

    private Categoria getCategoriaFromLayout(){
        categoria.nombre = binding.etCatEditName.getText().toString();
        return  categoria;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_op_basic,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (item.getItemId()){
            case R.id.action_delete:
                    builder.setMessage("¿Esta seguro que desea eliminar esta categoria?")
                            .setTitle("Eliminacion")
                            .setNegativeButton("NO",null)
                            .setPositiveButton("SI",(dialog, which) -> categoriasViewModel.deleteCategoriaForResult(categoria) );
                    AlertDialog dialog = builder.create();
                    dialog.show();
                break;
            case R.id.action_checkout:
                // todo: revisar la ortografia de todos los mensajes dialog

                    builder.setMessage("¿Desea confirmar los cambios en la categoria?")
                            .setTitle("Actualizacion")
                            .setNegativeButton("NO",null)
                            .setPositiveButton("SI",(dialog2, which) -> {
                                String oldCategoria = categoria.nombre;
                                categoriasViewModel.updateCategoriaForResult(getCategoriaFromLayout(), oldCategoria);
                            });
                    AlertDialog dialogo = builder.create();
                    dialogo.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
