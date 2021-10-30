package org.jbtc.gshop.db.viewmodel;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jbtc.gshop.db.GshopRoom;
import org.jbtc.gshop.db.dao.CategoriaDao;
import org.jbtc.gshop.db.dao.ProductoDao;
import org.jbtc.gshop.db.entity.Categoria;
import org.jbtc.gshop.db.entity.Producto;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoriasViewModel extends AndroidViewModel {
    private static final String TAG = "casbc";
    private final CategoriaDao categoriaDao;
    private MutableLiveData<Integer> intDelete;
    private MutableLiveData<Integer> intUpdate;

    public CategoriasViewModel(Application application) {
        super(application);
        categoriaDao = GshopRoom.getInstance(application).categoriaDao();
    }

    public Single<Categoria> getCategoriaById(long id) {
        return categoriaDao.getCategoriaById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Categoria>> getAllCategoria() {
        return categoriaDao.getAllCategoria()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public SingleSource<Integer> clearCategoria() {
        return categoriaDao.clearCategoria()
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
                    database.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'Categoria';");
                    emitter.onSuccess(true);
                } else {
                    emitter.onSuccess(false);
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    public Single<Long[]> insertCategorias(List<Categoria> categoriaList) {
        return categoriaDao.insertCategorias(categoriaList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Categoria> getCategoria(long id) {
        return categoriaDao.getCategoriaById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public void updateCategoriaForResult(Categoria categoria, String oldCategoria) {
        if (!categoria.nombre.equals(oldCategoria)) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("categorias").child(oldCategoria).get()
                    .addOnSuccessListener(cat -> {
                        Log.i(TAG, "updateCategoriaForResult: " + cat);
                        mDatabase.child("categorias").child(categoria.nombre).setValue(cat.getValue())
                                .addOnSuccessListener(unused -> {
                                    mDatabase.child("categorias").child(oldCategoria).removeValue();
                                    ProductoDao p = GshopRoom.getInstance(getApplication()).productoDao();
                                    categoriaDao.updateCategoria(categoria)
                                            .flatMap(integer -> p.updateProductoSetCategoria(oldCategoria, categoria.nombre))
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe((integer, throwable1) -> {
                                                Log.i(TAG, "onSuccess: updateCategoria(categoria) terminado");
                                                if (throwable1 == null)
                                                    intUpdate.setValue(integer);
                                                else {
                                                    intUpdate.setValue(0);
                                                    Log.e(TAG, "updateCategoriaForResult: ", throwable1);
                                                }
                                            });
                                }).addOnFailureListener(e -> intUpdate.setValue(0));
                    })
                    .addOnFailureListener(e -> intUpdate.setValue(0));
        }
    }

    public LiveData<Integer> updateCategoriaResult() {
        if (intUpdate == null)
            intUpdate = new MutableLiveData<Integer>();
        return intUpdate;
    }

    public void deleteCategoriaForResult(Categoria categoria) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("categorias").child(categoria.nombre).removeValue()
                .addOnSuccessListener(unused ->
                        categoriaDao.deleteCategoria(categoria)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .flatMap(integer -> GshopRoom.getInstance(
                                        getApplication())
                                        .productoDao()
                                        .deleteProductoByCategoria(categoria.nombre)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread()))
                                .subscribe((integer, throwable) -> {
                                    if (throwable == null) {
                                        intDelete.setValue(1);
                                    } else {
                                        intDelete.setValue(0);
                                    }
                                })).addOnFailureListener(e -> intDelete.setValue(0));
    }

    public LiveData<Integer> deleteCategoriaResult() {
        if (intDelete == null)
            intDelete = new MutableLiveData<Integer>();
        return intDelete;
    }
}
