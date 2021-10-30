package org.jbtc.gshop.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.jbtc.gshop.db.entity.Categoria;
import org.jbtc.gshop.db.entity.Producto;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CategoriaDao {
    @Query("SELECT * FROM Categoria")
    Single<List<Categoria>> getAllCategoria();

    //CREATE
    @Insert
    Single<Long[]> insertCategorias(List<Categoria> categoriaList);

    //READ
    @Query("select * from Categoria where id=:id")
    Single<Categoria> getCategoriaById(long id);

    @Query("select * from Categoria;")
    Single<List<Categoria>> getCategoria();

    //UPDATE
    @Update
    Single<Integer> updateCategoria(Categoria categoria);

    //DELETE
    @Delete
    Single<Integer> deleteCategoria(Categoria categoria);

    @Query("delete from Categoria")
    Single<Integer> clearCategoria();
}
