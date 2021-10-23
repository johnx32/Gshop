package org.jbtc.gshop.db.viewmodel;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jbtc.gshop.db.GshopRoom;
import org.jbtc.gshop.db.dao.CategoriaDao;
import org.jbtc.gshop.db.entity.Categoria;
import org.jbtc.gshop.db.entity.Producto;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoriasViewModel extends AndroidViewModel {
    private final CategoriaDao categoriaDao;
    private MutableLiveData<Integer> intDelete;

    public CategoriasViewModel(Application application) {
        super(application);
        categoriaDao = GshopRoom.getInstance(application).categoriaDao();
    }

    public Single<List<Categoria>> getAllCategoria(){
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
                getApplication().getApplicationContext().getDatabasePath(openHelper.getDatabaseName()),null);

        return Single.create(emitter -> {
            try {
                if (database != null) {
                    database.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'Categoria';");
                    emitter.onSuccess(true);
                }else{ emitter.onSuccess(false); }
            }catch (Exception e){emitter.onError(e);}
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
    public void deleteCategoriaForResult(Categoria categoria) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("categorias").child(categoria.nombre).removeValue()
                .addOnSuccessListener( unused ->
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
                            if (throwable == null){
                                intDelete.setValue(1);
                            }else{
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
