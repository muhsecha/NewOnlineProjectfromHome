package com.example.newonlineprojectfromhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button login;
    String userText;
    String passText;
    String userAdmin = "admin";
    String passAdmin = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.txtusername);
        password = findViewById(R.id.txtpassword);
        login = findViewById(R.id.btnlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (username.getText().toString().length()==0 || password.getText().toString().length()==0){
                   Toast.makeText(MainActivity.this, "Please enter your Username and Password...", Toast.LENGTH_SHORT).show();
               }else {
                   userText = username.getText().toString();
                   passText = password.getText().toString();
                   if (username.getText().toString().length()!=0 && password.getText().toString().length()!=0){
                       Intent intent  = new Intent(MainActivity.this, CostumerDasboard.class);
                       startActivity(intent);
                       finish();
                   }
                   else if (userText.equals(userAdmin)&& passText.equals(passAdmin)){
                       Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
                       startActivity(intent);
                       finish();
                   }else {
                       Toast.makeText(MainActivity.this, "Wrong Username or Password!!", Toast.LENGTH_SHORT).show();
                   }
               }
            }
        });

    }
}
