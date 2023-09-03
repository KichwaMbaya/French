package com.justkeepfaith.cryintears;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class limite_premio extends AppCompatActivity {

    TextView limittit, pleasewait, limit_awardstat;
    Button limitctn;
    SharedPreferences sharedPreferences;
    final Timer t = new Timer();
    int counterp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limite_premio);

        limitctn = findViewById(R.id.limitctn);
        pleasewait = findViewById(R.id.pleasewait);
        limittit = findViewById(R.id.limit_tit);
        limit_awardstat = findViewById(R.id.limit_awardstat);

        limitctn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(limite_premio.this, principale2.class));
                t.cancel();
                limite_premio.this.finish();
            }
        });
        t.schedule(new TimerTask() {
            public void run() {
                limite_premio.this.counterp++;
                if (counterp == 2500) {
                    Intent intent = new Intent(limite_premio.this, principale2.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 10, 10);

        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        sharedPreferences = sharedPreferences2;
        String loan_arwdeddis = sharedPreferences2.getString("loan_limit", "123");

        limittit.setText("Salut " + sharedPreferences2.getString("last_name", "Utilisatrice") + ", votre limite de prêt est €" + loan_arwdeddis);
        limit_awardstat.setText("--Toutes nos félicitations! Vous êtes qualifié pour nos services de prêt et vous avez reçu une limite de prêt " +
                "de €" +loan_arwdeddis+ ". Ce n'est que le début, vous serez admissible à une limite de prêt plus élevée alors que nous " +
                "continuons à travailler ensemble. Bienvenue à bord.");
    }
}