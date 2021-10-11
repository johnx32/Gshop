package org.jbtc.gshop.db.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import org.jbtc.gshop.db.GshopRoom;
import org.jbtc.gshop.db.dao.PedidoDao;
import org.jbtc.gshop.db.entity.Pedido;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PedidoViewModel extends AndroidViewModel {
    PedidoDao pedidoDao;

    public PedidoViewModel(Application application) {
        super(application);
        pedidoDao = GshopRoom.getInstance(application).pedidoDao();
    }

    public Single <List<Pedido>> getAllPedido(){
        return pedidoDao.getAllPedido()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
