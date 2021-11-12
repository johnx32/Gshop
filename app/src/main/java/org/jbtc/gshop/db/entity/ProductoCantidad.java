package org.jbtc.gshop.db.entity;

import androidx.room.Embedded;

public class ProductoCantidad {
    public int cantidad;
    @Embedded public Producto producto;
}
