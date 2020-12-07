package com.example.bookify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText edtTxtName, edtTxtPass, edtTxtEmail;
    private TextView txtName, txtPass, txtEmail;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        Button register =  findViewById(R.id.buttonRegister);
        register.setOnClickListener(this);

        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtPass = findViewById(R.id.edtTxtPass);


        progressBar = findViewById(R.id.progressBar);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonRegister:
                registerUser();
                break;
            default:
                break;
        }
    }

    private void registerUser(){
        final String email = edtTxtEmail.getText().toString().trim();
        final String name = edtTxtName.getText().toString().trim();
        String password = edtTxtPass.getText().toString().trim();

        if (name.isEmpty()){
            edtTxtName.setError("Name is Required");
            edtTxtName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            edtTxtEmail.setError("Email is Required");
            edtTxtEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtTxtEmail.setError("Email is Invalid");
            edtTxtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edtTxtPass.setError("Password is Required");
            edtTxtPass.requestFocus();
            return;
        }
        if (password.length() < 6){
            edtTxtPass.setError("Password is too short (Should be more than 6 Characters)");
            edtTxtPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);

                                    }else{
                                        Toast.makeText(Register.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });

                        }else{
                            Toast.makeText(Register.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
        
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
    }
}