package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
     * Helper methods related to requesting and receiving earthquake data from USGS.
     */
    public final class QueryUtils {

        /**
         * Create a private constructor because no one should ever create a {@link QueryUtils} object.
         * This class is only meant to hold static variables and methods, which can be accessed
         * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
         */

        private static  final String LOG_TAG = QueryUtils.class.getSimpleName();
        private QueryUtils() {
        }

        public static ArrayList<Earthquake> fetchEarthquakeData(String requestUrl){
            URL url = createUrl(requestUrl);
            String jsonResponse =null;
            try {
                jsonResponse= makeHttpsRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "problem in  http request");
            }
            ArrayList<Earthquake> earthquakes = extractFeaturesFromJson(jsonResponse);

            return earthquakes;

        }

        /**
         * Return a list of {@link Earthquake} objects that has been built up from
         * parsing a JSON response.
         */
        private static ArrayList<Earthquake> extractFeaturesFromJson(String earthquakeJson) {

            if(TextUtils.isEmpty(earthquakeJson)){
                return null;
            }

            // Create an empty ArrayList that we can start adding earthquakes to
            ArrayList<Earthquake> earthquakes = new ArrayList<>();

            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.

                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and

                // build up a list of Earthquake objects with the corresponding data.



                try {

                    JSONObject baseJsonResponse = new JSONObject(earthquakeJson);

                    JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

                    //Iteration
                    for(int i=0;i<earthquakeArray.length();i++){
                        JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                        JSONObject properties = currentEarthquake.getJSONObject("properties");

                        double magnitude = properties.getDouble("mag");
                        String location = properties.getString("place");
                        long time = properties.getLong("time");
                        String url = properties.getString("url");

                        earthquakes.add(new Earthquake(location,time,magnitude,url));

                    }


                }  catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            // Return the list of earthquakes
            return earthquakes;
        }




        private static URL createUrl(String stringUrl){
           URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "error in createUrl");
            }
            return url;
        }
        /*Declaring static so that it could be used outside class without creating instance
         of this class QueryUtils


         */
        private static String makeHttpsRequest(URL url) throws IOException{

            String jsonResponse = "";
            if(url==null){
                return jsonResponse;
            }

            HttpsURLConnection urlConnection= null;
            InputStream  stream = null;
            try {
                urlConnection =(HttpsURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                if(urlConnection.getResponseCode()==200) {
                    stream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(stream);
                }else{
                    Log.e(LOG_TAG, "error in makehttpsrequest"+ urlConnection.getResponseCode());
                }

            } catch (IOException e) {
                Log.e(LOG_TAG, "problem receiving json earthquake json result");
            }
            finally{
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                if(stream!=null){
                    stream.close();
                }

            }
            return jsonResponse;

        }
        private static String readFromStream(InputStream stream) throws IOException {

            StringBuilder strBuilder= new StringBuilder();
            if(stream!= null){
                InputStreamReader inputStreamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
               String line = reader.readLine();


                while(line!= null){
                    strBuilder.append(line);
                    line= reader.readLine();
                }



            }
            return strBuilder.toString();
        }
    }

