package org.jbtc.gshop.db.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Pedido {

    @Exclude
    public String uidCliente;
    @Exclude
    public String key;
    @PrimaryKey(autoGenerate = true)
    @Exclude
    public long id;
    @Exclude
    public Boolean cancelado;
    //@PropertyName("timestamp")
    public Date timestamp;
    @Ignore
    private List<Item> carrito=new ArrayList<>();

    public Pedido() {
    }

    public Pedido(Boolean cancelado, long timestamp) {
        this.cancelado = cancelado;
        this.timestamp = new Date(timestamp);
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }

    public long getTimestamp() {
        return timestamp.getTime();
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = new Date(timestamp);
    }

    public List<Item> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<Item> carrito) {
        this.carrito = carrito;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "uidCliente='" + uidCliente + '\'' +
                ", key='" + key + '\'' +
                ", id=" + id +
                ", estado=" + cancelado +
                ", timestamp=" + timestamp +
                ", carrito=" + carrito +
                '}';
    }
}
