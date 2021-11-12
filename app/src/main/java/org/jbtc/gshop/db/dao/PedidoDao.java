package org.jbtc.gshop.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.jbtc.gshop.db.entity.Pedido;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface PedidoDao {
    @Query("SELECT * FROM Pedido")
    Single<List<Pedido>> getAllPedido();

    //CREATE
    @Insert
    Single<Long[]> insertPedido(List<Pedido> pedidoList);

    //READ
    @Query("select * from Pedido where id=:id")
    Single<Pedido> getPedidoById(long id);
    @Query("select * from Pedido;")
    Single<List<Pedido>> getPedidos();

    //UPDATE
    @Update
    Single<Integer> updatePedido(Pedido pedido);

    //DELETE
    @Delete
    Single<Integer> deleteProducto(Pedido pedido);

    @Query("delete from Pedido")
    Single<Integer> clearPedido();

    @Insert
    Single<Long[]> insertPedidos(List<Pedido> pedidoList);

    @Query("select * from pedido where id=:id")
    Single<Pedido> getPedido(long id);

    //@Query("select * from pedido where id=:id")
    //Single<Pedido> getProductosByPedido(long key);
}
