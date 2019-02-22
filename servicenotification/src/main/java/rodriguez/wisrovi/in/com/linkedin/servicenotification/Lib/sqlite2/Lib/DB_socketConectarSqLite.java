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
import android.os.Build;
import android.support.annotation.RequiresApi;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla.DB_socketColumnaCrear;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla.DB_socketTablaCrear;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla.DB_socketTipoDatoSqLite;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.ReadTable.DB_socketDatosColumna;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.ReadTable.DB_socketDatosTabla;


public class DB_socketConectarSqLite {

    private DB_socketModel model;
    private DB_socketTablaCrear tablaDto;


    public DB_socketConectarSqLite(Context context, DB_socketTablaCrear tablaDto){
        model = new DB_socketModel(context, tablaDto);
        this.tablaDto = tablaDto;
    }

    public DB_socketDatosTabla LeerTabla(){
        return model.LeerTabla();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int InsertarDatos(List<DB_socketDatosColumna> tablaLlena){
        if(model.cantidadDatosInsertar() != tablaLlena.size()){
            return -1;
            //Log.e("SQLite", "Datos incompletos para ser guardados");
        }else{
            ContentValues values = ListDtoConvertContenVelues(tablaLlena);
            if(values.size()==tablaDto.getColumnasDtos().size()){
                return model.InsertarDatos(values).intValue();
            }else{
                return -2;
            }
        }
    }

    public int ActualizarDatos(List<DB_socketDatosColumna> tablaLlena, int idFilaActualizar){
        if(model.cantidadDatosInsertar() != tablaLlena.size()){
            return -1;
            //Log.e("SQLite", "Datos incompletos para ser guardados");
        }else{
            ContentValues values = ListDtoConvertContenVelues(tablaLlena);
            if(values.size()>0){
                return model.ActualizarDatos(values, Integer.toString(idFilaActualizar));
            }

            /*if(values.size()==tablaDto.getColumnasDtos().size()){
                return model.ActualizarDatos(values, Integer.toString(idFilaActualizar));
            }else{
                return -2;
            }*/
            return -2;
        }
    }

    private ContentValues ListDtoConvertContenVelues(List<DB_socketDatosColumna> tablaLlena){
        List<DB_socketColumnaCrear> columnasDtos2 = tablaDto.getColumnasDtos();
        ContentValues values = new ContentValues();
        if(columnasDtos2.size()==tablaLlena.size()){
            for (Iterator<DB_socketDatosColumna> iterator = tablaLlena.iterator(); iterator.hasNext();) {
                DB_socketDatosColumna next = iterator.next();
                //Log.e(next.getNombreColumna(), next.getDatoColumna());
                if(next.getDatoColumna() != null){
                    for (int i = 0; i < columnasDtos2.size(); i++) {
                        DB_socketColumnaCrear next2 = columnasDtos2.get(i);
                        if(next.getNombreColumna().equals(next2.getNombreColumna())){
                            if(!next.getDatoColumna().equals("")){
                                //Log.e("ColumnaEncontrada",next2.getTipoDato());
                                switch (next2.getTipoDato()){
                                    case DB_socketTipoDatoSqLite.DatoBoolean:{
                                        values.put(next.getNombreColumna(), Boolean.parseBoolean(next.getDatoColumna()));
                                    }
                                    break;
                                    case DB_socketTipoDatoSqLite.DatoEntero:{
                                        values.put(next.getNombreColumna(), Integer.parseInt(next.getDatoColumna()));
                                    }
                                    break;
                                    case DB_socketTipoDatoSqLite.DatoString:{
                                        values.put(next.getNombreColumna(), next.getDatoColumna());
                                    }
                                    break;
                                }
                            }else{
                                //Log.e(next2.getTipoDato(),next.getDatoColumna());
                            }
                            break;
                        }
                    }
                }
            }
        }

        return  values;
    }

    public List<DB_socketDatosColumna> plantillaUsoDatos(){
        List<DB_socketDatosColumna> DBsocketDatosColumnas = new ArrayList<>();
        for (DB_socketDatosColumna DBsocketDatosColumna : model.getPlantillaManipulacionDatos()){
            DB_socketDatosColumna copy = deepCopy(DBsocketDatosColumna);
            DBsocketDatosColumnas.add(copy);
        }
        return DBsocketDatosColumnas;
    }

    private DB_socketDatosColumna deepCopy(DB_socketDatosColumna input){
        DB_socketDatosColumna copy = new DB_socketDatosColumna();
        copy.setDatoColumna(input.getDatoColumna());
        copy.setNombreColumna(input.getNombreColumna());
        return copy;
    }

}
