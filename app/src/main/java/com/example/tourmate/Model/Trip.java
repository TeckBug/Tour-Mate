package com.example.tourmate.Model;

public class Trip {
    private String tripName,tripDescription,tripStartDate,tripEndDate;
    private int tripBudget;

    public Trip(String tripName, String tripDescription, String tripStartDate, String tripEndDate, int tripBudget) {
        this.tripName = tripName;
        this.tripDescription = tripDescription;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.tripBudget = tripBudget;
    }

    public String getTripName() {
        return tripName;
    }

    public String getTripDescription() {
        return tripDescription;
    }

    public String getTripStartDate() {
        return tripStartDate;
    }

    public String getTripEndDate() {
        return tripEndDate;
    }

    public int getTripBudget() {
        return tripBudget;
    }
}
