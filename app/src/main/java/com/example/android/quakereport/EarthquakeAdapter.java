package com.example.android.quakereport;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {


    private static final String LOCATION_SEPARATOR = " of ";

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

        String originalLocation = currentEarthquake.getmLocation();
        String primaryLocation;
        String locationOffset;
        if(originalLocation.contains(LOCATION_SEPARATOR)){
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0]+ LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }else{
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation= originalLocation;
        }

        TextView primaryLocationTextView = listItemView.findViewById(R.id.primary_location);
        primaryLocationTextView.setText(primaryLocation);

        TextView locationOffsetTextView = listItemView.findViewById(R.id.location_offset);
        locationOffsetTextView.setText(locationOffset);

        Date dateObject = new Date(currentEarthquake.getmMiliseconds());

        TextView dateTextView = listItemView.findViewById(R.id.date);

        String formattedDate = formatDate(dateObject);
        dateTextView.setText(formattedDate);



        TextView timeTextView = listItemView.findViewById(R.id.time);
        String formattedTime = formatTime(dateObject);
        timeTextView.setText(formattedTime);

        TextView magnitudeTextView = listItemView.findViewById(R.id.magnitude);
        String formattedMagnitude= formatMagnitude(currentEarthquake.getmMagnitude());
        magnitudeTextView.setText(formattedMagnitude);


        return listItemView;
    }

    private String formatDate(Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd , yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude){
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

}
