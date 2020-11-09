package com.example.bookify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //TODO: Help later
    }

    public void onSignupBtnClick (View view){
        EditText edtTxtName = findViewById(R.id.edtTxtName);
        EditText edtTxtPass = findViewById(R.id.edtTxtPass);
        EditText edtTxtEmail = findViewById(R.id.edtTxtEmail);
        TextView txtName = findViewById(R.id.splashtxtName);
        TextView txtPass = findViewById(R.id.splashTxtPass);
        TextView txtEmail = findViewById(R.id.splashTxtEmail);
        txtName.setText("Name = " + edtTxtName.getText().toString());
        txtPass.setText("Password = " + edtTxtPass.getText().toString());
        txtEmail.setText("Email = " + edtTxtEmail.getText().toString());
    }
}