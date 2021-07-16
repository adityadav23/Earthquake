package com.example.android.quakereport;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes) {
        super(context, 0, earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView== null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent , false);
        }

        Earthquake currentEarthquake= getItem(position);

        TextView locationTextView = listItemView.findViewById(R.id.location);
        locationTextView.setText(currentEarthquake.getmLocation());

        TextView dateTextView = listItemView.findViewById(R.id.date);
        dateTextView.setText(currentEarthquake.getmDate());

        TextView magnitudeTextView = listItemView.findViewById(R.id.magnitude);
        magnitudeTextView.setText(currentEarthquake.getmMagnitude());


        return listItemView;
    }
}
