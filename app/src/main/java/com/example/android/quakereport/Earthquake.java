package com.example.android.quakereport;

public class Earthquake {

    private  String mLocation;
    private long mMiliseconds;
    private String mMagnitude;

    //Constructor function

    public Earthquake(String mLocation, long mMiliseconds, String mMagnitude) {
        this.mLocation = mLocation;
        this.mMiliseconds = mMiliseconds;
        this.mMagnitude = mMagnitude;
    }

    //  Getter functions to return private values of members


    public String getmLocation() {
        return mLocation;
    }

    public long getmMiliseconds() {
        return mMiliseconds;
    }

    public String getmMagnitude() {
        return mMagnitude;
    }
}
