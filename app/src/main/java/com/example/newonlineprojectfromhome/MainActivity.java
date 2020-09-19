package com.example.newonlineprojectfromhome;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText user;
    EditText pass;
    Button login;
    SharedPreferences sp;
    ProgressDialog progressDialog;
    TextView regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);

        user = findViewById(R.id.etEmail);
        pass = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);
        regis = findViewById(R.id.tvregistrasi);

        sp = getSharedPreferences("login",MODE_PRIVATE);
        sp.edit().putString("logged", sp.getString("logged", "missing")).apply();

        String admin = sp.getString("logged", "missing");
        String customer = sp.getString("logged", "missing");

        if(customer.equals("customer")){
            Intent intent = new Intent(MainActivity.this, CostumerDasboard.class);
            startActivity(intent);
            finish();
        }else if (admin.equals("admin")){
            Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
            startActivity(intent);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user.getText().toString();
                String password = pass.getText().toString().trim();
                progressDialog.setTitle("Logging In...");
                progressDialog.show();
                AndroidNetworking.post("http://192.168.6.201/rentalLaptop/login.php")
                        .addBodyParameter("email",username)
                        .addBodyParameter("password",password)
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject PAYLOAD = response.getJSONObject("PAYLOAD");
                                    boolean sukses = PAYLOAD.getBoolean("respon");
                                    String roleuser = PAYLOAD.getString("roleuser");
                                    Log.d("PAYLOAD", "onResponse: " + PAYLOAD);
                                    if (sukses && roleuser.equals("admin")) {
                                        sp.edit().putString("logged","admin").apply();
                                        Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                    } else if (sukses && roleuser.equals("customer")){
                                        sp.edit().putString("logged","customer").apply();
                                        Intent intent = new Intent(MainActivity.this, CostumerDasboard.class);
                                        startActivity(intent);
                                        finish();
                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(MainActivity.this, "gagal", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Log.d("eror", "onError: "+anError.getErrorDetail());
                            }
                        });
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
