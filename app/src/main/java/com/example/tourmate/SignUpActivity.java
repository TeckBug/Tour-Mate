package com.example.tourmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    private EditText nameEt, emailEt, passwordEt, comformPasswrodEt;
    private Button signUpBtn;
    private String name,email, password,conformPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.setTitle("Sign Up");
        init();
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name= nameEt.getText().toString().trim();
                email = emailEt.getText().toString().trim();
                password = passwordEt.getText().toString().trim();
                conformPassword = comformPasswrodEt.toString().trim();

                if(name.isEmpty()){

                    Toast.makeText(SignUpActivity.this, "Name Empty !", Toast.LENGTH_SHORT).show();

                }else if(email.isEmpty()){

                    Toast.makeText(SignUpActivity.this, "Email Empty !", Toast.LENGTH_SHORT).show();

                }else if (password.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Password Empty !", Toast.LENGTH_SHORT).show();
                }else if (conformPassword.isEmpty()){
                    Toast.makeText(SignUpActivity.this, "Conform Password Empty !", Toast.LENGTH_SHORT).show();
                }

                else {
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                    if (email.matches(emailPattern))
                    {
                        if(password.length() == 6) {

                            signUp(name, email, password, conformPassword);

                        }else {
                            Toast.makeText(SignUpActivity.this,"password length should be 6 character",Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home ){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void signUp(final String name, final String email, String password, String conformPassword) {


        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    String id = firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference reference = databaseReference.child("users").child(id);
                    HashMap<String, Object> userinfo = new HashMap<>();
                    userinfo.put("name", name);
                    userinfo.put("email", email);
                    reference.setValue(userinfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                             //   startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            }else {
                                Toast.makeText(SignUpActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(SignUpActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(SignUpActivity.this, "Sing Up Successfully", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Sign Up Faile ", Toast.LENGTH_SHORT).show();
            }
        });

            }


            private void init() {
                nameEt = findViewById(R.id.userNameETId);
                emailEt = findViewById(R.id.emailETId);
                passwordEt = findViewById(R.id.passwordeETId);
                comformPasswrodEt = findViewById(R.id.confirmPasswordId);
                signUpBtn = findViewById(R.id.logUpBtId);
                firebaseAuth = FirebaseAuth.getInstance();
                databaseReference = FirebaseDatabase.getInstance().getReference();

            }


            public void singup(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }

}

