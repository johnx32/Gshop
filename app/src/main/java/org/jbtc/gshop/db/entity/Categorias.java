package org.jbtc.gshop.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Categorias {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String nombre;

    public Categorias(int id, String nombre){

        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
