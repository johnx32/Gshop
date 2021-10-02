package org.jbtc.gshop.db.entity;

public class Categorias {
    int id;
    String nombre;

    public Categorias(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }
}
