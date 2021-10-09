package org.jbtc.gshop.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import org.jbtc.gshop.db.GshopRoom;
import org.jbtc.gshop.db.dao.ProductoDao;
import org.jbtc.gshop.db.entity.Producto;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProductoViewModel extends AndroidViewModel {
    ProductoDao productoDao;

    public ProductoViewModel(Application application) {
        super(application);
        productoDao = GshopRoom.getInstance(application).productoDao();
    }

    public Single<List<Producto>> getAllProducto(){
        return productoDao.getAllProducto()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<Long[]> insertProductos(List<Producto> productoList){
        return productoDao.insertProductos(productoList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
