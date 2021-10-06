package org.jbtc.gshop.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.jbtc.gshop.db.entity.Categorias;
import org.jbtc.gshop.db.entity.Producto;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface CategoriasDao {

    @Query("SELECT * FROM Categorias")
    Single<List<Categorias>> getallCategorias();

    //CREATE
    //@Insert
    //Single<Long> insertProducto(Producto producto);
    @Insert
    Single<Long[]> insertCategorias(List<Categorias> categoriasList);//long que es el nuevo rowId para el elemento insertado. Si el parámetro es un arreglo o una colección, debería mostrar long[] o List<Long>.

    //READ
    @Query("select * from Categorias where id=:id")
    Single<Categorias> getCategoriasById(long id);
    @Query("select * from Categorias;")
    Single<List<Categorias>> getCategorias();

    //UPDATE
    @Update
    Single<Integer> updateCategorias(Categorias categorias);//int que indica la cantidad de filas actualizadas en la base de datos.

    //DELETE
    @Delete
    Single<Integer> deleteCategorias(Categorias categorias);//int que indica la cantidad de filas que se quitaron de la base de datos.

    @Query("delete from Categorias")
    Single<Integer> clearCategorias();
}
