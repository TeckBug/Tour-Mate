package com.example.tourmate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText emailET, passwordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        emailET = findViewById(R.id.emailETId);
        passwordET= findViewById(R.id.passwordeETId);
    }

    public void singup(View view) {

    }
}
