package org.jbtc.gshop.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.jbtc.gshop.db.GshopRoom;
import org.jbtc.gshop.db.dao.CategoriaDao;
import org.jbtc.gshop.db.entity.Categoria;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoriaViewModel extends AndroidViewModel {
    CategoriaDao categoriaDao;

    public CategoriaViewModel(Application application) {
        super(application);
        categoriaDao = GshopRoom.getInstance(application).categoriaDao();
    }

    public Single<List<Categoria>> getAllCategoria(){
        return categoriaDao.getAllCategoria()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
