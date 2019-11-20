package com.example.tourmate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tourmate.Model.Trip;
import com.example.tourmate.R;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {
    private List<Trip> tripList;
    private Context context;

    public TripAdapter(List<Trip> tripList, Context context) {
        this.tripList = tripList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_trip_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip=tripList.get(position);
        holder.tripNameTv.setText(trip.getTripName());
        holder.tripDescriptionTv.setText(trip.getTripDescription());

    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tripNameTv,tripDescriptionTv;
        public Button detailsBtn,memoriesBtn,deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tripNameTv=itemView.findViewById(R.id.tripNameTv);
            tripDescriptionTv=itemView.findViewById(R.id.tripDescriptionTv);
            detailsBtn=itemView.findViewById(R.id.detailsBtn);
            memoriesBtn=itemView.findViewById(R.id.memoriesBtn);
            deleteBtn=itemView.findViewById(R.id.deleteBtn);
        }
    }
}
