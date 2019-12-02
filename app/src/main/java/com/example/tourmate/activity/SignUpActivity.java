package com.example.tourmate.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tourmate.Model.User;
import com.example.tourmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    private EditText nameEt, emailEt, passwordEt, comformPasswrodEt;
    private Button signUpBtn;
    private StorageReference postimagesreference;
    private String savecurrentdate,savecurrenttime, name, email, password, confirmPassword, postrandomname, downloadurl, currentuser;
    private String imageUrl;
    private FirebaseAuth firebaseAuth;
    private ImageView imageView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ProgressDialog loadinbar;
    private static final int Gallery_Pick = 1;
    private Uri ImageUri;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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

                name = nameEt.getText().toString().trim();
                email = emailEt.getText().toString().trim();
                password = passwordEt.getText().toString().trim();
                confirmPassword = comformPasswrodEt.toString().trim();

                Calendar callForDate = Calendar.getInstance();
                SimpleDateFormat currentdate = new SimpleDateFormat("dd-MMM-yyyy");
                savecurrentdate = currentdate.format(callForDate.getTime());

                Calendar callfortime = Calendar.getInstance();
                SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm");
                savecurrenttime = currenttime.format(callForDate.getTime());

                postrandomname = savecurrentdate + savecurrenttime;



                if (name.isEmpty()) {
                    nameEt.setError(getString(R.string.input_error_name));
                    nameEt.requestFocus();
                    return;
                } else if (email.isEmpty()) {
                    emailEt.setError(getString(R.string.input_error_email));
                    emailEt.requestFocus();
                    return;
                } else if (password.isEmpty()) {
                    passwordEt.setError(getString(R.string.input_error_password));
                    passwordEt.requestFocus();
                    return;

                } else if (confirmPassword.isEmpty()) {
                    comformPasswrodEt.setError(getString(R.string.input_error_conformPassword));
                    comformPasswrodEt.requestFocus();
                    return;
                } else if (password.length() < 6) {


                    Toast.makeText(SignUpActivity.this, R.string.input_error_More6Characters, Toast.LENGTH_SHORT).show();
                    return;
                } else if (ImageUri == null) {
                    Toast.makeText(SignUpActivity.this, R.string.input_error_image, Toast.LENGTH_SHORT).show();
                    return;
                } else if (emailEt.getText().toString().trim().matches(emailPattern)) {
                   /* if (confirmPassword.contains(password)) {*/

                        loadinbar.setTitle("SignUp");
                        loadinbar.setMessage("Signing up");
                        loadinbar.show();
                        loadinbar.setCanceledOnTouchOutside(true);

                        final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("Post Images").child(ImageUri.getLastPathSegment() + postrandomname + ".jpg");
                        filepath.putFile(ImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {

                                    Task<Uri> result = task.getResult().getMetadata().getReference().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            downloadurl = uri.toString();
                                            signUpPassword(name, email, password, downloadurl);

                                        }
                                    });



                                }
                            }
                        });
                   /* }
                    else {

                        Toast.makeText(SignUpActivity.this, "password not match", Toast.LENGTH_SHORT).show();
                    }*/
                }

                else {
                    Toast.makeText(SignUpActivity.this, "Invalid Email address", Toast.LENGTH_SHORT).show();
                }





            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void signUpPassword(final String name, final String email, String password, final String image) {


        final User user = new User(name, email);
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    String id = firebaseAuth.getCurrentUser().getUid();
                    user.setUserId(id);
                    user.setProfilePhoto(image);
                    DatabaseReference reference = databaseReference.child("users").child(id);

                    reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                imageView.setVisibility(View.INVISIBLE);
                                nameEt.setText("");
                                emailEt.setText("");
                                passwordEt.setText("");
                                comformPasswrodEt.setText("");
                                loadinbar.dismiss();
                                Toast.makeText(SignUpActivity.this, "Sign Up success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Sign Up not success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

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
        imageView = findViewById(R.id.cameraSignUpIvId);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        loadinbar = new ProgressDialog(this);
        postimagesreference = FirebaseStorage.getInstance().getReference();

        imageView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGalary();
                    }
                });
    }

    private void openGalary() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, Gallery_Pick);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            imageView.setImageURI(ImageUri);
        }
    }

    public void singup(View view) {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }

}

