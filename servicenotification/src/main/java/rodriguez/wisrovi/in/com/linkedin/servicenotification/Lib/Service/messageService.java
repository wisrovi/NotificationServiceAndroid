



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

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.gson.Gson;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.DB.ConfigurarSocketDto;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.DB.ModeloConfiguracionDB;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Dic.DiccionarioProcesos;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Dto.ActivitySendToServiceDto;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Dto.ServiceSendToActivity;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.NoModificar.AnswerSocket;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.NoModificar.TCPSocket;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.UtilVersionAndroid.ForAndroid8.NotificationUtils;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.UtilVersionAndroid.UtilGeneral.PushDto;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.UtilVersionAndroid.UtilGeneral.Util;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.VariablesPreferenciales.SocketVariablesPreferencias;




public class messageService extends Service {

    private Context context;
    private final Messenger mMessenger = new Messenger(new IncomingHandler());
    private boolean servicioIniciado = false;
    private static int ID_SERVICIO_PRIMER_PLANO = 1000;
    private NotificationCompat.Builder builder;
    private Util util;
    private ConfigurarSocketDto datosConfiguracionAlmacenados;
    private Boolean oldEstadoConexion = null;
    private SocketVariablesPreferencias socketVariablesPreferencias;
    private String NombreProyecto = "";
    private ModeloConfiguracionDB configuracionGuardada;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcesoIncioService();
        socketVariablesPreferencias = new SocketVariablesPreferencias("inReboot", context);
        configuracionGuardada = new ModeloConfiguracionDB(context);
    }

    private void ProcesoIncioService() {
        context = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationUtils(context).BuilderAndroid8();
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        new InicializadorSocket(100, 100).start();
    }

    class InicializadorSocket extends CountDownTimer {
        public InicializadorSocket(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            procesoInicioSocket();
        }
    }


    private void rematriculaInmediata() {
        TcpSocket.ActivarRematriculaInmediata();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String imagenUsar = socketVariablesPreferencias.LeerVariable("LOGO");
        NombreProyecto = socketVariablesPreferencias.LeerVariable("ProjectName");
        new Temporizador(20, 20).start();
        if (!socketVariablesPreferencias.LeerVariable("START").equals("")) {
            String action = intent.getAction();

            if(action != null){
                switch (action) {
                    case "STOP": {
                        stopForeground(true);
                        stopSelf();
                    }
                    break;
                    case "RMAT": {
                        rematriculaInmediata();
                    }
                    break;
                }
            }else{
                boolean administrador = false;
                Notification notification;
                if(action != null){
                    if ("ADMIN".equals(action)) {
                        administrador = true;
                    }
                }
                if(!NombreProyecto.equals("")){
                    int imagen = 0;
                    if(!imagenUsar.equals("")){
                        imagen = Integer.parseInt(imagenUsar);
                    }
                    util = new Util(context, NombreProyecto, ID_SERVICIO_PRIMER_PLANO, builder,imagen);
                    notification = util.notificacionForegroundService("Servicio en ejecución.", administrador);
                    startForeground(ID_SERVICIO_PRIMER_PLANO, notification);
                    servicioIniciado = true;
                }
            }
        }
        return START_STICKY;
    }


    class Temporizador extends CountDownTimer {

        public Temporizador(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            ProcesoSocket();
            if(!servicioIniciado){
                if (!socketVariablesPreferencias.LeerVariable("START").equals("")) {
                    servicioIniciado = true;
                }
                if(Boolean.parseBoolean(socketVariablesPreferencias.LeerVariable("RematriculaInmediata"))){
                    TcpSocket.ActivarRematriculaInmediata();
                    socketVariablesPreferencias.GuardarVariableDatos("RematriculaInmediata", Boolean.toString(false));
                }
            }
            new Temporizador(1000, 1000).start();
        }
    }




    /******************* conexion Handler con otra clase **********************************/


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //return null;
        return mMessenger.getBinder();
    }

    ActivitySendToServiceDto datoDto;

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int a = msg.arg1;
            int b = msg.arg2;
            Object c = msg.obj;
            switch (msg.what) {
                case DiccionarioEventosService.BorrarPrimerPlano: {
                    servicioIniciado = false;
                    stopForeground(true);
                    stopSelf();
                }
                break;
                case DiccionarioEventosService.Mensaje: {
                    datoDto = (ActivitySendToServiceDto) c;
                    //Toast.makeText(context, datoDto.getProcess() + "->" + datoDto.getDataCompress(), Toast.LENGTH_LONG).show();
                }
                break;
                case DiccionarioEventosService.UpdatePermision: {
                    changeConfiguration();
                }
                break;
            }
        }
    }

    private void changeConfiguration(){
        ConfigurarSocketDto datosConfiguracion = configuracionGuardada.getDatosConfiguracion();
        TcpSocket.setPermisosNavegacionDatos(datosConfiguracion.isUsoDatos());
        this.datosConfiguracionAlmacenados = datosConfiguracion;
    }

    /******************* enviando message **********************/

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /***************************** socket ********************************/
    private ServiceSendToActivity sendActivity;
    private TCPSocket TcpSocket;

    private void procesoInicioSocket() {

        ConfigurarSocketDto datosConfiguracion = configuracionGuardada.getDatosConfiguracion();
        if (datosConfiguracion != null) {
            this.datosConfiguracionAlmacenados = datosConfiguracion;
            NombreProyecto = datosConfiguracion.getTituloProyecto();
                    TcpSocket = new TCPSocket(context,
                    datosConfiguracion.getIpServer(),
                    datosConfiguracion.getPuerto(),
                    datosConfiguracion.isUsoDatos(),
                    datosConfiguracion.isInfinito(),
                    datosConfiguracion.getTituloClasenvocadora(),
                    datosConfiguracion.getTituloProyecto());
        } else {
            new InicializadorSocket(30000, 30000).start();
        }
    }

    private void ProcesoSocket() {
        if (!socketVariablesPreferencias.LeerVariable("START").equals("")) {
            boolean isNoConection = false;
            if (servicioIniciado) {
                if (TcpSocket != null) {
                    if (TcpSocket.isSocketIniciado()) {
                        Boolean dispositivoMatriculado = TcpSocket.isDispositivoMatriculado();
                        if (oldEstadoConexion != dispositivoMatriculado) {
                            oldEstadoConexion = dispositivoMatriculado;
                            boolean isAdmin = Boolean.parseBoolean(socketVariablesPreferencias.LeerVariable("UsuarioAdministrador"));
                            Notification notif = util.updatePush(oldEstadoConexion, "Servicio en ejecución.", isAdmin);
                            startForeground(ID_SERVICIO_PRIMER_PLANO, notif);
                            if(dispositivoMatriculado){
                                socketVariablesPreferencias.GuardarVariableDatos("DispositivoMatriculado", "OK");
                            }else{
                                socketVariablesPreferencias.GuardarVariableDatos("DispositivoMatriculado", "");
                            }
                        }

                        //si hay socket
                        if (TcpSocket.isNewReceived()) {
                            //recibido de datos
                            TcpSocket.setNewReceived(false);
                            AnswerSocket mensajeRecibido = TcpSocket.getAnswerSocket();
                            sendActivity(mensajeRecibido.getProcess(), mensajeRecibido.getCompressedData(), mensajeRecibido.getError());
                            if (mensajeRecibido.getProcess().equals(DiccionarioProcesos.procesoSocketPush)) {
                                if (datosConfiguracionAlmacenados.isUsarPushDefault()) {
                                    PushDto pushDto = new Gson().fromJson(mensajeRecibido.getCompressedData(), PushDto.class);
                                    util.MsgPush(pushDto);
                                }
                            }
                        } else {
                            //como no se estan recibiendo datos, se aprovecha para enviar datos
                            if (TcpSocket.isEstadoConexion()) {
                                if (datoDto != null) {
                                    if (datoDto.isMsgBroadcast()) {
                                        TcpSocket.SendDataSocketBroadcast(datoDto.getProcess(), datoDto.getDataCompress());
                                    } else {
                                        TcpSocket.SendDataSocket(datoDto.getProcess(), datoDto.getDataCompress());
                                    }
                                    datoDto = null;
                                }
                            } else {
                                //aun no se pueden enviar datos, debido a que el socket aun no ha establecido conexion

                            }
                        }
                    } else {
                        //no hay socket iniciado
                        isNoConection = true;
                    }
                } else {
                    //no se pudo construir el socket
                    isNoConection = true;
                }
            } else {
                //no se ha dato inicio al servicio

            }
            if(isNoConection){
                socketVariablesPreferencias.GuardarVariableDatos("DispositivoMatriculado", "");
                if (oldEstadoConexion == null) {
                    oldEstadoConexion = true;
                }
                if (oldEstadoConexion != false) {
                    oldEstadoConexion = false;
                    Notification notif = util.updatePush(oldEstadoConexion, "Servicio en ejecución.", false);
                    startForeground(ID_SERVICIO_PRIMER_PLANO, notif);
                }
            }
        }
    }

    private void sendActivity(String proceso, String datosComprimidos, String error) {
        sendActivity = new ServiceSendToActivity();
        sendActivity.setCompressedData(datosComprimidos);
        sendActivity.setProcess(proceso);
        sendActivity.setError(error);
        socketVariablesPreferencias.GuardarVariableDatos("DATA", new Gson().toJson(sendActivity));
    }

}
