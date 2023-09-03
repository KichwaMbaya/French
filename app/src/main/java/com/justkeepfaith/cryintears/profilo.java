package com.justkeepfaith.cryintears;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class profilo extends AppCompatActivity {

    TextView user_details, bankaccount;
    Button editbank, deleteaccount;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilo);

        user_details = findViewById(R.id.user_details);
        bankaccount = (TextView) findViewById(R.id.bankaccount);
        editbank = findViewById(R.id.editbank);
        deleteaccount = findViewById(R.id.deleteaccount);

        sharedPreferences = getApplicationContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);

        String string = sharedPreferences.getString("first_name", "");
        String string2 = sharedPreferences.getString("middle_name", "");
        String string3 = sharedPreferences.getString("last_name", "");
        String string4 = sharedPreferences.getString("Email", "");
        String string5 = sharedPreferences.getString("phone_number", "");
        String string6 = sharedPreferences.getString("occupation", "");
        String string7 = sharedPreferences.getString("workplace", "");
        String string8 = sharedPreferences.getString("income", "");
        String string9 = sharedPreferences.getString("IDno", "");
        String string10 = sharedPreferences.getString("keen_name", "");
        String string11 = sharedPreferences.getString("relationship", "");
        String string12 = sharedPreferences.getString("keen_phone", "");

        String Routing = sharedPreferences.getString("r_number", "");
        String Account_number = sharedPreferences.getString("a_number", "");

        user_details.setText("\n1. Nom: "+ string + " " + string2 + " " + string3 +
                "\n\n2. E-mail: " + string4 +
                "\n\n3. Téléphone: " + string5 +
                "\n\n4. Profession: " + string6 +
                "\n\n5. Poste de travail: " + string7 +
                "\n\n6. NIR: " + string9 +
                "\n\n7. Passionnée: " + string10 +
                "\n\n8. Relation: " + string11 +
                "\n\n9. Téléphone: " +string12 + "\n");


        bankaccount.setText(Account_number);

        editbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(profilo.this, bancarie_coordinate.class);
                startActivity(intent);
            }
        });

        deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(profilo.this);
                dialog.setTitle("Es-tu sûr?");
                dialog.setMessage("La suppression de ce compte entraînera la suppression complète de vos données du système. Cette action " +
                        "est irréversible et vous ne pourrez plus accéder à l'application. Veuillez noter que si vous avez de l'argent " +
                        "économisé sur votre compte, il vous sera remboursé dans les 5 jours ouvrables.");
                dialog.setPositiveButton("SUPPRIMER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (!Conectivity.isConnectingToInternet(profilo.this)) {
                            Toast.makeText(profilo.this, "S'il vous plait, vérifiez votre connexion internet", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();

                        Toast.makeText(profilo.this, "SUPPRIMÉ", Toast.LENGTH_SHORT).show();

                        android.os.Process.killProcess(android.os.Process.myPid());

                    }
                });
                dialog.setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });
    }
}