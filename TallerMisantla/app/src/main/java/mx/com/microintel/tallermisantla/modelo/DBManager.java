package mx.com.microintel.tallermisantla.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by fernandostone on 18/11/16.
 */

public class DBManager extends SQLiteOpenHelper {
    private String TAG = DBManager.class.getSimpleName();
    public static final String BD_NOMBRE = "DBVerify";
    public static final int BD_VERSION = 1;

    ArrayList<String> alQuery = new ArrayList<String>();

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        alQuery.add("CREATE TABLE Productos (idProducto INTEGER PRIMARY KEY  NOT NULL, nombre TEXT, " +
                "descripcion TEXT, foto TEXT, categoria TEXT, precio REAL, inventario INTEGER)");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate db=" + db);
        for (int i = 0; i < alQuery.size(); i++) {
            db.execSQL(alQuery.get(i));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        Log.i(TAG, "onUpgrade db=" + db.getPath());
        alQuery.add(0, "DROP TABLE IF EXISTS Productos;");
        for (int i = 0; i < alQuery.size(); i++) {
            db.execSQL(alQuery.get(i));
        }
    }


//
}
