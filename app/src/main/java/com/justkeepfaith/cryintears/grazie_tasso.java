package com.justkeepfaith.cryintears;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class grazie_tasso extends AppCompatActivity {

    TextView thanksokbutton, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grazie_tasso);

        thanksokbutton = (TextView) findViewById(R.id.thanksokbutton);
        no = (TextView) findViewById(R.id.no);

        cancelalarm();


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.TEXT", "Pour des conditions de prêt rapides et optimales. " +
                        "Suivez le lien ci-dessous pour télécharger l'application dans le Play Store et postulez. Recevez €50 " +
                        "lorsque vous partagez avec un ami\n  " +
                        "https://play.google.com/store/apps/details?id=com.justkeepfaith.cryintears");
                intent.setType("text/plain");
                grazie_tasso.this.startActivity(Intent.createChooser(intent, (CharSequence) null));
            }
        });
        thanksokbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.justkeepfaith.cryintears"));
                startActivity(intent);
            }
        });

    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Donnez-nous une note de 5 étoiles et un avis").setCancelable(false).setPositiveButton("TAUX", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                grazie_tasso.this.startActivity(new Intent("android.intent.action.VIEW",
                        Uri.parse("https://play.google.com/store/apps/details?id=com.justkeepfaith.cryintears")));
            }
        }).setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }
    private void cancelalarm(){

        Intent intent = new Intent(grazie_tasso.this, Notification_Alarm2.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(grazie_tasso.this, 0,
                intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.cancel(pendingIntent);
    }
}