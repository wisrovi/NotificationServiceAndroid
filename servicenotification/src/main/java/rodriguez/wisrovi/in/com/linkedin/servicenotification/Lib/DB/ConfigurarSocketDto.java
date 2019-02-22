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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.DB;



public class ConfigurarSocketDto {
    private String ipServer;
    private int puerto;
    private boolean usoDatos;
    private boolean infinito = true;
    private String tituloClasenvocadora = "ComunicationToService";
    private String tituloProyecto;
    private boolean usarPushDefault;
    private int ImagenGrande = 0;

    public String getIpServer() {
        return ipServer;
    }

    public void setIpServer(String ipServer) {
        this.ipServer = ipServer;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public boolean isUsoDatos() {
        return usoDatos;
    }

    public void setUsoDatos(boolean usoDatos) {
        this.usoDatos = usoDatos;
    }

    public boolean isInfinito() {
        return infinito;
    }

    public String getTituloClasenvocadora() {
        return tituloClasenvocadora;
    }

    public String getTituloProyecto() {
        return tituloProyecto;
    }

    public void setTituloProyecto(String tituloProyecto) {
        this.tituloProyecto = tituloProyecto;
    }

    public boolean isUsarPushDefault() {
        return usarPushDefault;
    }

    public void setUsarPushDefault(boolean usarPushDefault) {
        this.usarPushDefault = usarPushDefault;
    }

    public int getImagenGrande() {
        return ImagenGrande;
    }

    public void setImagenGrande(int imagenGrande) {
        ImagenGrande = imagenGrande;
    }
}
