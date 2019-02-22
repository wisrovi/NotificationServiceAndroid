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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Broadcast;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.messageService;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.VariablesPreferenciales.SocketVariablesPreferencias;



public class UtilBroadcast {
    public boolean isMyServiceRunning(@NonNull Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }

        List<String> serviciosActivos = listaServiciosActivos(context);
        for (Iterator<String> iterator = serviciosActivos.iterator(); iterator.hasNext(); ) {
            String contenido = iterator.next();
            if (contenido.equals(serviceClass.getName())) {
                return true;
            }
        }

        return false;
    }

    private boolean isMyServiceRunning2(Class<NotificationListenerService> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public List<String> listaServiciosActivos(Context context) {
        List<String> serviciosActivos = new ArrayList<>();

        List<ActivityManager.RunningServiceInfo> lista = getServiceInfo(context);
        for (Iterator<ActivityManager.RunningServiceInfo> iterator = lista.iterator(); iterator.hasNext(); ) {
            ActivityManager.RunningServiceInfo contenido = iterator.next();
            String className = contenido.service.getClassName().toString();
            serviciosActivos.add(className);
        }
        return serviciosActivos;
    }

    public boolean serviceIsRunningInForeground(@NonNull Class<?> serviceClass, Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (getClass().getName().equals(serviceClass.getName())) {
                if (service.foreground) {
                    return true;
                }
            }
        }
        return false;
    }


    private List<ActivityManager.RunningServiceInfo> getServiceInfo(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        Integer valor = Integer.MAX_VALUE;  //200 -> servicios activos, 40 -> servicios trabajando
        List<ActivityManager.RunningServiceInfo> serviceInfoList = manager.getRunningServices(valor);
        if (serviceInfoList.size() > 0) {
            return serviceInfoList;
        }
        return null;
    }

    public void StartServiceIfIsClose(Context context){
        SocketVariablesPreferencias socketVariablesPreferencias = new SocketVariablesPreferencias("inReboot", context);
        if (!socketVariablesPreferencias.LeerVariable("START").equals("")) {
            if(!serviceIsRunningInForeground(messageService.class, context)){
                iniciarServicio(context);
            }
        }
    }

    public void iniciarServicio(Context context) {
        Intent intent1 = new Intent(context, messageService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent1);
        } else {
            context.startService(intent1);
        }
    }
}
