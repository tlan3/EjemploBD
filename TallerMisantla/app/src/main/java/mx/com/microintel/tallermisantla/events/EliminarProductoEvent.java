package mx.com.microintel.tallermisantla.events;

import android.view.View;

import mx.com.microintel.tallermisantla.modelo.Producto;

/**
 * Created by fernandostone on 18/11/16.
 */

public class EliminarProductoEvent {
    public View view;
    public Producto producto;
    public EliminarProductoEvent(View position, Producto producto){
        this.view = position;
        this.producto = producto;
    }
}
