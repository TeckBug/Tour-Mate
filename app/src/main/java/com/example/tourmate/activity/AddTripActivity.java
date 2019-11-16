package com.example.tourmate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddTripActivity extends AppCompatActivity {

    private EditText tripNameEt,tripDescriptionEt,tripStartDateEt,tripEndDateEt,tripBudgetEt;
    private Button addTripBtn;

    private String tripName,tripDescription,tripStartDate,tripEndDate;
    private int tripBudget;

    private DatabaseReference tripRef;
    private FirebaseAuth mAuth;
    private String current_user_id;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        this.setTitle("Add Trip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();

                current_user_id=mAuth.getCurrentUser().getUid();
                tripRef=FirebaseDatabase.getInstance().getReference().child("Trip");
                HashMap<String,Object> trip=new HashMap<>();
                trip.put("Trip_Name",tripName);
                trip.put("Trip_Description",tripDescription);
                trip.put("Trip_Start_Date",tripStartDate);
                trip.put("Trip_End_Date",tripEndDate);
                trip.put("Trip_Budget",tripBudget);

                tripRef.child(current_user_id).push().updateChildren(trip).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddTripActivity.this, "Trip added successfully", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String message=task.getException().getMessage();
                            Toast.makeText(AddTripActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });




            }
        });




    }

    private void getData() {
        tripName=tripNameEt.getText().toString();
        tripDescription=tripDescriptionEt.getText().toString();
        tripStartDate=tripStartDateEt.getText().toString();
        tripEndDate=tripEndDateEt.getText().toString();
        tripBudget=Integer.valueOf(tripBudgetEt.getText().toString());
    }

    private void init() {
        tripNameEt=findViewById(R.id.tripNameEt);
        tripDescriptionEt=findViewById(R.id.tripDescriptionEt);
        tripStartDateEt=findViewById(R.id.tripStartDateEt);
        tripEndDateEt=findViewById(R.id.tripEndDateEt);
        tripBudgetEt=findViewById(R.id.tripBudgetEt);
        addTripBtn=findViewById(R.id.addTripBtn);

        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }



        return super.onOptionsItemSelected(item);

    }
}
