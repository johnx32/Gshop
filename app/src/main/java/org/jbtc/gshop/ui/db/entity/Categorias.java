package org.jbtc.gshop.ui.db.entity;

public class Categorias {
    int id;
    String nombre;

    public Categorias(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
