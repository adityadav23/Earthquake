package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    /**
     * Declararing global variable for url
     * Define constructor to take context and string url

     */
    private final String mUrl;
   public EarthquakeLoader(Context context , String url){
       super(context);
       this.mUrl = url;
   }


    /**
     * calling foceLoad method to startloading
     */
    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    /**
     * Similar to doInBackground
     * check if url is not null
     * get the arraylist of earthquakes Using class QueryUtils
     * @return
     */
    @Override
    public ArrayList<Earthquake> loadInBackground() {

       if(mUrl== null){
           return null;
       }

       ArrayList<Earthquake>  quakeArrayList = QueryUtils.fetchEarthquakeData(mUrl);
        return quakeArrayList;
    }
}
