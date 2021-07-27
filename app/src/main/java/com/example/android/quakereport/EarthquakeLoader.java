package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    private final String mUrl;
   public EarthquakeLoader(Context context , String url){
       super(context);
       this.mUrl = url;
   }


    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {

       if(mUrl== null){
           return null;
       }

       ArrayList<Earthquake>  quakeArrayList = QueryUtils.fetchEarthquakeData(mUrl);
        return quakeArrayList;
    }
}
