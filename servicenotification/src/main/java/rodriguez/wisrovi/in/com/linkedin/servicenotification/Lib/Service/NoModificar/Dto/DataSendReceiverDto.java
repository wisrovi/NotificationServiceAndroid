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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.NoModificar.Dto;

public class DataSendReceiverDto {
    String proceso;
    String datosComprimidos;
    String error;

    public DataSendReceiverDto(String proceso, String datosComprimidos, String error) {
        this.proceso = proceso;
        this.datosComprimidos = datosComprimidos;
        this.error = error;
    }

    public String getProceso() {
        return proceso;
    }

    public void setProceso(String proceso) {
        this.proceso = proceso;
    }

    public String getDatosComprimidos() {
        return datosComprimidos;
    }

    public void setDatosComprimidos(String datosComprimidos) {
        this.datosComprimidos = datosComprimidos;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
