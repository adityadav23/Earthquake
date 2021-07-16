package com.example.android.quakereport;

public class Earthquake {

    private  String mLocation;
    private String mDate;
    private String mMagnitude;

    //Constructor function

    public Earthquake(String mLocation, String mDate, String mMagnitude) {
        this.mLocation = mLocation;
        this.mDate = mDate;
        this.mMagnitude = mMagnitude;
    }

    //  Getter functions to return private values of members


    public String getmLocation() {
        return mLocation;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmMagnitude() {
        return mMagnitude;
    }
}
