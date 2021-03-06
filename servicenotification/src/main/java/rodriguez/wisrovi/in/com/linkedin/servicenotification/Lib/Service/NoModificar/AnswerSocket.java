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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.NoModificar;


public class AnswerSocket {
    String proceso;
    String datosComprimidos;
    String error;

    public String getProcess() {
        return proceso;
    }

    public void setProcess(String proceso) {
        this.proceso = proceso;
    }

    public String getCompressedData() {
        return datosComprimidos;
    }

    public void setCompressedData(String datosComprimidos) {
        this.datosComprimidos = datosComprimidos;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
