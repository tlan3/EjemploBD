package mx.com.microintel.tallermisantla.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by fernandostone on 18/11/16.
 */

public class DAOProductos {

    private DBManager dbManager;
    private SQLiteDatabase db;
    private Context mContext;
    private String TAG = DAOProductos.class.getSimpleName();
    public static final String Productos = "Productos";
    private Cursor cursor;


    public DAOProductos(Context mContext) {
        this.mContext = mContext;
        //this.dbManager = new DBManager(mContext, DBManager.BD_NOMBRE, null, DBManager.BD_VERSION);
        this.OpenDB();
    }

    private void OpenDB() {
        dbManager = new DBManager(mContext, DBManager.BD_NOMBRE, null, DBManager.BD_VERSION);
        db = dbManager.getWritableDatabase();
    }


    /**
     * Cerramos el cursor de la clase
     */
    private void closeCursor(Cursor mCursor) {
        try {
            if (mCursor != null) {
                mCursor.close();
                Log.i(TAG, "cerrando conexión BD");
            }
        } catch (Exception e) {
            Log.i(TAG, "error en cierre de conexión" + e.getMessage());
        }
    }


    /**
     * devuelve el id del último registro guardado a a partir de los parámetros de entrada
     *
     * @param TABLA
     * @param columna
     * @return
     */
    public int maxID(String TABLA, String columna) {
        int id = 0;
        Cursor mCursor;
        OpenDB();
        mCursor = db.rawQuery("SELECT MAX(" + columna + ") AS " + columna + " FROM " + TABLA, new String[]{});
        if (mCursor.moveToFirst()) {
            id = mCursor.getInt(mCursor.getColumnIndex(columna));
        }
        closeCursor(mCursor);
        Log.i(TABLA, columna + " = " + id);
        return id;
    }


    public int updateProducto(Producto producto) {
        int cont;
        OpenDB();

        ContentValues contentValues = new ContentValues();
        String[] datos = producto.getValues();
        for (int i = 0; i < ProductoDef.ColsProducto.length; i++) {
            contentValues.put(ProductoDef.ColsProducto[i], datos[i]);
        }
        cont = db.update(ProductoDef.TablaNombre, contentValues, ProductoDef.idProducto+ " = ?", new String[]{String.valueOf(producto.idProducto)});
        db.close();
        return cont;
    }

    public int deleteProducto(int idProducto){
        int cont;
        OpenDB();
        cont = db.delete(ProductoDef.TablaNombre, ProductoDef.idProducto+ " = ?", new String[]{String.valueOf(idProducto)});
        db.close();
        return cont;
    }

    public int insertProducto(Producto producto) {
        producto.idProducto = maxID(Productos, ProductoDef.idProducto) + 1;
        long regId = 1;
        OpenDB();
        ContentValues contentValues = new ContentValues();
        String[] datos = producto.getValues();
        for (int i = 0; i < ProductoDef.ColsProducto.length; i++) {
            contentValues.put(ProductoDef.ColsProducto[i], datos[i]);
        }
        regId = db.insert(ProductoDef.TablaNombre, null, contentValues);
        db.close();
        return (int) regId;
    }
    public ArrayList<Producto> getProductos() {
        OpenDB();
        Cursor cursor = db.query(Productos, null, null, null, null, null, null);
        ArrayList<Producto> productos = new ArrayList<>();
        while (cursor.moveToNext()) {
            Producto producto = new Producto();
            producto.idProducto = cursor.getInt(cursor.getColumnIndex(ProductoDef.idProducto));
            producto.nombre = cursor.getString(cursor.getColumnIndex(ProductoDef.nombre));
            producto.descripcion = cursor.getString(cursor.getColumnIndex(ProductoDef.descripcion));
            producto.foto= cursor.getString(cursor.getColumnIndex(ProductoDef.foto));
            producto.categoria= cursor.getString(cursor.getColumnIndex(ProductoDef.categoria));
            producto.precio = cursor.getString(cursor.getColumnIndex(ProductoDef.precio));
            producto.inventario = cursor.getString(cursor.getColumnIndex(ProductoDef.inventario));
            productos.add(producto);
        }

        return productos;
    }

    public int insert(String TABLA, String[] columnas, String[] datos) {
        long regId = 1;
        OpenDB();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < columnas.length; i++) {
            contentValues.put(columnas[i], datos[i]);
        }
        regId = db.insert(TABLA, null, contentValues);
        db.close();
        return (int) regId;
    }

    /* Inner class that defines the table contents */
    public static class ProductoDef implements BaseColumns {
        private static String TablaNombre = "Productos";
        private static String idProducto = "idProducto";
        private static String nombre = "nombre";
        private static String descripcion = "descripcion";
        private static String foto = "foto";
        private static String categoria = "categoria";
        private static String precio = "precio";
        private static String inventario = "inventario";
        public static String[] ColsProducto = {idProducto, nombre, descripcion, foto, categoria, precio, inventario};
    }


}
