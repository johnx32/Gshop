package org.jbtc.gshop.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.jbtc.gshop.db.entity.Producto;

import java.util.List;

@Dao
public interface ProductoDao {
    @Query("SELECT * FROM Producto")
    List<Producto> getAllProducto();
    @Insert
    void insertProducto(Producto producto);
}
