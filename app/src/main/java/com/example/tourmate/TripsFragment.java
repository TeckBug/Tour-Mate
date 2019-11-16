package com.example.tourmate;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TripsFragment extends Fragment {

    private FloatingActionButton tripFloatingActionButton;

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
            public void onClick(View v) {
                gotoAddTripActivity(v);
            }
        });


        return view;
    }

    private void gotoAddTripActivity(View v) {
        Intent intent=new Intent(getContext(),AddTripActivity.class);
        startActivity(intent);
    }

    private void init(View view) {
        tripFloatingActionButton=view.findViewById(R.id.tripFloatingActionButton);
    }

}
