package com.example.newonlineprojectfromhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class EditDataCostomer extends AppCompatActivity {


    TextView tvId;
    EditText etNama, etEmail, etNohp, etAlamat, etNoktp;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data_costomer);

        tvId = findViewById(R.id.tvId);
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etNohp = findViewById(R.id.etNohp);
        etAlamat = findViewById(R.id.etAlamat);
        etNoktp = findViewById(R.id.etNoktp);
        btnEdit = findViewById(R.id.btnEdit);


        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Customer");

        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");
        final String nama = extras.getString("nama");
        final String email = extras.getString("email");
        final String nohp = extras.getString("nohp");
        final String alamat = extras.getString("alamat");
        final String noktp = extras.getString("noktp");

        tvId.setText("Id :" + id);
        etNama.setText(nama);
        etEmail.setText(email);
        etNohp.setText(nohp);
        etAlamat.setText(alamat);
        etNoktp.setText(noktp);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post("http://192.168.6.201/rentalLaptop/editdata.php")
                        .addBodyParameter("id", id)
                        .addBodyParameter("name", etNama.getText().toString())
                        .addBodyParameter("email", etEmail.getText().toString())
                        .addBodyParameter("nohp", etNohp.getText().toString())
                        .addBodyParameter("alamat", etAlamat.getText().toString())
                        .addBodyParameter("noktp", etNoktp.getText().toString())
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses) {
                                        Intent returnIntent = new Intent(EditDataCostomer.this, DataCostomer.class);
                                        returnIntent.putExtra("refresh", "refresh");
                                        startActivityForResult(returnIntent, 23);
                                        finish();
                                        Toast.makeText(EditDataCostomer.this, "Edit Sukses", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(EditDataCostomer.this, "Edit gagal", Toast.LENGTH_SHORT).show();
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

