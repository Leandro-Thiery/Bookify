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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.PatternSyntaxException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtTxtPass, edtTxtEmail;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

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
        progressBar = findViewById(R.id.mainprogressBar);
        mAuth = FirebaseAuth.getInstance();

        //TODO: Help later
    }

    @Override
    public void onClick (View v){

        switch (v.getId()){
            case R.id.buttonLogin:
                userLogin();

                break;

            case R.id.txtbuttonRegister:
                Intent intentRegister = new Intent(MainActivity.this, Register.class);
                startActivity(intentRegister);
                break;
            case R.id.buttonForgot:
                Intent intentForgot = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intentForgot);
                break;

        }
        





    }

    private void userLogin() {
        String email = edtTxtEmail.getText().toString().trim();
        String password = edtTxtPass.getText().toString().trim();
        if(email.isEmpty()){
            edtTxtEmail.setError("Email is Required");
            edtTxtEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtTxtEmail.setError("Please enter a valid email address");
            edtTxtEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            edtTxtPass.setError(("Password is Required"));
            edtTxtPass.requestFocus();
            return;
        }
        if(password.length() < 6){
            edtTxtPass.setError("Minimal password length is 6 characters");
            edtTxtPass.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    progressBar.setVisibility(View.GONE);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Please Check your email to verify your user account", Toast.LENGTH_SHORT).show();
                    }



                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Failed to login! Please try again", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}

