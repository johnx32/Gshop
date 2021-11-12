package org.jbtc.gshop.db.viewmodel;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jbtc.gshop.db.GshopRoom;
import org.jbtc.gshop.db.dao.ProductoDao;
import org.jbtc.gshop.db.entity.Pedido;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.entity.ProductoCantidad;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductosViewModel extends AndroidViewModel {
    private static final String TAG = "casbc";
    private ProductoDao productoDao;
    private MutableLiveData<Integer> intUpdate;
    private MutableLiveData<Integer> intDelete;
    private MutableLiveData<Long> longCreate;


    public ProductosViewModel(Application application) {
        super(application);
        productoDao = GshopRoom.getInstance(application).productoDao();
    }

    public Single<Producto> getProducto(long id) {
        return productoDao.getProducto(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Single<List<Producto>> getAllProducto() {
        return productoDao.getAllProducto()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long[]> insertProductos(List<Producto> productoList) {
        return productoDao.insertProductos(productoList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void insertProductosForResult(Producto producto) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference cat = mDatabase.child("categorias").child(producto.name_categoria);
        String key = cat.push().getKey();
        producto.key = key;
                cat.child(key).setValue(producto)
                .addOnSuccessListener(used -> {
                    productoDao.insertProducto(producto)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((integer, throwable) -> {
                                if (throwable == null){
                                    longCreate.setValue(1L);
                                }else{
                                    longCreate.setValue(0L);
                                }
                            });
                }).addOnFailureListener(e -> longCreate.setValue(0L));
    }

    public LiveData<Long> insertProductoResult() {
        if (longCreate == null)
            longCreate = new MutableLiveData<Long>();
        return longCreate;
    }

    public void updateProductoForResult(Producto producto) {
        productoDao.getProductoById(producto.id)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe((p, throwable) -> {
                    if (throwable == null) {
                        if (p.name_categoria.equals(producto.name_categoria)) {
                            //si la categoria no cambia realiza una insersion normal
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            mDatabase.child("categorias").child(producto.name_categoria).child(producto.key).setValue(producto)
                                    .addOnSuccessListener(unused ->
                                            productoDao.updateProducto(producto)
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe((integer, throwable1) -> {
                                                        Log.i(TAG, "onSuccess: updateProducto(producto) terminado");
                                                        if (throwable1 == null)
                                                            intUpdate.setValue(integer);
                                                        else {
                                                            intUpdate.setValue(0);
                                                            Log.e(TAG, "updateProductoForResult: ", throwable1);
                                                        }
                                                    })
                                    )
                                    .addOnFailureListener(e -> intUpdate.setValue(0));
                        } else {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                            //elimino el producto
                            mDatabase.child("categorias").child(p.name_categoria).child(p.key).removeValue()
                                    .addOnSuccessListener(unused -> {
                                        productoDao.deleteProducto(p)
                                                .subscribeOn(Schedulers.io())
                                                //.observeOn(AndroidSchedulers.mainThread())
                                                .subscribe((integer, throwable1) -> {
                                                    if (throwable1 == null) {
                                                        // vuelvo a insertar en producto en una categoria distinta
                                                        mDatabase.child("categorias").child(producto.name_categoria).child(producto.key).setValue(producto)
                                                                .addOnSuccessListener(unused1 -> {
                                                                    productoDao.insertProducto(producto)
                                                                            .subscribeOn(Schedulers.io())
                                                                            .observeOn(AndroidSchedulers.mainThread())
                                                                            .subscribe((id, throwable2) -> {
                                                                                if (throwable2 == null)
                                                                                    intUpdate.setValue(1);
                                                                                else {
                                                                                    intUpdate.setValue(0);
                                                                                    Log.e(TAG, "updateProductoForResult: ", throwable2);
                                                                                }
                                                                            });
                                                                }).addOnFailureListener(e -> intUpdate.setValue(0));
                                                    } else {
                                                        intUpdate.setValue(0);
                                                        Log.e(TAG, "updateProductoForResult: ", throwable1);
                                                    }
                                                });
                                    }).addOnFailureListener(e -> intUpdate.setValue(0));
                        }
                    }

                });
    }

    public LiveData<Integer> updateProductoResult() {
        if (intUpdate == null)
            intUpdate = new MutableLiveData<Integer>();
        return intUpdate;
    }

    // DELETE
    public void deleteProductoForResult(Producto producto) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("categorias").child(producto.name_categoria).child(producto.key).removeValue()
                .addOnSuccessListener(unused -> {
                    productoDao.deleteProducto(producto)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe((integer, throwable) -> {
                                Log.i(TAG, "deleteProductoForResult integer: " + integer);
                                if (throwable == null){
                                    intDelete.setValue(1);
                                }else{
                                    intDelete.setValue(0);
                                }
                            });
                }).addOnFailureListener(e -> intDelete.setValue(0));
    }

    public LiveData<Integer> deleteProductoResult() {
        if (intDelete == null)
            intDelete = new MutableLiveData<Integer>();
        return intDelete;
    }

    public Single<Integer> clearProducto() {
        return productoDao.clearProducto()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public SingleSource<?> resetCounter() {

        SupportSQLiteOpenHelper openHelper = GshopRoom.getInstance(getApplication().getApplicationContext()).getOpenHelper();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
                getApplication().getApplicationContext().getDatabasePath(openHelper.getDatabaseName()), null);

        return Single.create(emitter -> {
            try {
                if (database != null) {
                    database.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'Producto';");
                    emitter.onSuccess(true);
                } else {
                    emitter.onSuccess(false);
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

    }

    public Single<List<ProductoCantidad>> getProductosByPedido(String keyPedido) {
        return productoDao.getProductosByPedido(keyPedido)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
