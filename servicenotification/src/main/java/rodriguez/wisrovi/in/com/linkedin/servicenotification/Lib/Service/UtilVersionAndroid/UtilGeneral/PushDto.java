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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.UtilVersionAndroid.UtilGeneral;


public class PushDto {
    String Sonido;
    String vibrar;
    String tituloNotificacion;
    String mensajeTicker;
    String textoNotificacion;
    String infoNotificacion;
    String nombreClaseIniciar;
    int idNotificacion = 1;
    int iconoPequeno;
    int iconoGrande;
    boolean autocancelable;

    public String getNombreClaseIniciar() {
        return nombreClaseIniciar;
    }

    public void setNombreClaseIniciar(String nombreClaseIniciar) {
        this.nombreClaseIniciar = nombreClaseIniciar;
    }

    public String getSonido() {
        return Sonido;
    }

    public void setSonido(String sonido) {
        Sonido = sonido;
    }

    public String getVibrar() {
        return vibrar;
    }

    public void setVibrar(String vibrar) {
        this.vibrar = vibrar;
    }

    public String getTituloNotificacion() {
        return tituloNotificacion;
    }

    public void setTituloNotificacion(String tituloNotificacion) {
        this.tituloNotificacion = tituloNotificacion;
    }

    public String getMensajeTicker() {
        return mensajeTicker;
    }

    public void setMensajeTicker(String mensajeTicker) {
        this.mensajeTicker = mensajeTicker;
    }

    public String getTextoNotificacion() {
        return textoNotificacion;
    }

    public void setTextoNotificacion(String textoNotificacion) {
        this.textoNotificacion = textoNotificacion;
    }

    public String getInfoNotificacion() {
        return infoNotificacion;
    }

    public void setInfoNotificacion(String infoNotificacion) {
        this.infoNotificacion = infoNotificacion;
    }

    public int getIconoGrande() {
        return iconoGrande;
    }

    public void setIconoGrande(int iconoGrande) {
        this.iconoGrande = iconoGrande;
    }

    public int getIconoPequeno() {
        return iconoPequeno;
    }

    public void setIconoPequeno(int iconoPequeno) {
        this.iconoPequeno = iconoPequeno;
    }

    public boolean isAutocancelable() {
        return autocancelable;
    }

    public void setAutocancelable(boolean autocancelable) {
        this.autocancelable = autocancelable;
    }

    public int getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(int idNotificacion) {
        this.idNotificacion = idNotificacion;
    }
}
