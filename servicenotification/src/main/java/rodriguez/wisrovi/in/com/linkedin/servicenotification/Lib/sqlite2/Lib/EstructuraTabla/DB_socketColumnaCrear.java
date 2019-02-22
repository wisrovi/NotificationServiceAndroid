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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla;

public class DB_socketColumnaCrear {
    String NombreColumna;
    String TipoDato;

    public DB_socketColumnaCrear(String col, String tipo){
        this.NombreColumna = col;
        this.TipoDato = tipo;
    }

    public String getTipoDato() {
        return TipoDato;
    }

    public String getNombreColumna() {
        return NombreColumna;
    }
}
