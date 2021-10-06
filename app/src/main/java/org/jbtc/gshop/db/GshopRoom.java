package org.jbtc.gshop.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import org.jbtc.gshop.db.dao.ProductoDao;
import org.jbtc.gshop.db.entity.Producto;

@Database(entities = {Producto.class}, version = 1)
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
}
