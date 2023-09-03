package com.justkeepfaith.cryintears;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification_Alarm2 extends BroadcastReceiver {
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context.getApplicationContext(), striscia_pagina.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent activity = PendingIntent.getActivity(context.getApplicationContext(),
                0, intent1, PendingIntent.FLAG_IMMUTABLE);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Successful")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Votre prêt est sur le point d'être annulé.")
                .setContentText("Votre demande de prêt a été acceptée mais est sur le point d'être annulée. Accédez à l'application " +
                        "et complétez les étapes manquantes pour recevoir votre prêt.")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Votre demande de prêt a été acceptée mais est sur le point d'être annulée. Accédez à l'application " +
                                "et complétez les étapes manquantes pour recevoir votre prêt"))
                .setAutoCancel(true)
                .setContentIntent(activity);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
