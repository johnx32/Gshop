package org.jbtc.gshop.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.jbtc.gshop.db.entity.Producto;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface ProductoDao {

    @Query("SELECT * FROM Producto")
    Single<List<Producto>> getAllProducto();

    @Query("select * from producto where id=:id")
    Single<Producto> getProducto(long id);

    //CREATE
    //@Insert
    //Single<Long> insertProducto(Producto producto);
    @Insert
    Single<Long[]> insertProductos(List<Producto> productoList);//long que es el nuevo rowId para el elemento insertado. Si el parámetro es un arreglo o una colección, debería mostrar long[] o List<Long>.

    //READ
    @Query("select * from Producto where id=:id")
    Single<Producto> getProductoById(long id);
    @Query("select * from Producto;")
    Single<List<Producto>> getProductos();

    //UPDATE
    @Update
    Single<Integer> updateProducto(Producto producto);//int que indica la cantidad de filas actualizadas en la base de datos.

    //DELETE
    @Delete
    Single<Integer> deleteProducto(Producto producto);//int que indica la cantidad de filas que se quitaron de la base de datos.

    @Query("delete from Producto")
    Single<Integer> clearProducto();

    @Insert
    Single<Long> insertProducto(Producto producto);
}
