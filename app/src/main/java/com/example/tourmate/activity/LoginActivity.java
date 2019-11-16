package com.example.tourmate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailET, passwordET;
    private Button loginBtn;
    private String email,password;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle("Sign In");
        init();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailET.getText().toString().trim();
                password = passwordET.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty() ){

                    Toast.makeText(LoginActivity.this, "Email Empty!", Toast.LENGTH_SHORT).show();

                }else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Password Empty !", Toast.LENGTH_SHORT).show();
                }else {
                    signIn(email,password);
                }

            }
        });
    }

    private void signIn(String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Email or Password did not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void init() {
        emailET = findViewById(R.id.emailETId);
        passwordET = findViewById(R.id.passwordeETId);
        loginBtn = findViewById(R.id.loginBtId);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void singin(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}
