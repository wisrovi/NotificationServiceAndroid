

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.NoModificar.Root;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Created by Jim Soria on 27/06/2016.
 */
public class TCPCliente {

    private String serverMessage;
    /**
     * Specify the Server Ip Address here. Whereas our Socket Server is started.
     */
    //Ponemos la IP del servidor donde el Socket se a iniciado.
    public String SERVERIP; //IP de tu Computadora.............  "192.168.1.7";//
    public static int SERVERPORT; // el mismo puerto la que tiene el servidor
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;
    private boolean estadoConexion = false;

    private PrintWriter out = null;
    private BufferedReader in = null;

    /**
     * Constructor de la clase. OnMessagedReceived Escucha los mensajes resibidos desde el servidor
     */
    public TCPCliente(final OnMessageReceived listener, String ipServidor, int puertoServidor) {
        mMessageListener = listener;
        this.SERVERIP = ipServidor;
        this.SERVERPORT = puertoServidor;
    }

    public TCPCliente(final OnMessageReceived listener) {
        mMessageListener = listener;
    }

    /**
     * Enviamos los mensajes del cliente al servidor
     *
     * @param message textos del cliente
     */
    public boolean sendMessage(String message) {
        if (out != null && !out.checkError()) {
            //System.out.println("message to " + SERVERIP + ":" + Integer.toString(SERVERPORT) + "=" + message);
            out.println(message);
            out.flush();
            return true;
        } else {
            estadoConexion = false;
            return false;
        }
    }

    public boolean sendMessage(Object message) {
        if (out != null && !out.checkError()) {
            //System.out.println("message to " + SERVERIP + ":" + Integer.toString(SERVERPORT) + "=" + message);
            out.println(message);
            out.flush();
            return true;
        } else {
            estadoConexion = false;
            return false;
        }
    }


    public void stopClient() {
        mRun = false;
        System.out.print("conexion perdida");
    }

    public void run() {

        mRun = true;

        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            //System.out.print("Conexion iniciada");

            //Creamos un socket y hacemos la coneccion con el servidor
            Socket socket = new Socket(serverAddr, SERVERPORT);
            try {

                //Enviamos los mensajes al servidor
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);


                estadoConexion = true;

                //recibimos el mensaje que el servidor envía de vuelta
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                //Aquí, mientras que el cliente escucha los mensajes enviados por el servidor
                //Leemos las líneas
                while (mRun) {
                    if (socket != null) {
                        if (socket.isConnected()) {
                            if (in != null) {
                                serverMessage = in.readLine();

                                if (serverMessage != null && mMessageListener != null) {
                                    //llamamos al metodo messageReceived de la clase MyActivity
                                    mMessageListener.messageReceived(serverMessage);
                                }
                                serverMessage = null;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Socket debe ser cerrado. No es posible conectar a este Socket
                // after it is closed, which means a new socket instance has to be created.
                //después de que se cierra,esto significa que una nueva instancia Socket o Zócalo tiene que ser creado.
                estadoConexion = false;
                socket.close();
            }
        } catch (Exception e) {
            estadoConexion = false;
            stopClient();
        }

    }

    //Declaramos las interfacez. El Método messageReceived(String message) se deben implementar en el MyActivity
    //clase en el asynckTask doInBackground
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }

    public boolean isEstadoConexion() {
        return estadoConexion;
    }

    public void setEstadoConexion(boolean estadoConexion) {
        this.estadoConexion = estadoConexion;
    }


}