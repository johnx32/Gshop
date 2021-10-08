package org.jbtc.gshop.db.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import org.jbtc.gshop.db.GshopRoom;
import org.jbtc.gshop.db.dao.ClienteDao;
import org.jbtc.gshop.db.entity.Cliente;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ClienteViewModel extends AndroidViewModel {

    ClienteDao clienteDao;

    public ClienteViewModel(Application application) {
        super(application);
        clienteDao = GshopRoom.getInstance(application).clienteDao();
    }

    public Single<List<Cliente>> getAllCliente(){
        return clienteDao.getAllCliente()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
