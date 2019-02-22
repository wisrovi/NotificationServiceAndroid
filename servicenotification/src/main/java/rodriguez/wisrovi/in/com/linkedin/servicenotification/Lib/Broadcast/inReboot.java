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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.messageService;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.VariablesPreferenciales.SocketVariablesPreferencias;





public class inReboot extends BroadcastReceiver {
    public inReboot() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (("android.net.conn.CONNECTIVITY_CHANGE").equals(action)) {
            SocketVariablesPreferencias socketVariablesPreferencias = new SocketVariablesPreferencias("inReboot", context);
            if (!socketVariablesPreferencias.LeerVariable("START").equals("")) {
                boolean servicioFuncionando = new UtilBroadcast().isMyServiceRunning(messageService.class, context);
                if (!servicioFuncionando) {
                    new UtilBroadcast().iniciarServicio(context);
                }
            }
        }

        if (("android.intent.action.BOOT_COMPLETED").equals(action)) {
            new UtilBroadcast().iniciarServicio(context);
        }
    }
}
