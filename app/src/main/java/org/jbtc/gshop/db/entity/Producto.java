package org.jbtc.gshop.db.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

@Entity
public class Producto {
    @PrimaryKey(autoGenerate = true)
    @Exclude
    public long id;
    @Exclude
    public String key;
    public String descripcion;
    public String nombre;
    public int precio;
    public String url;
    @Exclude
    public String name_categoria;

    public Producto() {
    }

    public Producto(String descripcion, String nombre, int precio, String url) {

        this.descripcion = descripcion;
        this.nombre = nombre;
        this.precio = precio;
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
