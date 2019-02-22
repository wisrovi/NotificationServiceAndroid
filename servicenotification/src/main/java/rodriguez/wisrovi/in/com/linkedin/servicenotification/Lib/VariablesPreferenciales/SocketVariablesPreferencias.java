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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.VariablesPreferenciales;

import android.content.Context;
import android.content.SharedPreferences;



public class SocketVariablesPreferencias {

    String nombreArchivo;
    Context thisContex;

    public SocketVariablesPreferencias(String nombreArchivo, Context thisContex) {
        this.nombreArchivo = nombreArchivo;
        this.thisContex = thisContex;
    }

    //************************************** preferencias de usuario  ***************
    public void GuardarVariableDatos(String variable, String valor) {
        SharedPreferences misPreferencias = thisContex.getSharedPreferences(
                nombreArchivo
                , thisContex.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = misPreferencias.edit();
        editor.putString(variable, valor);
        editor.commit();

    }

    public String LeerVariable(String variable) {
        SharedPreferences misPreferencias =
                thisContex.getSharedPreferences(
                        nombreArchivo
                        , thisContex.MODE_PRIVATE
                );
        return misPreferencias.getString(variable, "");
    }
}
