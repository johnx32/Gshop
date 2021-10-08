package org.jbtc.gshop.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.jbtc.gshop.db.entity.Cliente;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ClienteDao {

    @Query("select * from Cliente;")
    Single<List<Cliente>> getAllCliente();


}
