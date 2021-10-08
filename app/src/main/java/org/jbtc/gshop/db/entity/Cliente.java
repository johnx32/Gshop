package org.jbtc.gshop.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Cliente {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String key;
    public String nombre;
    public String email;
    public String url;

    public Cliente(String key, String nombre, String email, String url) {
        this.key = key;
        this.nombre = nombre;
        this.email = email;
        this.url = url;
    }
}
