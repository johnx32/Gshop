package org.jbtc.gshop.ui.db.entity;

public class Categorias {
    long id;
    String nombre;

    public Categorias(long id, String nombre){
        this.id=id;
        this.nombre=nombre;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
