package com.example.tourmate.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tourmate.R;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class splashActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.splashProgressBar);

        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                startApp();
            }
        });
        thread.start();
    }

    private void startApp() {
        if (firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(splashActivity.this,MainActivity.class));
            finish();
        }else {
            startActivity(new Intent(splashActivity.this,LoginActivity.class));
        }
    }

    private void doWork() {
        for (progress = 20; progress<=100; progress+=20){
            try {
                Thread.sleep(500);
                progressBar.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
