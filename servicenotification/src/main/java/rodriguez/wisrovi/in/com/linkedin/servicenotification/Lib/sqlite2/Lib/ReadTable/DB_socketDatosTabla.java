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



import java.util.ArrayList;
import java.util.List;

public class DB_socketDatosTabla {
    List<DB_socketFila> tablaCompleta;

    public DB_socketDatosTabla() {
        tablaCompleta = new ArrayList<>();
    }

    public List<DB_socketFila> getTablaCompleta() {
        return tablaCompleta;
    }

    public void setTablaCompleta(List<DB_socketFila> tablaCompleta) {
        this.tablaCompleta = tablaCompleta;
    }
}
