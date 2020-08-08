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
    String usertext;
    String passtext;
    String useradmin = "admin";
    String passadmin = "admin";

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
                   usertext = username.getText().toString();
                   passtext = password.getText().toString();
                   if (username.getText().toString().length()!=0 && password.getText().toString().length()!=0){
                       Intent intent  = new Intent(MainActivity.this,MainMenu.class);
                       startActivity(intent);
                       finish();
                   }
                   else if (usertext.equals(useradmin)&& passtext.equals(passadmin)){
                       Intent intent = new Intent(MainActivity.this,MainMenu.class);
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
