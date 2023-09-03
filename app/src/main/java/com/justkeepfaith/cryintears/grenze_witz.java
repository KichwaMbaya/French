package com.justkeepfaith.cryintears;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class grenze_witz extends AppCompatActivity {

    TextView grenze_tit;
    Button grenze_contin;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    String loanlimit, Upper, Lower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grenze_witz);

        grenze_tit = findViewById(R.id.grenze_tit);
        grenze_contin = findViewById(R.id.grenze_contin);

        SharedPreferences sharedPreferences2 = getApplicationContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        sharedPreferences = sharedPreferences2;

        grenze_tit.setText("Salut " + sharedPreferences2.getString("last_name", "Utente"));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loan_limit", loanlimit);
        Upper = sharedPreferences.getString("Upper", "100");
        Lower = sharedPreferences.getString("Lower", "50");
        editor.commit();

        randomnumber();
        progress_dig();

        grenze_contin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(grenze_witz.this, principale2.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void progress_dig(){

        progressDialog = new ProgressDialog(grenze_witz.this);
        progressDialog.setTitle("S'il vous plaît, attendez");
        progressDialog.setMessage("Contacter les serveurs...");
        progressDialog.setProgressStyle(0);
        progressDialog.setMax(100);
        progressDialog.show();
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {

                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    progressDialog.setMessage("Vérification de l'authenticité...");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    progressDialog.setMessage("Vérification de la qualification...");
                    Thread.sleep(1500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    progressDialog.setMessage("Limite d'attribution.");
                    Thread.sleep(1000);
                    Intent intent = new Intent(grenze_witz.this, limite_premio.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }
    private void randomnumber() {

        int high = Integer.parseInt(Upper);
        int low = Integer.parseInt(Lower);

        loanlimit = String.valueOf(new Random().nextInt(high - low) + low);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loan_limit", loanlimit);
        editor.commit();
    }
}