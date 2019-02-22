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

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.UtilVersionAndroid.UtilGeneral;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.UtilVersionAndroid.ForAndroid8.NotificationUtils;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service.messageService;
import rodriguez.wisrovi.in.com.linkedin.servicenotification.R;


public class Util {

    private PendingIntent pendingIntent;
    private String nombreAplicacion;
    private int ID_SERVICIO_PRIMER_PLANO;
    private NotificationCompat.Builder builder;
    private Context context;

    public Util(Context context, String ce, int id, NotificationCompat.Builder build, int iconoAplicacion) {
        build.setVisibility(Notification.VISIBILITY_PRIVATE);
        this.context = context;
        Intent targetIntent = new Intent(context, messageService.class);
        targetIntent.setAction("RMAT");
        pendingIntent = PendingIntent.getActivity(context, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        nombreAplicacion = ce;
        this.ID_SERVICIO_PRIMER_PLANO = id;

        if(iconoAplicacion==0){
             iconoAplicacion = R.drawable.icono_notificaciones;
        }
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), iconoAplicacion);
        build.setLargeIcon(bm);
        this.builder = build;
    }

    public Notification updatePush(boolean estadoConexion, String mensaje, boolean isAdmin) {
        if (isAdmin) {
            Intent targetIntent = new Intent(context, messageService.class);
            targetIntent.setAction("STOP");
            PendingIntent botonIntent = PendingIntent.getActivity(context, 0,
                    targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Action playAction =
                    new NotificationCompat.Action(R.drawable.icono_apagar, "Detener Servicio", botonIntent);
            builder.addAction(playAction);
        }

        builder.setContentIntent(pendingIntent);
        if (estadoConexion) {
            builder.setSmallIcon(R.drawable.conectado);
        } else {
            builder.setSmallIcon(R.drawable.desconectado);
        }
        builder.setContentTitle(nombreAplicacion);
        builder.setContentText(mensaje);
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(false);

        //builder.setPriority(Notification.PRIORITY_MAX);
        return builder.build();
    }

    public Notification notificacionForegroundService(String mensaje, boolean isAdmin) {
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.icono_comprometerse);
        builder.setContentTitle(nombreAplicacion);
        builder.setContentText(mensaje);
        builder.setWhen(System.currentTimeMillis());
        //builder.setFullScreenIntent(pendingIntent, true);
        builder.setAutoCancel(false);

        int prioridad4 = Notification.PRIORITY_MAX;
        int prioridad3 = Notification.PRIORITY_HIGH;
        int prioridad2 = Notification.PRIORITY_DEFAULT;
        int prioridad1 = Notification.PRIORITY_LOW;
        int prioridad0 = Notification.PRIORITY_MIN;
        builder.setPriority(prioridad1);

        if (isAdmin) {
            Intent targetIntent = new Intent(context, messageService.class);
            targetIntent.setAction("STOP");
            PendingIntent botonIntent = PendingIntent.getActivity(context, 0,
                    targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Action playAction =
                    new NotificationCompat.Action(R.drawable.icono_apagar, "Detener Servicio", botonIntent);
            builder.addAction(playAction);
        }


        //builder.setVibrate(new long[]{200,500,1000});
        String categoria14 = Notification.CATEGORY_CALL;
        String categoria13 = Notification.CATEGORY_MESSAGE;
        String categoria12 = Notification.CATEGORY_EMAIL;
        String categoria11 = Notification.CATEGORY_EVENT;
        String categoria10 = Notification.CATEGORY_PROMO;
        String categoria9 = Notification.CATEGORY_ALARM;
        String categoria8 = Notification.CATEGORY_PROGRESS;
        String categoria7 = Notification.CATEGORY_SOCIAL;
        String categoria6 = Notification.CATEGORY_ERROR;
        String categoria5 = Notification.CATEGORY_TRANSPORT;
        String categoria4 = Notification.CATEGORY_SYSTEM;
        String categoria3 = Notification.CATEGORY_SERVICE;
        String categoria2 = Notification.CATEGORY_RECOMMENDATION;
        String categoria1 = Notification.CATEGORY_STATUS;


        builder.setCategory(categoria3);

        return builder.build();
    }

    public void detenerforegroundService(String mensaje) {
        builder.setSmallIcon(R.drawable.desconectado)
                .setContentTitle(nombreAplicacion)
                .setContentText(mensaje)
                .setWhen(System.currentTimeMillis());

        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = builder.build();
        mNotificationManager.notify(ID_SERVICIO_PRIMER_PLANO, builder.build());
    }

    public void MsgPush(PushDto pushDto) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder temporalBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            temporalBuilder = new NotificationUtils(context).BuilderAndroid8_TEMP();
        } else {
            temporalBuilder = new NotificationCompat.Builder(context);
        }

        int imagenPequena = pushDto.getIconoPequeno();
        if (imagenPequena == 0) {
            imagenPequena = R.drawable.icono_mensaje;
        }
        temporalBuilder.setSmallIcon(imagenPequena);

        temporalBuilder.setContentText(pushDto.getTextoNotificacion());
        temporalBuilder.setContentTitle(pushDto.getTituloNotificacion());
        temporalBuilder.setTicker(pushDto.getMensajeTicker());
        temporalBuilder.setContentInfo(pushDto.getInfoNotificacion());

        int imagenGrande = pushDto.getIconoGrande();
        if (imagenGrande != 0) {
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), imagenGrande);
            temporalBuilder.setLargeIcon(bm);
        }

        if (Boolean.parseBoolean(pushDto.getSonido())) {
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            temporalBuilder.setSound(alarmSound);
        }

        if (Boolean.parseBoolean(pushDto.getVibrar())) {
            temporalBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        }

        temporalBuilder.setAutoCancel(pushDto.isAutocancelable());
        temporalBuilder.setPriority(Notification.PRIORITY_MAX);

        String categoria14 = Notification.CATEGORY_CALL;
        String categoria13 = Notification.CATEGORY_MESSAGE;
        String categoria12 = Notification.CATEGORY_EMAIL;
        String categoria11 = Notification.CATEGORY_EVENT;
        String categoria10 = Notification.CATEGORY_PROMO;
        String categoria9 = Notification.CATEGORY_ALARM;
        String categoria8 = Notification.CATEGORY_PROGRESS;
        String categoria7 = Notification.CATEGORY_SOCIAL;
        String categoria6 = Notification.CATEGORY_ERROR;
        String categoria5 = Notification.CATEGORY_TRANSPORT;
        String categoria4 = Notification.CATEGORY_SYSTEM;
        String categoria3 = Notification.CATEGORY_SERVICE;
        String categoria2 = Notification.CATEGORY_RECOMMENDATION;
        String categoria1 = Notification.CATEGORY_STATUS;
        temporalBuilder.setCategory(categoria13);
        temporalBuilder.setVibrate(new long[]{1000,3000,2000});

        try {
            String ruta = this.rutaClases(context) + pushDto.getNombreClaseIniciar();
            Class claseIniciar = Class.forName(ruta);
            Intent intent = new Intent(context, claseIniciar);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			
            PendingIntent contentIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            temporalBuilder.setContentIntent(contentIntent);

            int id = pushDto.getIdNotificacion();
            if(pushDto.getIdNotificacion()==0){
                id = createID();
            }
            notificationManager.notify(pushDto.getIdNotificacion(), temporalBuilder.build());
        } catch (Exception var12) {
            System.out.print("No se pudo crear el push, hubo un problema.");
        }

    }

    public int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }

    private String rutaClases(Context context) {
        String applicationId = context.getPackageName();
        return applicationId + ".";
    }
}
