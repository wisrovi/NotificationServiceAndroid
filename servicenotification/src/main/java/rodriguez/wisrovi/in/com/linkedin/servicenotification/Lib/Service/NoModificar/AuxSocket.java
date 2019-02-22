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

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;


public class AuxSocket {

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
        } // for now eat exceptions
        return "";
    }

    public static String encode(String value) {
        String mensaje = value;
        byte[] data;
        try {
            data = mensaje.getBytes("UTF-8");
            String base = Base64.encodeToString(data, Base64.DEFAULT);
            byte[] strBytes = Base64.decode(base, Base64.DEFAULT);
            byte[] encoded = Base64.encode(strBytes, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
            return new String(encoded);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decode(String value) {
        byte[] data = Base64.decode(value, Base64.DEFAULT);
        try {
            String dato = new String(data, "UTF-8");
            dato = dato.replace("fcv", "");
            dato = dato.replace("tic", "");
            return dato;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean verificarPermisos(Context contexto, String permisoSolicitar) {
        String[] permissions = new String[]{permisoSolicitar};
        final int MULTIPLE_PERMISSIONS_REQUEST_CODE = 3;
        boolean validador = false;
        if (isApiMarshmallow()) {// Marshmallow+
            if (ActivityCompat.checkSelfPermission(contexto, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
                //Si alguno de los permisos no esta concedido lo solicita
                ActivityCompat.requestPermissions((Activity) contexto, permissions, MULTIPLE_PERMISSIONS_REQUEST_CODE);
            } else {//Si todos los permisos estan concedidos prosigue con el flujo normal
                validador = true;
            }
        } else { // Pre-Marshmallow tiene permisos automaticamente
            validador = true;
        }
        return validador;
    }

    public boolean isApiMarshmallow() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {// Marshmallow+
            return true;
        }
        return false;
    }

    public boolean isApiOreo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {// Marshmallow+
            return true;
        }
        return false;
    }

}
