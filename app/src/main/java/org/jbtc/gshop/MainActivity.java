package org.jbtc.gshop;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import org.jbtc.gshop.databinding.ActivityMainBinding;
import org.jbtc.gshop.db.entity.Categoria;
import org.jbtc.gshop.db.entity.Item;
import org.jbtc.gshop.db.entity.Pedido;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.CategoriasViewModel;
import org.jbtc.gshop.db.viewmodel.ItemViewModel;
import org.jbtc.gshop.db.viewmodel.PedidosViewModel;
import org.jbtc.gshop.db.viewmodel.ProductosViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "fkams";
    private AppBarConfiguration mAppBarConfiguration;
    private ProductosViewModel productosViewModel;
    private CategoriasViewModel categoriasViewModel;
    private PedidosViewModel pedidosViewModel;
    private ItemViewModel itemViewModel;
    private ActivityMainBinding binding;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        productosViewModel = new ViewModelProvider(this).get(ProductosViewModel.class);
        categoriasViewModel = new ViewModelProvider(this).get(CategoriasViewModel.class);
        pedidosViewModel = new ViewModelProvider(this).get(PedidosViewModel.class);
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);

        setSupportActionBar(binding.appBarMain.toolbar);

        //todo: aqui falta hacer x cosa
        createDrawerLayout();

        //getFromFirebaseDatabase();
        loadCategoriasFromFirebase();
    }


    private void loadCategoriasFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("categorias");

        myRef.get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(Task<DataSnapshot> task) {

                        if(task.isSuccessful()){
                            List<Categoria> categoriaList = new ArrayList<>();
                            List<Producto> productoList = new ArrayList<>();

                            DataSnapshot categorias = task.getResult();
                            for(DataSnapshot categoriaData : categorias.getChildren()){
                                Categoria categoria = new Categoria(categoriaData.getKey());
                                categoriaList.add(categoria);

                                for (DataSnapshot productoData : categoriaData.getChildren()){
                                    Producto p = productoData.getValue(Producto.class);
                                    p.key=productoData.getKey();
                                    p.name_categoria=categoria.nombre;
                                    productoList.add(p);
                                }
                            }

                            productosViewModel.clearProducto()
                                    .flatMap(integer -> categoriasViewModel.clearCategoria())
                                    .flatMap(integer -> categoriasViewModel.resetCounter())
                                    .flatMap(aBoolean -> productosViewModel.resetCounter())
                                    .flatMap(aBoolean -> productosViewModel.insertProductos(productoList))
                                    .flatMap(longs -> categoriasViewModel.insertCategorias(categoriaList))

                                    .subscribe();
                        }else{ /*si no hay internet no eliminara nada*/ }
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, "loadCategoriasFromFirebase no hay internet: ", e));

        DatabaseReference refClientes = database.getReference("pedidos");
        refClientes.get()
                .addOnCompleteListener( task -> {
                    if(task.isSuccessful()){
                        List<Pedido> pedidoList = new ArrayList<>();
                        List<Item> itemList = new ArrayList<>();

                        DataSnapshot dsClientes = task.getResult();
                        for(DataSnapshot dsCliente : dsClientes.getChildren()){
                            String uid = dsCliente.getKey();
                            for(DataSnapshot dsPedido : dsCliente.getChildren()){
                                String key = dsPedido.getKey();
                                Pedido pedido = dsPedido.getValue(Pedido.class);
                                pedido.uidCliente = uid;
                                pedido.key = key;
                                pedidoList.add(pedido);
                                for(Item item:pedido.getCarrito()) item.keyPedido=key;
                                itemList.addAll(pedido.getCarrito());
                            }
                        }

                        Log.i(TAG, "loadCategoriasFromFirebase: pedidoList: "+pedidoList);
                        Log.i(TAG, "loadCategoriasFromFirebase: itemList: "+itemList);
                pedidosViewModel.clearPedido()
                        .flatMap(integer -> itemViewModel.clearItem())
                        .flatMap(integer -> pedidosViewModel.resetCounter())
                        .flatMap(bool -> itemViewModel.resetCounter())
                        .flatMap(bool -> pedidosViewModel.insertPedidos(pedidoList))
                        .flatMap(longs -> itemViewModel.insertItems(itemList))
                        .subscribe();
            }
        }).addOnFailureListener(e -> Log.e(TAG, "loadCategoriasFromFirebase: ", e) );


    }

    private void getFromFirebaseDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refPoleras = database.getReference("categorias").child("poleras");
        refPoleras.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.i(TAG, "onComplete: task: "+task.getResult());
                    List<Producto> productoList = new ArrayList<>();

                    DataSnapshot dsPoleras = task.getResult();
                    for (DataSnapshot dsProducto:dsPoleras.getChildren()){
                        String key = dsProducto.getKey();
                        Producto producto = dsProducto.getValue(Producto.class);
                        producto.key = key;
                        productoList.add(producto);
                    }
                    productosViewModel.insertProductos(productoList)
                            .subscribe();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: error en peticion a firebase", e);
            }
        });

    }

    /**
     * Metodo donde se inicializa el Drawer
     * */
    private void createDrawerLayout(){
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_categorias,
                R.id.nav_productos,
                R.id.nav_pedidos)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public void setTitle(CharSequence title) {
        binding.appBarMain.toolbar.setTitle(title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}