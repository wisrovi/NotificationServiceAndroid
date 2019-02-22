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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.UtilVersionAndroid.ForAndroid8;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;


public class NotificationUtils extends ContextWrapper {
    private NotificationManager mManager;
    private static final String ANDROID_CHANNEL_ID = "www.linkedin.com.in.wisrovi.rodriguez";
    private static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";

    private static final String ANDROID_CHANNEL_ID_TEMP = "www.linkedin.com.in.wisrovi.rodriguez.NotificationService";
    private static final String ANDROID_CHANNEL_NAME_TEMP = "ANDROID CHANNEL 2";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationUtils(Context base) {
        super(base);
        createChannels();
        createChannelsAux();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels() {
        int prioridad5 = NotificationManager.IMPORTANCE_MAX; //Emitir sonido y mostrar en pantalla.
        int prioridad4 = NotificationManager.IMPORTANCE_HIGH; //Emitir sonido.
        int prioridad3 = NotificationManager.IMPORTANCE_DEFAULT; //con sonido
        int prioridad2 = NotificationManager.IMPORTANCE_LOW; //Sin sonido.
        int prioridad1 = NotificationManager.IMPORTANCE_MIN; //Sin sonido ni interrupción visual.
        int prioridad0 = NotificationManager.IMPORTANCE_NONE;
        int prioridad_1 = NotificationManager.IMPORTANCE_UNSPECIFIED;
        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                ANDROID_CHANNEL_NAME, prioridad1);
        androidChannel.enableLights(false);
        androidChannel.enableVibration(false);
        androidChannel.setLightColor(Color.GREEN);
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(androidChannel);
        createChannelsAux();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannelsAux() {
        int prioridad5 = NotificationManager.IMPORTANCE_MAX; //Emitir sonido y mostrar en pantalla.
        int prioridad4 = NotificationManager.IMPORTANCE_HIGH; //Emitir sonido.
        int prioridad3 = NotificationManager.IMPORTANCE_DEFAULT; //con sonido
        int prioridad2 = NotificationManager.IMPORTANCE_LOW; //Sin sonido.
        int prioridad1 = NotificationManager.IMPORTANCE_MIN; //Sin sonido ni interrupción visual.
        int prioridad0 = NotificationManager.IMPORTANCE_NONE;
        int prioridad_1 = NotificationManager.IMPORTANCE_UNSPECIFIED;
        NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID_TEMP,
                ANDROID_CHANNEL_NAME_TEMP, prioridad4);
        androidChannel.enableLights(true);
        androidChannel.enableVibration(true);
        androidChannel.setLightColor(Color.GREEN);
        androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(androidChannel);
    }

    private NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public String getAndroidChannelId() {
        return ANDROID_CHANNEL_ID;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationCompat.Builder BuilderAndroid8() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ANDROID_CHANNEL_ID);
        return builder;
    }

    public NotificationCompat.Builder BuilderAndroid8_TEMP() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ANDROID_CHANNEL_ID_TEMP);
        return builder;
    }
}
