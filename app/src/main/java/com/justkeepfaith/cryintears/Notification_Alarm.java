package com.justkeepfaith.cryintears;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification_Alarm extends BroadcastReceiver {

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
                .setContentTitle("Toutes nos félicitations!! Votre demande de prêt a été acceptée!!")
                .setContentText("Votre prêt a été approuvé. Connectez-vous à l'application pour les détails de décaissement.")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Votre prêt a été approuvé. Connectez-vous à l'application pour les détails de décaissement."))
                .setAutoCancel(true)
                .setContentIntent(activity);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, builder.build());
    }
}
