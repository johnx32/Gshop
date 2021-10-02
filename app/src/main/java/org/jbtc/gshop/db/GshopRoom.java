package org.jbtc.gshop.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.jbtc.gshop.db.dao.ProductoDao;
import org.jbtc.gshop.db.entity.Producto;

@Database(entities = {Producto.class}, version = 1)
public abstract class GshopRoom extends RoomDatabase {
    public abstract ProductoDao productoDao();
}
