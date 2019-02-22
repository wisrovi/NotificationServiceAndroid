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


import java.util.List;

public class DB_socketTablaCrear {
    String nombreTabla;
    List<DB_socketColumnaCrear> columnasDtos;

    public List<DB_socketColumnaCrear> getColumnasDtos() {
        return columnasDtos;
    }

    public void setColumnasDtos(List<DB_socketColumnaCrear> columnasDtos) {
        this.columnasDtos = columnasDtos;
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public void setNombreTabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
    }
}
