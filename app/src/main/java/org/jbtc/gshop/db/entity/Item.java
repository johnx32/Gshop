package org.jbtc.gshop.db.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    @Exclude
    public long id;
    public int cantidad;
    public String key;
    @Exclude
    public String keyPedido;

    public Item() {
    }

    @Ignore
    public Item(int cantidad, String keyPedido) {
        this.cantidad = cantidad;
        this.keyPedido = keyPedido;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", key='" + key + '\'' +
                ", keyPedido='" + keyPedido + '\'' +
                '}';
    }
}
