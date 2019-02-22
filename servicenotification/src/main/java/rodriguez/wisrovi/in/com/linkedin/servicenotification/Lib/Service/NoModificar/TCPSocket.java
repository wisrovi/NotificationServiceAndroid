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


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings.Secure;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Dic.DiccionarioErrores;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Dic.DiccionarioProcesos;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.NoModificar.Dto.DiccionarioTipoNavegacion;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.NoModificar.Dto.MatriculaDTO;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.NoModificar.Dto.PinVidaDto;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.NoModificar.Dto.ProtocoloComunicacionDto;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.NoModificar.Root.TCPCliente;






/*
 *
 * en el manifiest se deben dan permisos de:
 * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 * <uses-permission android:name="android.permission.INTERNET"/>
 *
 *
 * */

public class TCPSocket extends AppCompatActivity {

    private final static int indice = 1;

    private TCPCliente mTcpClient = null;
    private connectTask conctTask = null;
    private String idThisDispositivo = "";
    private String mensajeRecibido;
    private boolean socketIniciado = false;
    private boolean newReceived = false;

    private boolean rematriculaInmediata = false;
    private Boolean dispositivoMatriculado = false;

    private Context contextConfiguradorTCPCliente;

    private String ipServidorSocket = "";
    private int puertoServidorSocket = 0;

    private boolean infinito;


    private boolean permisosNavegacionDatos = false;

    private String tituloCreadorClase;

    private boolean estadoConexion;
    private String conexionNavegacionActual;
    private String conexionNavegacionEnMemoria;

    private String ipActual;
    private String ipMemoria;

    private boolean paqueteListoEnviar = false;
    private String datosExternosEnviar = "";
    private Gson gson;
    private ProtocoloComunicacionDto protocoloComunicacionDto;

    private AnswerSocket answerSocket;
    private String keySocketActual = "";
    private CronometroRespuesta cronometroRespuesta;

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public TCPSocket(Context context, String ip, int puerto,
                     boolean permisoNavegacionDatos, boolean infinito,
                     String tituloClase, String tituloProyecto) {
        contextConfiguradorTCPCliente = context;
        idThisDispositivo = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
        cicloInfinitoSocketAutoconectable();
        ipServidorSocket = ip;
        puertoServidorSocket = puerto;
        permisosNavegacionDatos = permisoNavegacionDatos;
        this.infinito = infinito;
        this.tituloCreadorClase = tituloClase;
        gson = new Gson();

        conexionNavegacionActual = DiccionarioTipoNavegacion.NOCONEXION;
        conexionNavegacionEnMemoria = DiccionarioTipoNavegacion.NOCONEXION;

        protocoloComunicacionDto = new ProtocoloComunicacionDto();
        protocoloComunicacionDto.setEncabezado(tituloProyecto);
        protocoloComunicacionDto.setMsgError(DiccionarioErrores.msgErrorDefault);

        answerSocket = new AnswerSocket();

        ipActual = AuxSocket.getIPAddress(true);
        ipMemoria = ipActual;
        cronometroRespuesta = new CronometroRespuesta(30000,30000);
    }

    int conteo = 0;

    public void cicloInfinitoSocketAutoconectable() {
        final int retardo = indice + 1 ;
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            public void run() {
                while (infinito) {
                    try {
                        if (TengoPermisoNavegarSegunTipoConexionDatos()) {
                            if (mTcpClient == null) {
                                socketIniciado = false;
                                if (infinito) {
                                    conctTask = new connectTask();
                                    conctTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                    Thread.sleep(1000*retardo);
                                } else {
                                    break;
                                }

                                if (!infinito) {
                                    break;
                                } else {
                                    for (byte i = 0; i < 30; i++) {
                                        if (infinito) {
                                            if (mTcpClient != null) {
                                                if (mTcpClient.isEstadoConexion() && infinito) {
                                                    break;
                                                }
                                            }
                                            Thread.sleep(1000*retardo);
                                        } else {
                                            break;
                                        }
                                    }
                                    if (!infinito) {
                                        break;
                                    }
                                }
                                if (mTcpClient == null) {
                                    /*** fallo al iniciar el socket ***/
                                    Thread.sleep(1000*(retardo+5));
                                } else {
                                    if (infinito) {
                                        boolean estadoConexion = mTcpClient.isEstadoConexion();
                                        if (estadoConexion) {
                                            /*************** socket iniciado *************/
                                            socketIniciado = true;
                                            if (infinito) {
                                                Thread.sleep(1000*(retardo+3));
                                                matricular();
                                            } else {
                                                break;
                                            }
                                        } else {
                                            /*** fallo al conectar socket ***/
                                            Thread.sleep(1000*(retardo+5));
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            } else {
                                conteo++;
                                if (!infinito) {
                                    break;
                                } else {
                                    if (paqueteListoEnviar) {
                                        if(dispositivoMatriculado){
                                            paqueteListoEnviar = false;
                                            EnviarPorSocket(datosExternosEnviar);
                                        }
                                    }
                                    int limite = 60*(retardo+1);
                                    if (conteo >= limite || rematriculaInmediata) {
                                        conteo = 0;
                                        if (!infinito) {
                                            break;
                                        } else {
                                            if (mTcpClient.isEstadoConexion()) {
                                                mTcpClient.setEstadoConexion(false);
                                                if(!paqueteListoEnviar && dispositivoMatriculado){
                                                    Thread.sleep(200*(retardo+2));
                                                }
                                                if (dispositivoMatriculado) {
                                                    pinVidaServidorSocket();
                                                } else {
                                                    matricular();
                                                    if(!paqueteListoEnviar && dispositivoMatriculado){
                                                        Thread.sleep(3000*(retardo));
                                                    }
                                                }
                                                rematriculaInmediata = false;
                                            } else {
                                                mTcpClient = null;
                                                if(!paqueteListoEnviar && dispositivoMatriculado){
                                                    Thread.sleep(2000*retardo);
                                                }
                                            }
                                        }
                                    } else {
                                        if (infinito) {
                                            if(!paqueteListoEnviar && dispositivoMatriculado){
                                                Thread.sleep(1000*(retardo+2));
                                            }
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            }
                        } else {
                            if (infinito) {
                                if(!paqueteListoEnviar && dispositivoMatriculado){
                                    Thread.sleep(5000*retardo);
                                }
                                if (mTcpClient != null) {
                                    mTcpClient.setEstadoConexion(false);
                                }
                            } else {
                                break;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        if (mTcpClient != null) {
                            mTcpClient.setEstadoConexion(false);
                        }
                    }
                }
            }
        }).start();
    }


    public boolean EnviarDatosSocket(String sms) {
        if (mTcpClient != null) {
            rematriculaInmediata = !EnviarPorSocket(sms);
            return rematriculaInmediata;
        }
        return false;
    }


    public void matricular() {
        if (mTcpClient != null) {
            MatriculaDTO matriculaDto = new MatriculaDTO();
            matriculaDto.setIdDispositivo(idThisDispositivo);
            matriculaDto.setIpDispositivo(AuxSocket.getIPAddress(true));
            String matriculaComprimida = gson.toJson(matriculaDto);

            protocoloComunicacionDto.setProceso(DiccionarioProcesos.procesoSocketMatricula);
            protocoloComunicacionDto.setDatosComprimidos(matriculaComprimida);
            String datosEnviar = gson.toJson(protocoloComunicacionDto);
            datosEnviar = AuxSocket.encode(datosEnviar);
            EnviarPorSocket(datosEnviar);
        }
    }

    public void pinVidaServidorSocket() {
        if (mTcpClient != null) {
            PinVidaDto pinVidaDto = new PinVidaDto();
            pinVidaDto.setIdDispositivo(idThisDispositivo);
            pinVidaDto.setIpDispositivo(ipActual);
            pinVidaDto.setKey(keySocketActual);
            String pinComprimido = gson.toJson(pinVidaDto);

            protocoloComunicacionDto.setProceso(DiccionarioProcesos.procesoSocketPinVida);
            protocoloComunicacionDto.setDatosComprimidos(pinComprimido);
            String pinVida = gson.toJson(protocoloComunicacionDto);
            pinVida = AuxSocket.encode(pinVida);
            rematriculaInmediata = !EnviarPorSocket(pinVida);

        }
    }

    public boolean EnviarPorSocket(String datos) {
        boolean permisoHacerSolicitud = false;
        if (socketIniciado) {
            permisoHacerSolicitud = TengoPermisoNavegarSegunTipoConexionDatos();
        }

        if (permisoHacerSolicitud) {
            cronometroRespuesta.start();
            return mTcpClient.sendMessage(datos);
        }
        return true;
    }

    private class CronometroRespuesta extends CountDownTimer{
        public CronometroRespuesta(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            dispositivoMatriculado = false;
            rematriculaInmediata = true;
        }
    }

    public boolean TengoPermisoNavegarSegunTipoConexionDatos() {
        boolean permisoHacerSolicitud = false;
        if (isOnline(contextConfiguradorTCPCliente, DiccionarioTipoNavegacion.DATOS)) {
            conexionNavegacionActual = DiccionarioTipoNavegacion.DATOS;
            if (permisosNavegacionDatos) {
                permisoHacerSolicitud = true;
            }
        } else {
            if (isOnline(contextConfiguradorTCPCliente, DiccionarioTipoNavegacion.WIFI)) {
                conexionNavegacionActual = DiccionarioTipoNavegacion.WIFI;
                permisoHacerSolicitud = true;
            } else {
                conexionNavegacionActual = DiccionarioTipoNavegacion.NOCONEXION;
                permisoHacerSolicitud = false;
            }
        }

        ipActual = AuxSocket.getIPAddress(true);
        if (!ipActual.equals(ipMemoria)) {
            ipMemoria = ipActual;
            rematriculaInmediata = true;
            dispositivoMatriculado = false;
        }

        if (!conexionNavegacionActual.equals(conexionNavegacionEnMemoria)) {
            conexionNavegacionEnMemoria = conexionNavegacionActual;
            rematriculaInmediata = true;
            dispositivoMatriculado = false;
        }

        return permisoHacerSolicitud;
    }

    public static boolean isOnline(Context context, String solicitud) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        boolean conectedWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        boolean conectedDatos = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED;
        boolean conectedGeneral = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();

        if (solicitud.equals(DiccionarioTipoNavegacion.WIFI)) {
            return conectedWifi;
        } else {
            if (solicitud.equals(DiccionarioTipoNavegacion.DATOS)) {
                return conectedDatos;
            } else {
                return conectedGeneral;
            }
        }
    }


    public TCPCliente getmTcpClient() {
        return mTcpClient;
    }

    public void setmTcpClient(TCPCliente mTcpClient) {
        this.mTcpClient = mTcpClient;
    }

    public boolean isSocketIniciado() {
        return socketIniciado;
    }

    public void setSocketIniciado(boolean socketIniciado) {
        this.socketIniciado = socketIniciado;
    }

    public boolean isNewReceived() {
        return newReceived;
    }

    public void setNewReceived(boolean newReceived) {
        this.newReceived = newReceived;
    }


    public class connectTask extends AsyncTask<String, String, TCPCliente> {

        @Override
        protected TCPCliente doInBackground(String... message) {
            //creamos un objeto TCPCliente
            mTcpClient = new TCPCliente(new TCPCliente.OnMessageReceived() {
                @Override
                //aquí se implementa el método messageReceived
                public void messageReceived(String message) {
                    try {
                        //Este método llama al onProgressUpdate
                        publishProgress(message);
                        if (message != null) {
                            //System.out.println("Retornamos el mensaje del socket::::: >>>>> " + message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, ipServidorSocket, puertoServidorSocket);
            mTcpClient.run();
            if (mTcpClient != null) {
                //EnviarDatosSocket("mensaje inicial cuando conecta con el servidor");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            mensajeRecibido = values[0];//capturo los datos recibidos

            mensajeRecibido = AuxSocket.decode(mensajeRecibido);
            if(!mensajeRecibido.equals("")){
                cronometroRespuesta.cancel();
            }

            Gson gson = new Gson();
            ProtocoloComunicacionDto dtoEncontrado = gson.fromJson(mensajeRecibido, ProtocoloComunicacionDto.class);

            boolean reportarNuevoDato = true;
            if (dtoEncontrado.getProceso().equals(DiccionarioProcesos.procesoSocketMatricula) && dtoEncontrado.getMsgError().equals(DiccionarioErrores.msgErrorDefault)) {
                keySocketActual = "";
                MatriculaDTO matriculaDTO = gson.fromJson(dtoEncontrado.getDatosComprimidos(), MatriculaDTO.class);
                reportarNuevoDato = false;
                if (matriculaDTO != null) {
                    keySocketActual = matriculaDTO.getKey();
                    if (!keySocketActual.equals("")) {
                        dispositivoMatriculado = true;//levanto esta bandera cuando ser recibio una confirmacion de matricula
                    }
                }
            }

            if (dtoEncontrado.getProceso().equals(DiccionarioProcesos.procesoSocketPush)) {
                SendDataSocket(DiccionarioProcesos.procesoSocketPush, "OK");
            }

            if (dtoEncontrado.getProceso().equals(DiccionarioProcesos.procesoSocketPinVida)) {
                reportarNuevoDato = false;
                if(!dtoEncontrado.getMsgError().equals(DiccionarioErrores.msgErrorDefault)){
                    dispositivoMatriculado = false;
                    rematriculaInmediata = true;
                }
            }

            answerSocket.setCompressedData(dtoEncontrado.getDatosComprimidos());
            answerSocket.setError(dtoEncontrado.getMsgError());
            answerSocket.setProcess(dtoEncontrado.getProceso());

            newReceived = reportarNuevoDato;  //bandera para avisar a las clases externas que ha llegado un nuevo mensaje por el servidorSocket
            if (mTcpClient != null) {
                mTcpClient.setEstadoConexion(true);//EstadoConexion es un perro guardian que me chequea que exista conexion
            }
        }
    }

    public AnswerSocket getAnswerSocket() {
        return answerSocket;
    }

    public void setAnswerSocket(AnswerSocket answerSocket) {
        this.answerSocket = answerSocket;
    }

    public boolean isPermisosNavegacionDatos() {
        return permisosNavegacionDatos;
    }

    public void setPermisosNavegacionDatos(boolean permisosNavegacionDatos) {
        this.permisosNavegacionDatos = permisosNavegacionDatos;
    }

    public boolean isInfinito() {
        return infinito;
    }

    public void setInfinito(boolean infinito) {
        /***************** socket detenido *****************/
        this.infinito = infinito;
    }

    public Boolean isDispositivoMatriculado() {
        return dispositivoMatriculado;
    }

    public void setDispositivoMatriculado(boolean dispositivoMatriculado) {
        this.dispositivoMatriculado = dispositivoMatriculado;
    }

    public boolean isEstadoConexion() {
        try {
            estadoConexion = mTcpClient.isEstadoConexion();
        } catch (Exception e) {
            estadoConexion = false;
        }

        return estadoConexion;
    }

    public void setEstadoConexion(boolean estadoConexion) {
        this.estadoConexion = estadoConexion;
    }

    public void SendDataSocket(String proceso, String dtodatosComprimidosGson) {
        protocoloComunicacionDto.setProceso(proceso);
        protocoloComunicacionDto.setDatosComprimidos(dtodatosComprimidosGson);
        String datosEnviar = gson.toJson(protocoloComunicacionDto);
        datosEnviar = AuxSocket.encode(datosEnviar);
        datosExternosEnviar = datosEnviar;
        paqueteListoEnviar = true;
    }

    public void SendDataSocketBroadcast(String proceso, String dtodatosComprimidosGson) {
        protocoloComunicacionDto.setDatosComprimidos(dtodatosComprimidosGson);
        protocoloComunicacionDto.setProceso(DiccionarioProcesos.procesoSocketBroadcast);
        protocoloComunicacionDto.setMsgError(proceso);
        String datosEnviar = gson.toJson(protocoloComunicacionDto);
        datosEnviar = AuxSocket.encode(datosEnviar);
        datosExternosEnviar = datosEnviar;
        paqueteListoEnviar = true;
    }

    public boolean isRematriculaInmediata() {
        return rematriculaInmediata;
    }

    public void ActivarRematriculaInmediata() {
        dispositivoMatriculado = false;
        this.rematriculaInmediata = true;
    }
}
