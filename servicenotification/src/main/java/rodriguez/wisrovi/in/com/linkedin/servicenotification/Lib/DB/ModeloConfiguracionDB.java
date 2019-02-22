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

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;



import java.util.ArrayList;
import java.util.List;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.DB_socketConectarSqLite;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla.DB_socketColumnaCrear;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla.DB_socketTablaCrear;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.EstructuraTabla.DB_socketTipoDatoSqLite;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.ReadTable.DB_socketDatosColumna;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.ReadTable.DB_socketDatosTabla;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.sqlite2.Lib.ReadTable.DB_socketFila;



public class ModeloConfiguracionDB {
    private DB_socketTablaCrear tabla;
    private DB_socketConectarSqLite DBsocketConectarSqLite;
    private ConfigurarSocketDto configurarSocketDto;
    private List<DB_socketDatosColumna> DBsocketDatosColumnas;
    private boolean dbExiste;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ModeloConfiguracionDB(Context context, ConfigurarSocketDto configurar) {
        tabla = new DB_socketTablaCrear();
        tabla.setNombreTabla("wisrovisocket");
        this.configurarSocketDto = configurar;
        modelarDB();
        enlazarModeloConSqLite(context);
        parametriarDatosParaSqLite(configurarSocketDto);
        ProcesarConfiguarion();
    }

    public ModeloConfiguracionDB(Context context) {
        tabla = new DB_socketTablaCrear();
        modelarDB();
        tabla.setNombreTabla("wisrovisocket");
        enlazarModeloConSqLite(context);
        ConfigurarSocketDto datConfig = getDatosConfiguracion();
        this.configurarSocketDto = datConfig;
        if (configurarSocketDto != null) {
            parametriarDatosParaSqLite(configurarSocketDto);
        }
    }

    private void modelarDB() {
        List<DB_socketColumnaCrear> columnasDtos = new ArrayList<>();
        columnasDtos.add(new DB_socketColumnaCrear("ipServer", DB_socketTipoDatoSqLite.DatoString));
        columnasDtos.add(new DB_socketColumnaCrear("puertoSocket", DB_socketTipoDatoSqLite.DatoEntero));
        columnasDtos.add(new DB_socketColumnaCrear("tituloProyecto", DB_socketTipoDatoSqLite.DatoString));
        columnasDtos.add(new DB_socketColumnaCrear("usoDatos", DB_socketTipoDatoSqLite.DatoBoolean));
        columnasDtos.add(new DB_socketColumnaCrear("pushDefault", DB_socketTipoDatoSqLite.DatoBoolean));
        columnasDtos.add(new DB_socketColumnaCrear("tituloClaseInvocadora", DB_socketTipoDatoSqLite.DatoString));
        tabla.setColumnasDtos(columnasDtos);
    }

    private void enlazarModeloConSqLite(Context context) {
        DBsocketConectarSqLite = new DB_socketConectarSqLite(context, tabla);
    }

    private void parametriarDatosParaSqLite(ConfigurarSocketDto nuevosParametros) {
        DBsocketDatosColumnas = DBsocketConectarSqLite.plantillaUsoDatos();
        DBsocketDatosColumnas.get(0).setDatoColumna(nuevosParametros.getIpServer());
        DBsocketDatosColumnas.get(1).setDatoColumna(Integer.toString(nuevosParametros.getPuerto()));
        DBsocketDatosColumnas.get(2).setDatoColumna(nuevosParametros.getTituloProyecto());
        DBsocketDatosColumnas.get(3).setDatoColumna(Boolean.toString(nuevosParametros.isUsoDatos()));
        DBsocketDatosColumnas.get(4).setDatoColumna(Boolean.toString(nuevosParametros.isUsarPushDefault()));
        DBsocketDatosColumnas.get(5).setDatoColumna(nuevosParametros.getTituloClasenvocadora());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean ActualizarConfiguracion(ConfigurarSocketDto newData) {
        parametriarDatosParaSqLite(newData);
        ProcesarConfiguarion();
        return isDbExiste();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ProcesarConfiguarion() {
        if (LeerTabla().getTablaCompleta().size() >= 1) {
            ActualizarDatos();
        } else {
            InsertarDatos();
        }
        dbExiste = true;
    }

    private DB_socketDatosTabla LeerTabla() {
        return DBsocketConectarSqLite.LeerTabla();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void InsertarDatos() {
        int id = DBsocketConectarSqLite.InsertarDatos(DBsocketDatosColumnas);
        if (id > 0) {
            dbExiste = true;
        }
        dbExiste = false;
    }

    private void ActualizarDatos() {
        int id = DBsocketConectarSqLite.ActualizarDatos(DBsocketDatosColumnas, 1);
        if (id > 0) {
            dbExiste = true;
        }
        dbExiste = false;
    }

    public ConfigurarSocketDto getDatosConfiguracion() {
        ConfigurarSocketDto configuracionGuardada = new ConfigurarSocketDto();
        DB_socketDatosTabla datos = LeerTabla();
        if (datos.getTablaCompleta().size() > 0) {
            for (int indiceFilas = 0; indiceFilas < datos.getTablaCompleta().size(); indiceFilas++) {
                DB_socketFila DBsocketFila = datos.getTablaCompleta().get(indiceFilas);
                for (int indiceColumna = 0; indiceColumna < DBsocketFila.getFila().size(); indiceColumna++) {
                    DB_socketDatosColumna columnas = DBsocketFila.getFila().get(indiceColumna);
                    switch (columnas.getNombreColumna()) {
                        case "ipServer": {
                            configuracionGuardada.setIpServer(columnas.getDatoColumna());
                        }
                        break;
                        case "puertoSocket": {
                            configuracionGuardada.setPuerto(Integer.parseInt(columnas.getDatoColumna()));
                        }
                        break;
                        case "tituloProyecto": {
                            configuracionGuardada.setTituloProyecto(columnas.getDatoColumna());
                        }
                        break;
                        case "usoDatos": {
                            configuracionGuardada.setUsoDatos(Boolean.parseBoolean(columnas.getDatoColumna()));
                        }
                        break;
                        case "pushDefault": {
                            configuracionGuardada.setUsarPushDefault(Boolean.parseBoolean(columnas.getDatoColumna()));
                        }
                        break;
                    }
                }
            }
        } else {
            configuracionGuardada = null;
        }
        return configuracionGuardada;
    }

    public boolean isDbExiste() {
        return dbExiste;
    }
}
