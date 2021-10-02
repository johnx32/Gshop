package org.jbtc.gshop.db.entity;

public class Categoria {

    int id;
    String nombreCatg;
    int cantidad;

    public Categoria(int id, String nombreCatg, int cantidad) {
        this.id = id;
        this.nombreCatg = nombreCatg;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public String getNombreCatg() {
        return nombreCatg;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombreCatg(String nombreCatg) {
        this.nombreCatg = nombreCatg;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
