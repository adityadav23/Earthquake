package com.example.android.quakereport;

public class Earthquake {

    private  String mLocation;
    private long mMiliseconds;
    private double mMagnitude;
    private String mUrl;

    //Constructor function

    public Earthquake(String mLocation, long mMiliseconds, double mMagnitude, String mUrl) {
        this.mLocation = mLocation;
        this.mMiliseconds = mMiliseconds;
        this.mMagnitude = mMagnitude;
        this.mUrl= mUrl;
    }

    //  Getter functions to return private values of members


    public String getmLocation() {
        return mLocation;
    }

    public long getmMiliseconds() {
        return mMiliseconds;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public void setmMiliseconds(long mMiliseconds) {
        this.mMiliseconds = mMiliseconds;
    }

    public void setmMagnitude(double mMagnitude) {
        this.mMagnitude = mMagnitude;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
