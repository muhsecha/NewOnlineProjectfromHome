package com.example.newonlineprojectfromhome;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AdminDashboard extends AppCompatActivity {

    LinearLayout lndata,lnedit,lnlogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        lndata = findViewById(R.id.lndata);
        lnlogout = findViewById(R.id.lnsignout);

        lndata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboard.this,DataCostomer.class);
                startActivity(intent);
            }
        });

        lnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogInterface.OnClickListener dialog = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        switch (i) {

                            case DialogInterface.BUTTON_POSITIVE:
                                SharedPreferences mLogin = getSharedPreferences("login", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mLogin.edit();
                                editor.clear();
                                editor.apply();
                                Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                        }

                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Apakah anda yakin ingin keluar?").setPositiveButton("Ya", dialog)
                        .setNegativeButton("Tidak", dialog).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
