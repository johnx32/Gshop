package org.jbtc.gshop.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.jbtc.gshop.db.dao.CategoriaDao;
import org.jbtc.gshop.db.dao.PedidoDao;
import org.jbtc.gshop.db.dao.ProductoDao;
import org.jbtc.gshop.db.entity.Categoria;
import org.jbtc.gshop.db.entity.Pedido;
import org.jbtc.gshop.db.entity.Producto;
import org.jbtc.gshop.db.dao.ClienteDao;
import org.jbtc.gshop.db.entity.Cliente;

@Database(entities = {Producto.class, Categoria.class, Cliente.class, Pedido.class}, version = 1)
public abstract class GshopRoom extends RoomDatabase {

    //public static volatile GshopRoom INSTANCE;

    public static GshopRoom getInstance(Context contexto){
        /*if(INSTANCE==null){
            synchronized (GshopRoom.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(contexto.getApplicationContext(),
                            GshopRoom.class,"base.db")
                            .build();
                }
            }
        }
        return INSTANCE;*/
        return Room.databaseBuilder(contexto.getApplicationContext(),
                GshopRoom.class,"database-name")
                .build();
    }

    public abstract ProductoDao productoDao();
    public abstract CategoriaDao categoriaDao();
    public abstract ClienteDao clienteDao();
    public abstract PedidoDao pedidoDao();
}
