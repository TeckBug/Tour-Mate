package com.example.tourmate.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tourmate.R;
import com.example.tourmate.fragment.TripsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.internal.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AddTripActivity extends AppCompatActivity {

    private EditText tripNameEt,tripDescriptionEt,tripStartDateEt,tripEndDateEt,tripBudgetEt;
    private Button addTripBtn;

    private String tripName,tripDescription,tripStartDate,tripEndDate;
    private String tripBudget;

    private DatabaseReference tripRef;
    private FirebaseAuth mAuth;
    private String current_user_id;

    private int mark;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        this.setTitle("Add Trip");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        tripStartDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatepicker(0);
            }
        });

        tripEndDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatepicker(1);
            }
        });

        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid=getData();

                if(isValid==true){

                    final ProgressDialog progressDialog = new ProgressDialog(AddTripActivity.this,R.style.MyProgressDialogue);
                    progressDialog.setTitle("Adding Trip");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

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
                                progressDialog.dismiss();
                                Toast.makeText(AddTripActivity.this, "Trip added successfully", Toast.LENGTH_SHORT).show();
                                gotoTripFragment();
                            }
                            else{
                                String message=task.getException().getMessage();
                                Toast.makeText(AddTripActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }





            }
        });




    }

    private void gotoTripFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, new TripsFragment());
        ft.commit();
    }

    private void openDatepicker(int flag) {
        if(flag==0){
            mark=0;
        }else{
            mark=1;
        }
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month + 1;
                String currentDate = dayOfMonth + "/" + month + "/" + year;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                Date date = null;

                try {
                    date = dateFormat.parse((currentDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (mark == 0){
                    tripStartDateEt.setText(dateFormat.format(date));
                }else{
                    tripEndDateEt.setText(dateFormat.format(date));
                }
                //long milisecond=date.getTime();

            }

        };



        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog=new DatePickerDialog(AddTripActivity.this,dateSetListener,year,month,day);

        datePickerDialog.show();


    }

    private boolean getData() {
        tripName=tripNameEt.getText().toString().trim();
        tripDescription=tripDescriptionEt.getText().toString().trim();
        tripStartDate=tripStartDateEt.getText().toString().trim();
        tripEndDate=tripEndDateEt.getText().toString().trim();
        tripBudget=tripBudgetEt.getText().toString().trim();

        boolean valid=true;
        if(tripName.equals("")){
            tripNameEt.setError("This field is required");
            valid=false;
        }
        else{
            tripNameEt.setError(null);
        }

        if(tripDescription.length()<3){
            tripDescriptionEt.setError("at least 3 characters");
            valid=false;
        }else{
            tripDescriptionEt.setError(null);
        }
        if(tripBudget.isEmpty()){
            tripBudgetEt.setError("This field is required");
            valid=false;
        }
        else{
            tripBudgetEt.setError(null);
        }
        if(tripStartDate.isEmpty()){
            tripStartDateEt.setError("Start date required");
            valid=false;
        }else{
            tripStartDateEt.setError(null);
        }
        if(tripEndDate.isEmpty()){
            tripEndDateEt.setError("Start date required");
            valid=false;
        }else{
            tripEndDateEt.setError(null);
        }






        return valid;
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
