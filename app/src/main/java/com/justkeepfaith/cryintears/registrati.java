package com.justkeepfaith.cryintears;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class registrati extends AppCompatActivity {

    EditText rgsterfirst, rgstermiddle, rgsterlast, rgsteremail, rgsterphone, rgsterpin, rgsterconfirm;
    Button rgsterdob, rgsterbutton;
    DatePickerDialog datePickerDialog;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrati);

        rgsterfirst = findViewById(R.id.rgsterfirst);
        rgstermiddle = findViewById(R.id.rgstermiddle);
        rgsterlast = findViewById(R.id.rgsterlast);
        rgsteremail = findViewById(R.id.rgsteremail);
        rgsterphone = findViewById(R.id.rgsterphone);
        rgsterpin = findViewById(R.id.rgsterpin);
        rgsterconfirm = findViewById(R.id.rgsterconfirm);
        rgsterdob = findViewById(R.id.rgsterdob);
        rgsterbutton = findViewById(R.id.rgsterbutton);


        initDatePicker();
        rgsterdob.setText(getTodaysDate());

        if (!Conectivity.isConnectingToInternet(registrati.this)) {
            Toast.makeText(registrati.this, "S'il vous plait, vérifiez votre connexion internet", Toast.LENGTH_SHORT).show();
        }

        sharedPreferences = getApplicationContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);

        rgsterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String first = rgsterfirst.getText().toString();
                String middle = rgstermiddle.getText().toString();
                String last = rgsterlast.getText().toString();
                String email = rgsteremail.getText().toString();
                String phone = rgsterphone.getText().toString();
                String pin = rgsterpin.getText().toString();
                String confirm = rgsterconfirm.getText().toString();

                if (first.isEmpty()){
                    rgsterfirst.setError("Nom");
                    return;
                }
                if (middle.isEmpty()){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("middle_name");
                    editor.commit();
                }
                if (last.isEmpty()){
                    rgsterlast.setError("Nom");
                    return;
                }
                if (email.isEmpty()){
                    rgsteremail.setError("Veuillez saisir votre adresse e-mail");
                    Toast.makeText(registrati.this, "Veuillez saisir votre adresse e-mail", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    rgsteremail.setError("Veuillez entrer un email valide");
                    rgsteremail.requestFocus();
                    return;
                }
                if (phone.isEmpty()){
                    rgsterphone.setError("Téléphone");
                    Toast.makeText(registrati.this, "Téléphone", Toast.LENGTH_SHORT).show();
                }
                if (phone.length() < 7){
                    rgsterphone.setError("Trop court");
                    Toast.makeText(registrati.this, "Trop court", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phone.length() > 13){
                    rgsterphone.setError("Trop long");
                    Toast.makeText(registrati.this, "Trop long", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pin.isEmpty()){
                    rgsterpin.setError("ÉPINGLE");
                    Toast.makeText(registrati.this, "ÉPINGLE", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pin.length() < 4){
                    rgsterpin.setError("Trop court");
                    Toast.makeText(registrati.this, "Troppo corta", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirm.isEmpty()){
                    rgsterconfirm.setError("Confirmer le NIP");
                    Toast.makeText(registrati.this, "Confirmer le NIP", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confirm.equals(pin)){
                    rgsterpin.setError("Les champs ne correspondent pas");
                    rgsterconfirm.setError("Les champs ne correspondent pas");
                    Toast.makeText(registrati.this, "Les champs ne correspondent pas", Toast.LENGTH_SHORT).show();
                    return;
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("first_name", first);
                editor.putString("middle_name", middle);
                editor.putString("last_name", last);
                editor.putString("Email", email);
                editor.putString("phone_number", phone);
                editor.putString("PIN", pin);

                Integer logged_in = 2;
                editor.putInt("logged", logged_in);

                editor.commit();


                AlertDialog.Builder dialog = new AlertDialog.Builder(registrati.this);
                dialog.setTitle("Es-tu sûr?");
                dialog.setMessage("Les informations que vous avez fournies ne peuvent pas être modifiées après l'inscription.");
                dialog.setPositiveButton("Procéder", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        progressDialog = new ProgressDialog(registrati.this);
                        progressDialog.setTitle("S'il vous plaît, attendez");
                        progressDialog.setMessage("Création de compte...");
                        progressDialog.setProgressStyle(0);
                        progressDialog.setMax(100);
                        progressDialog.show();
                        progressDialog.setCancelable(false);
                        new Thread(new Runnable() {
                            public void run() {
                                try {
                                    Thread.sleep(6000);
                                    Intent intent = new Intent(registrati.this, accesso.class);
                                    startActivity(intent);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                progressDialog.dismiss();
                            }
                        }).start();

                    }
                });
                dialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
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
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int current = cal.get(YEAR);
        int age = 16;

        int year = current - age;
        int month = cal.get(MONTH);
        month = month + 1;
        int day = cal.get(DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                rgsterdob.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int current = cal.get(YEAR);
        int age = 16;

        int year = current - age;
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + ", " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JANVIER";
        if(month == 2)
            return "FÉVRIER";
        if(month == 3)
            return "MARS";
        if(month == 4)
            return "APRILEAVRIL";
        if(month == 5)
            return "MAI";
        if(month == 6)
            return "JUIN";
        if(month == 7)
            return "JUILLET";
        if(month == 8)
            return "AOÛT";
        if(month == 9)
            return "SEPTEMBRE";
        if(month == 10)
            return "OCTOBRE";
        if(month == 11)
            return "NOVEMBRE";
        if(month == 12)
            return "DÉCEMBRE";

        //default should never happen
        return "AOÛT";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}