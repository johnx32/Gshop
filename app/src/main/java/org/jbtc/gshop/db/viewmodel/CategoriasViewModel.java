package org.jbtc.gshop.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import org.jbtc.gshop.db.GshopRoom;
import org.jbtc.gshop.db.dao.CategoriasDao;
import org.jbtc.gshop.db.entity.Categorias;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CategoriasViewModel extends AndroidViewModel {
    CategoriasDao categoriasDao;

    public CategoriasViewModel(Application application) {
        super(application);
        categoriasDao = GshopRoom.getInstance(application).categoriasDao();
    }

    public Single<List<Categorias>> getAllCategorias(){
        return categoriasDao.getallCategorias()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
