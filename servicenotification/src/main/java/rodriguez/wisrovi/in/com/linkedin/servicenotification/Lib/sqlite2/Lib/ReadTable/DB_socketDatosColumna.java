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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.ReadTable;

public class DB_socketDatosColumna {
    String nombreColumna;
    String datoColumna;

    public DB_socketDatosColumna() {
    }

    public DB_socketDatosColumna(String nombreColumna, String datoColumna) {
        this.nombreColumna = nombreColumna;
        this.datoColumna = datoColumna;
    }

    public String getNombreColumna() {
        return nombreColumna;
    }

    public String getDatoColumna() {
        return datoColumna;
    }

    public void setNombreColumna(String nombreColumna) {
        this.nombreColumna = nombreColumna;
    }

    public void setDatoColumna(String datoColumna) {
        this.datoColumna = datoColumna;
    }
}
