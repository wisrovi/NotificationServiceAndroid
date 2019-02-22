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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla.DB_socketColumnaCrear;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla.DB_socketTablaCrear;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla.DB_socketTipoDatoSqLite;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.ReadTable.DB_socketDatosColumna;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.ReadTable.DB_socketDatosTabla;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.ReadTable.DB_socketFila;


public class DB_socketModel {
    private DB_socketConexionSqLite conexionSQLiteDBsocket;
    private Context context;
    private DB_socketTablaCrear tablaDto;


    private String CrearTabla;
    private String BorrarTabla;
    private String LeerTabla;

    private static List<DB_socketDatosColumna> DBsocketDatosColumnas;


    public DB_socketModel(Context contexto, DB_socketTablaCrear tablaDto1) {
        this.context = contexto;
        this.tablaDto = tablaDto1;

        DB_socketGeneradorCodigos codigos = new DB_socketGeneradorCodigos(tablaDto);
        CrearTabla = codigos.getCrearTabla();
        BorrarTabla = codigos.getBorrarTabla();
        LeerTabla = codigos.getLeerTabla();

        conexionSQLiteDBsocket = new DB_socketConexionSqLite(context, "bd_" + tablaDto.getNombreTabla(), null, 1,
                CrearTabla, BorrarTabla);
        plantillaDatos();
    }

    private void plantillaDatos(){
        DBsocketDatosColumnas = new ArrayList<>();
        for (Iterator<DB_socketColumnaCrear> iterator = tablaDto.getColumnasDtos().iterator(); iterator.hasNext();) {
            DB_socketColumnaCrear next = iterator.next();
            DBsocketDatosColumnas.add(new DB_socketDatosColumna(next.getNombreColumna(), null));
        }
    }

    private DB_socketConexionSqLite CrearConexion(){
        DB_socketConexionSqLite cSqL = new DB_socketConexionSqLite(context, "bd_" + tablaDto.getNombreTabla(), null, 1,
                CrearTabla, BorrarTabla);
        return cSqL;
    }

    private SQLiteDatabase iniciarLecturaDB() {
        conexionSQLiteDBsocket = CrearConexion();
        SQLiteDatabase db = conexionSQLiteDBsocket.getReadableDatabase();
        return db;
    }

    private SQLiteDatabase iniciarEscrituraDB() {
        conexionSQLiteDBsocket = CrearConexion();
        SQLiteDatabase db = conexionSQLiteDBsocket.getWritableDatabase();
        return db;
    }

    public DB_socketDatosTabla LeerTabla(){
        SQLiteDatabase db = iniciarLecturaDB();
        Cursor cursor = db.rawQuery(LeerTabla, null);
        List<ContentValues> datos = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            ContentValues valoresColumna = new ContentValues();
            valoresColumna.put("id", cursor.getInt(0));
            int columnaLeida = 1;
            for (Iterator<DB_socketColumnaCrear> iterator = tablaDto.getColumnasDtos().iterator(); iterator.hasNext();) {
                DB_socketColumnaCrear next = iterator.next();
                switch (next.getTipoDato()){
                    case DB_socketTipoDatoSqLite.DatoBoolean:{
                        valoresColumna.put(next.getNombreColumna(), cursor.getInt(columnaLeida));
                    }
                    break;
                    case DB_socketTipoDatoSqLite.DatoEntero:{
                        valoresColumna.put(next.getNombreColumna(), cursor.getInt(columnaLeida));
                    }
                    break;
                    case DB_socketTipoDatoSqLite.DatoString:{
                        valoresColumna.put(next.getNombreColumna(), cursor.getString(columnaLeida));
                    }
                    break;
                }
                columnaLeida++;
            }
            datos.add(valoresColumna);
        }
        cursor.close();
        db.close();

        DB_socketDatosTabla DBsocketDatosTabla = new DB_socketDatosTabla();
        List<DB_socketFila> DBsocketFilas = DBsocketDatosTabla.getTablaCompleta();
        for (int i=0; i<datos.size();i++){
            ContentValues values = datos.get(i);
            DB_socketFila DBsocketFilaDto = new DB_socketFila();
            List<DB_socketDatosColumna> fila = DBsocketFilaDto.getFila();
            fila.add(new DB_socketDatosColumna("Id",Integer.toString(i + 1)));
            for (Iterator<DB_socketColumnaCrear> iterator = tablaDto.getColumnasDtos().iterator(); iterator.hasNext();) {
                DB_socketColumnaCrear next = iterator.next();
                DB_socketDatosColumna columnaDto = new DB_socketDatosColumna();
                columnaDto.setNombreColumna(next.getNombreColumna());
                Object o = values.get(next.getNombreColumna());
                switch (next.getTipoDato()){
                    case DB_socketTipoDatoSqLite.DatoBoolean:{
                        int dato = (int) o;
                        if(dato==1){
                            columnaDto.setDatoColumna(Boolean.toString(true));
                        }else{
                            columnaDto.setDatoColumna(Boolean.toString(false));
                        }
                    }
                    break;
                    case DB_socketTipoDatoSqLite.DatoEntero:{
                        int dato = (int) o;
                        columnaDto.setDatoColumna(Integer.toString(dato));
                    }
                    break;
                    case DB_socketTipoDatoSqLite.DatoString:{
                        String dato = (String) o;
                        columnaDto.setDatoColumna(dato);
                    }
                    break;
                }
                fila.add(columnaDto);
            }
            DBsocketFilaDto.setFila(fila);
            DBsocketFilas.add(DBsocketFilaDto);
        }
        DBsocketDatosTabla.setTablaCompleta(DBsocketFilas);
        
        
        return DBsocketDatosTabla;
    }

    public Long InsertarDatos(ContentValues valoresInsertar){
        SQLiteDatabase db = iniciarEscrituraDB();
        Long idInsertado = db.insert(tablaDto.getNombreTabla(), " id ", valoresInsertar);
        db.close();
        if (idInsertado > 0) {
            //Log.e("SQLite", "Datos Guardados");
            return idInsertado;
        }
        //Log.e("SQLite", "Error Guardar datos");
        return new Long(0);
    }

    public int ActualizarDatos(ContentValues values, String idFilaActualizar){
        SQLiteDatabase db = iniciarEscrituraDB();
        int update = db.update(tablaDto.getNombreTabla(), values, " id = " + idFilaActualizar, null);
        db.close();
        if (update > 0) {
            //Log.e("SQLite", "Datos Guardados");
            return update;
        }
        //Log.e("SQLite", "Error Guardar datos");
        return -1;
    }


    public List<DB_socketDatosColumna> getPlantillaManipulacionDatos() {
        return DBsocketDatosColumnas;
    }

    public int cantidadDatosInsertar(){
        return tablaDto.getColumnasDtos().size();
    }
}
