package org.jbtc.gshop;

import android.app.Person;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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
import androidx.room.Room;

import org.jbtc.gshop.databinding.ActivityMainBinding;
import org.jbtc.gshop.db.GshopRoom;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.viewmodel.ProductoViewModel;

import java.util.List;

import io.reactivex.functions.BiConsumer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "fkams";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        createDrawerLayout();


        /*mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("categorias").child("poleras").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });*/



        /*Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                db.productoDao().insertProducto(new Producto("des","polera",50,"url"));

                List<Producto> productoList = db.productoDao().getAllProducto();
                Log.i("TAG", "onCreate: "+productoList.get(0).getNombre());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.textviewprueba.setText( productoList.get(0).getNombre() );
                    }
                });


            }
        });
        hilo.start();*/

        ProductoViewModel productoViewModel = new ViewModelProvider(this).get(ProductoViewModel.class);
        productoViewModel.getAllProducto()
                .subscribe(new BiConsumer<List<Producto>, Throwable>() {
                    @Override
                    public void accept(List<Producto> productos, Throwable throwable) throws Exception {
                        //if(throwable==null)
                            //binding.textviewprueba.setText(productos.get(0).nombre);
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