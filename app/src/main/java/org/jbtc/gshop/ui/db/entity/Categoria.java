package org.jbtc.gshop.ui.db.entity;

public class Categoria {
    long id;
    String descripcion;
    String nombre;
    boolean status;

    public Categoria(long id,String descripcion, String nombre, boolean status){
        this.id=id;
        this.descripcion=descripcion;
        this.nombre=nombre;
        this.status=status;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
