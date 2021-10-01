package org.jbtc.gshop.ui.db.entity;

public class Categoria {

    long id;
    String nombre;
    String url;

    public Categoria(long id, String descripcion, String nombre, int precio, String url) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
