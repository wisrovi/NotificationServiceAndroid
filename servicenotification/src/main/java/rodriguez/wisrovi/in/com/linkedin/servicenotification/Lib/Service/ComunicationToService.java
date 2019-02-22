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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.google.gson.Gson;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Broadcast.UtilBroadcast;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Broadcast.inReboot;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.DB.ConfigurarSocketDto;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.DB.ModeloConfiguracionDB;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Dto.ActivitySendToServiceDto;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Dto.ServiceSendToActivity;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.VariablesPreferenciales.SocketVariablesPreferencias;



public class ComunicationToService extends Activity {

    private Context thisContext;
    private Intent intent;
    private final Messenger mMessenger = new Messenger(new IncomingHandler());
    private SocketVariablesPreferencias socketVariablesPreferencias;
    private ModeloConfiguracionDB modeloConfiguracionDB;

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int a = msg.arg1;
            int b = msg.arg2;
            Object c = msg.obj;
            switch (msg.what) {
                case DiccionarioEventosService.Mensaje: {
                    sendActivity = (ServiceSendToActivity) c;
                }
                break;
            }
        }
    }


    /********************************      constructor       **************************************************/

    public ComunicationToService(Context context) {
        this.thisContext = context;
        socketVariablesPreferencias = new SocketVariablesPreferencias("inReboot", context);
        modeloConfiguracionDB = new ModeloConfiguracionDB(thisContext);
        intent = new Intent(thisContext, messageService.class);
        enlazar();
    }


    /**********************         enlace para enviar datos al service      *************************/

    private void enlazar() {
        thisContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        iniciarCicloRecibirDatos();
    }

    Messenger mServiceMessenger = null;
    private boolean mBound;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mServiceMessenger = new Messenger(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            mServiceMessenger = null;
            mBound = false;
        }
    };

    public boolean sendService(String proceso, String dataCompress, boolean isBroadcast) {
        if(isDispositivoMatriculado()){
            if (mBound){
                ActivitySendToServiceDto sendSocket = new ActivitySendToServiceDto();
                sendSocket.setMsgBroadcast(isBroadcast);
                sendSocket.setProcess(proceso);
                sendSocket.setDataCompress(dataCompress);

                Message msg = Message.obtain(null, DiccionarioEventosService.Mensaje, sendSocket);
                try {
                    mServiceMessenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                return true;
            }else {
                return false;
            }
        }else{
            socketVariablesPreferencias.GuardarVariableDatos("RematriculaInmediata", Boolean.toString(true));
            return false;
        }
    }

    /***********************************   enlace recibir datos del service   ************************************************/

    private void iniciarCicloRecibirDatos() {
        temporizadorRecibirDatosService = new TemporizadorRecibirDatosService(1000, 1000);
        temporizadorRecibirDatosService.start();
    }

    private ServiceSendToActivity sendActivity = null;
    private TemporizadorRecibirDatosService temporizadorRecibirDatosService;

    private class TemporizadorRecibirDatosService extends CountDownTimer {
        public TemporizadorRecibirDatosService(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            if (mBound) {
                String data = socketVariablesPreferencias.LeerVariable("DATA");
                if (!data.equals("")) {
                    sendActivity = new Gson().fromJson(data, ServiceSendToActivity.class);
                    socketVariablesPreferencias.GuardarVariableDatos("DATA", "");
                }else{
                    sendActivity = null;
                }

                if (sendActivity != null) {
                    ContentValues datos = new ContentValues();
                    datos.put("P", sendActivity.getProcess());
                    datos.put("D", sendActivity.getCompressedData());
                    datos.put("E", sendActivity.getError());
                    DatosRecibidos(datos);
                    sendActivity = null;
                }
            }
            new TemporizadorRecibirDatosService(1000, 1000).start();
        }
    }

    public void DatosRecibidos(ContentValues data) {

    }

    /***********************************   terminar enlace service   ************************************************/

    public void desenlazar() {
        if (mBound) {
            thisContext.unbindService(serviceConnection);
            mBound = false;
        }
        temporizadorRecibirDatosService.cancel();
    }


    /***************************    iniciar el servicio en segundo plano     *****************************/

    public void StartService(boolean isAdmin) {
        new InicioService(1000, 1000, isAdmin).start();
    }

    private class InicioService extends CountDownTimer {

        boolean administrador = false;

        public InicioService(long millisInFuture, long countDownInterval, boolean isAdmin) {
            super(millisInFuture, countDownInterval);
            this.administrador = isAdmin;
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            if (mBound) {
                if (administrador) {
                    intent.setAction("ADMIN");
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    thisContext.startForegroundService(intent);
                } else {
                    thisContext.startService(intent);
                }
                socketVariablesPreferencias.GuardarVariableDatos("START", "OK");
            } else {
                new InicioService(1000, 1000, administrador).start();
            }
        }
    }


    /**************************  detener proceso en segundo plano      *********************************/

    public void StopService() {
        thisContext.stopService(intent);
        socketVariablesPreferencias.GuardarVariableDatos("START", "");
        if (mBound) {
            Message msg = Message.obtain(null, DiccionarioEventosService.BorrarPrimerPlano);
            try {
                mServiceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void ChangeConfiguration(){
        if (mBound) {
            Message msg = Message.obtain(null, DiccionarioEventosService.UpdatePermision);
            try {
                mServiceMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    /**************************  almacenar datos en la DB      *********************************/

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean AlmacenarDatosConfiguracion(ConfigurarSocketDto configurarSocketDto) {
        modeloConfiguracionDB = new ModeloConfiguracionDB(thisContext, configurarSocketDto);
        socketVariablesPreferencias.GuardarVariableDatos("ProjectName", configurarSocketDto.getTituloProyecto());
        socketVariablesPreferencias.GuardarVariableDatos("LOGO", Integer.toString(configurarSocketDto.getImagenGrande()));
        return modeloConfiguracionDB.isDbExiste();
    }

    public ConfigurarSocketDto getOldConfiguration(){
        return modeloConfiguracionDB.getDatosConfiguracion();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean UpdateConfigDataBase(boolean usoDatos, boolean usarPushDefault) {
        ConfigurarSocketDto configurarSocketDto = modeloConfiguracionDB.getDatosConfiguracion();
        configurarSocketDto.setUsarPushDefault(usarPushDefault);
        configurarSocketDto.setUsoDatos(usoDatos);
        ChangeConfiguration();
        return modeloConfiguracionDB.ActualizarConfiguracion(configurarSocketDto);
    }

    public void StartServiceIfThisIsClose(){
        new UtilBroadcast().StartServiceIfIsClose(thisContext);
    }

    public boolean isDispositivoMatriculado(){
        String dispositivoMatriculado = socketVariablesPreferencias.LeerVariable("DispositivoMatriculado");
        if(dispositivoMatriculado.equals("OK")){
            return true;
        }else {
            return false;
        }
    }

    public void EstablecerUsuarioComoAdministrador(boolean isAdmin){
        socketVariablesPreferencias.GuardarVariableDatos("UsuarioAdministrador", Boolean.toString(isAdmin));
    }
}

