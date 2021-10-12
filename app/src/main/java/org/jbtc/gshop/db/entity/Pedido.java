package org.jbtc.gshop.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

@Entity
public class Pedido {

    @PrimaryKey(autoGenerate = true)
    @Exclude
    public long id;
    @Exclude
    public Boolean estado;
    public long fecha;

    public Pedido(Boolean estado, long fecha) {
        this.estado = estado;
        this.fecha = fecha;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }
}
