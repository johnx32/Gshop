package org.jbtc.gshop.db.viewmodel;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.AndroidViewModel;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import org.jbtc.gshop.db.GshopRoom;
import org.jbtc.gshop.db.dao.PedidoDao;
import org.jbtc.gshop.db.entity.Pedido;

import java.util.List;
import java.util.Optional;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PedidosViewModel extends AndroidViewModel {
    PedidoDao pedidoDao;

    public PedidosViewModel(Application application) {
        super(application);
        pedidoDao = GshopRoom.getInstance(application).pedidoDao();
    }

    public Single <List<Pedido>> getAllPedido(){
        return pedidoDao.getAllPedido()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Integer> clearPedido() {
        return pedidoDao.clearPedido()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Boolean> resetCounter() {

        SupportSQLiteOpenHelper openHelper = GshopRoom.getInstance(getApplication().getApplicationContext()).getOpenHelper();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
                getApplication().getApplicationContext().getDatabasePath(openHelper.getDatabaseName()), null);

        return Single.create(emitter -> {
            try {
                if (database != null) {
                    database.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'Pedido';");
                    emitter.onSuccess(true);
                } else {
                    emitter.onSuccess(false);
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });

    }

    public Single<Long[]> insertPedidos(List<Pedido> pedidoList) {
        return pedidoDao.insertPedidos(pedidoList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
