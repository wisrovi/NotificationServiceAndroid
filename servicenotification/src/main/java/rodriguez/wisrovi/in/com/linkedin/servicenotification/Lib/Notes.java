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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib;

import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Broadcast.inReboot;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.DB.ConfigurarSocketDto;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.ComunicationToService;


public class Notes extends AppCompatActivity {

    Control control;

    public Notes() {
        control = new Control(getApplicationContext());

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void configurar() {
        //configurar datos para sql
        ConfigurarSocketDto configurarSocketDto = new ConfigurarSocketDto();
        configurarSocketDto.setIpServer("190.131.247.226");
        configurarSocketDto.setPuerto(53000);
        configurarSocketDto.setUsarPushDefault(true);
        configurarSocketDto.setTituloProyecto("CAPERCIO");
        configurarSocketDto.setUsoDatos(true);

        control.AlmacenarDatosConfiguracion(configurarSocketDto);
    }

    public void iniciarServicio(boolean elQueIniciaElServicioEsUsuarioAdministrador) {
        //iniciar servicio
        control.StartService(elQueIniciaElServicioEsUsuarioAdministrador);
    }

    public void enviarDatosServidor(boolean esMensajeBroadcast) {
        //SendDataService
        control.sendService("PRUEBA", "123456789", esMensajeBroadcast);
    }


    class Control extends ComunicationToService {

        public Control(Context context) {
            super(context);
        }

        @Override
        public void DatosRecibidos(ContentValues data) {
            super.DatosRecibidos(data);
            String proceso = (String) data.get("P");
            String datos = (String) data.get("D");
            String error = (String) data.get("E");

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        control.desenlazar();
    }

}
