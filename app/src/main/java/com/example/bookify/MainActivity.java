package com.example.bookify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    String userEmail;
    String userPassword;
    private EditText edtTxtPass, edtTxtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = findViewById(R.id.buttonLogin);
        login.setOnClickListener(this);
        Button register = findViewById(R.id.txtbuttonRegister);
        register.setOnClickListener(this);
        Button forgot = findViewById(R.id.buttonForgot);
        forgot.setOnClickListener(this);

        edtTxtPass = findViewById(R.id.edtTxtPass);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);


        //TODO: Help later
    }

    @Override
    public void onClick (View v){

        switch (v.getId()){
            case R.id.buttonLogin:
                userEmail = edtTxtPass.getText().toString();
                userPassword = edtTxtEmail.getText().toString();

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                break;


            case R.id.txtbuttonRegister:
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intentRegister = new Intent(MainActivity.this, Register.class);
                startActivity(intentRegister);
                break;
            case R.id.buttonForgot:
                Intent intentForgot = new Intent(MainActivity.this, Forgot.class);
                startActivity(intentForgot);
                break;

        }





    }



}

