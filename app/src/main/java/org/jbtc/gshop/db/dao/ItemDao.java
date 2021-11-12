package org.jbtc.gshop.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.jbtc.gshop.db.entity.Item;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ItemDao {
    @Query("delete from Item")
    Single<Integer> clearItem();

    @Insert
    Single<Long[]> insetItems(List<Item> itemList);

    @Query("select id from item where keyPedido=:keyPedido")
    Single<Long[]> getItemsIdsByPedido(String keyPedido);
}
