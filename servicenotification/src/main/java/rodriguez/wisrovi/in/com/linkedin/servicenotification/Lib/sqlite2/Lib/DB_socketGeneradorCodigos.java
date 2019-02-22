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



import java.util.Iterator;
import java.util.List;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla.DB_socketColumnaCrear;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla.DB_socketTablaCrear;


public class DB_socketGeneradorCodigos {

    String CrearTabla = "";
    String BorrarTabla = "";
    String LeerTabla = "";

    public DB_socketGeneradorCodigos(DB_socketTablaCrear tablaDto){
        tablaDto.setNombreTabla(tablaDto.getNombreTabla().toLowerCase());

        CrearTabla = "CREATE TABLE " + tablaDto.getNombreTabla() + " ( " + " id " + " INTEGER PRIMARY KEY AUTOINCREMENT , ";
        List<DB_socketColumnaCrear> columnas = tablaDto.getColumnasDtos();
        for (Iterator<DB_socketColumnaCrear> iterator = columnas.iterator(); iterator.hasNext();) {
            DB_socketColumnaCrear next = iterator.next();
            CrearTabla = CrearTabla + next.getNombreColumna().toLowerCase() + next.getTipoDato() + ", ";

        }
        CrearTabla = CrearTabla.substring(0, CrearTabla.length()-2) + " )";

        BorrarTabla = "DROP TABLE IF EXISTS " + tablaDto.getNombreTabla();

        LeerTabla = "SELECT * FROM " + tablaDto.getNombreTabla();

    }

    public String getBorrarTabla() {
        return BorrarTabla;
    }

    public String getCrearTabla() {
        return CrearTabla;
    }

    public String getLeerTabla() {
        return LeerTabla;
    }

}
