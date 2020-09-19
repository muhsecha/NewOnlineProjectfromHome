package com.example.newonlineprojectfromhome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText txtNama, txtEmail, txtPassword, txtNoktp, txtNohp, txtAlamat,txtKonfirmPass;
    Button btnRegister;
    ProgressDialog progressDialog;
    SharedPreferences sp;

    String txtpass,txtKonfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        progressDialog = new ProgressDialog(this);

        txtNama = findViewById(R.id.txtNama);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtNoktp = findViewById(R.id.txtNoktp);
        txtNohp = findViewById(R.id.txtNohp);
        txtAlamat = findViewById(R.id.txtAlamat);
        txtKonfirmPass = findViewById(R.id.txtKonfirmPass);
        btnRegister = findViewById(R.id.btnRegister);

        txtpass = txtPassword.getText().toString();
        txtKonfirm = txtKonfirmPass.getText().toString();



            btnRegister.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    String nama = txtNama.getText().toString();
                    String email = txtEmail.getText().toString();
                    String noKtp = txtNoktp.getText().toString();
                    String noHp = txtNohp.getText().toString();
                    String alamat = txtAlamat.getText().toString();
                    String password = txtPassword.getText().toString().trim();
                    progressDialog.setTitle("Register In...");
                    progressDialog.show();
                    AndroidNetworking.post("http://192.168.6.201/rentalLaptop/registrasi.php")
                            .addBodyParameter("noktp", noKtp)
                            .addBodyParameter("email", email)
                            .addBodyParameter("password", password)
                            .addBodyParameter("name", nama)
                            .addBodyParameter("nohp", noHp)
                            .addBodyParameter("alamat", alamat)
                            .setPriority(Priority.LOW)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.d("hasil", "onResponse: ");
                                    try {
                                        JSONObject hasil = response.getJSONObject("hasil");
                                        String status = hasil.getString("STATUS");
                                        String message = hasil.getString("MESSAGE");
                                        Log.d("STATUS", "onResponse: " + status);
                                        if (status.equals("SUCCESS")) {
                                            sp.edit().putBoolean("logged", true).apply();
                                            Intent intent = new Intent(RegisterActivity.this, CostumerDasboard.class);
                                            startActivity(intent);
                                            finish();
                                            progressDialog.dismiss();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, message.toString(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {

                                }
                            });

                }
            });

        
    }
}

