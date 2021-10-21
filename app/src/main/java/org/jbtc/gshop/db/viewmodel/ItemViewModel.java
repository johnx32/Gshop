package org.jbtc.gshop.db.viewmodel;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import org.jbtc.gshop.db.GshopRoom;
import org.jbtc.gshop.db.dao.ItemDao;
import org.jbtc.gshop.db.entity.Item;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ItemViewModel extends AndroidViewModel {
    private ItemDao itemDao;
    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemDao = GshopRoom.getInstance(application).itemDao();
    }

    public Single<Integer> clearItem() {
        return itemDao.clearItem()
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
                    database.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'Item';");
                    emitter.onSuccess(true);
                } else {
                    emitter.onSuccess(false);
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    public Single<Long[]> insertItems(List<Item> itemList) {
        return itemDao.insetItems(itemList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
