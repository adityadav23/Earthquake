/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    /*
        Url of usgs website to fetch data
     */

   private static final String USGS_URL = " https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private EarthquakeAdapter mAdapter;

    // Earthquake Loader id
    private static final int EARTHQUAKE_LOADER_ID = 1;

    //emptyStateTextView declaration
    TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView =  findViewById(R.id.list);

        // referencing emptystate textview
        emptyStateTextView = findViewById(R.id.emptyTextView);
        //Setting emptyTextview to listview using listview setEmptyView method
        earthquakeListView.setEmptyView(emptyStateTextView);
        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new EarthquakeAdapter(this, new ArrayList<>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // setting setOnItemClickListener in adapter to redirect to webpage

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getmUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        /**
         * getting reference of loaderManager
         * then using initLoader function to initialize loader of given id
         */
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

    }

    /**
     * creates a loader if not found using EarthquakeLoader class
     * @param id
     * @param bundle
     * @return
     */

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle bundle) {

        return new EarthquakeLoader(this, USGS_URL);

    }



    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {

        // getting refernce and hiding loader after onLoadfinished is called
        View loadingIndicator = findViewById(R.id.loadingIndicator);
        loadingIndicator.setVisibility(View.GONE);
        //show emptytextView after load if no data found
        emptyStateTextView.setText(R.string.no_earthquake);
        // clears previous loaded data
        mAdapter.clear();
        // checks if the arraylist is not empty and null
        //then adds all the arraylist entries to the adapter passed
        if(data!=null && !data.isEmpty()){
            mAdapter.addAll(data);
        }

    }

        /** Resets the loader
         *
         * @param loader
         */
           @Override
          public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {

               mAdapter.clear();
            }

}
