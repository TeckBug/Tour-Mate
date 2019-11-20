package com.example.tourmate.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tourmate.Adapter.TripAdapter;
import com.example.tourmate.Model.Trip;
import com.example.tourmate.R;
import com.example.tourmate.activity.AddTripActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TripsFragment extends Fragment {

    private FloatingActionButton tripFloatingActionButton;
    private TripAdapter adapter;
    private List<Trip> tripList;
    private RecyclerView recyclerView;

    private DatabaseReference tripRef;
    private FirebaseAuth mAuth;
    private String push_id;
    String current_user_id;
    public TripsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trips, container, false);
        init(view);
        tripFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { gotoAddTripActivity(v);
            }
        });

       getTripInfoFromDatabase(view);

        return view;
    }

    private void getTripInfoFromDatabase(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new TripAdapter(tripList,getContext());
        recyclerView.setAdapter(adapter);



        current_user_id=mAuth.getCurrentUser().getUid();
        tripRef= FirebaseDatabase.getInstance().getReference().child("Trip").child(current_user_id);

        tripRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    int i=1;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        push_id=dataSnapshot1.getKey();
                        String tripName=dataSnapshot.child(push_id).child("Trip_Name").getValue().toString();
                        String tripDescription=dataSnapshot.child(push_id).child("Trip_Description").getValue().toString();
                        String tripStartDate=dataSnapshot.child(push_id).child("Trip_Start_Date").getValue().toString();
                        String tripEndDate=dataSnapshot.child(push_id).child("Trip_End_Date").getValue().toString();
                        String tripBudget=dataSnapshot.child(push_id).child("Trip_Budget").getValue().toString();

                        tripList.add(new Trip(tripName,tripDescription,tripStartDate,tripEndDate,Integer.valueOf(tripBudget)));
                        adapter.notifyDataSetChanged();



                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    private void gotoAddTripActivity(View v) {
        Intent intent=new Intent(getContext(), AddTripActivity.class);
        startActivity(intent);
    }

    private void init(View view) {
        tripFloatingActionButton=view.findViewById(R.id.tripFloatingActionButton);
        recyclerView=view.findViewById(R.id.recyclearView);
        tripList=new ArrayList<>();
        mAuth=FirebaseAuth.getInstance();
    }

}
