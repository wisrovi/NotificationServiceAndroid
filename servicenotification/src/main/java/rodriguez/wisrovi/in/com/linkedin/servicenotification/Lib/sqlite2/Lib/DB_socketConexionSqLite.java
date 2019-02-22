/*
 * Copyright (c) 2019. Created by William Steve Rodriguez Villamizar.
 * Esta representación está protegida por WISROVI. Queda prohibida la reproducción  y distribución del còdigo sin permiso del autor.
 * https://www.linkedin.com/in/wisrovi-rodriguez/
 * https://github.com/wisrovi/
 * wisrovi.rodriguez@gmail.com
 * https://es.stackoverflow.com/users/85923/william-angel
 *
 *
 *
 */

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB_socketConexionSqLite extends SQLiteOpenHelper {


    private String CrearTabla;
    private String BorrarTabla;


    public DB_socketConexionSqLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                                   String crearTabla1, String BorrarTabla1) {
        super(context, name, factory, version);
        this.BorrarTabla = BorrarTabla1;
        this.CrearTabla = crearTabla1;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CrearTabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(BorrarTabla);
    }
}
