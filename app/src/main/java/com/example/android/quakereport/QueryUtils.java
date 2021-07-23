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

        /** A log tag to dispay log messages
         *
         */
    private static  final String LOG_TAG = QueryUtils.class.getSimpleName();

            /*
              An empty contructor is made so that no instance could be created of this class
             */
        private QueryUtils() {
        }

            /** It is public class to return the arraylist to the doInBackground method
             * It uses all the helper method to get the arraylist
             *
             * @param requestUrl
             * @return
             */
    public static ArrayList<Earthquake> fetchEarthquakeData(String requestUrl){

        // Creating a Url object
            URL url = createUrl(requestUrl);

            // It returns the json from the url we have provided above
            // by making https connection and buffereading the data
            String jsonResponse =null;
            try {
                jsonResponse= makeHttpsRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "problem in  http request");
            }

            /*
                It extracts the required data as per the query from jsonResponse
             */
            ArrayList<Earthquake> earthquakes = extractFeaturesFromJson(jsonResponse);

            return earthquakes;

        }

        /**
         * Return a list of {@link Earthquake} objects that has been built up from
         * parsing a JSON response.
         */
        private static ArrayList<Earthquake> extractFeaturesFromJson(String earthquakeJson) {

            // if the jsonResponse passed is empty return null

            if(TextUtils.isEmpty(earthquakeJson)){
                return null;
            }

            // Create an empty ArrayList that we can start adding earthquakes to
            ArrayList<Earthquake> earthquakes = new ArrayList<>();

            /**
             * Parsing the jsonResponse as per the requirement
             * And adding to the the earthquakes ArrayList
             */

            try {

                //parsing rootJsonObject

                    JSONObject baseJsonResponse = new JSONObject(earthquakeJson);
                //Parsing JsonArray
                    JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

                    //Iteration
                    for(int i=0;i<earthquakeArray.length();i++){
                        JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                        JSONObject properties = currentEarthquake.getJSONObject("properties");

                        //getting values in the variables

                        double magnitude = properties.getDouble("mag");
                        String location = properties.getString("place");
                        long time = properties.getLong("time");
                        String url = properties.getString("url");

                        //Adding values to earthquake arrayList

                        earthquakes.add(new Earthquake(location,time,magnitude,url));

                    }


                }  catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            // Return the Arraylist  earthquakes
            return earthquakes;
        }



        /*
            Helper method to convert string into url object
            Declaring static so that it could be used outside class without creating instance
            of this class QueryUtils
         */



        private static URL createUrl(String stringUrl){
           URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "error in createUrl");
            }
            return url;
        }

        /**

         Helper method to make HttpsConnection from the url object passed and returning the string of json response
         Declaring static so that it could be used outside class without creating instance
         of this class QueryUtils

         */
        private static String makeHttpsRequest(URL url) throws IOException{

            String jsonResponse = "";

            // Check if url is null, then return empty json response

            if(url==null){
                return jsonResponse;
            }
            //Initializing HttpsConnection and InputStream

            HttpsURLConnection urlConnection= null;
            InputStream  stream = null;


            try {
                //using openConnection to get httpsConnection object
                urlConnection =(HttpsURLConnection) url.openConnection();

                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);

                //Setting the request method we want to use
                urlConnection.setRequestMethod("GET");
                //Making the final connection
                urlConnection.connect();
                /*
                    Check if the connection was successful then retrieve jsonResponse
                   from the inputstream
                 */

                if(urlConnection.getResponseCode()==200) {

                    //getting the  inputstream
                    stream = urlConnection.getInputStream();

                    //using function to get the string of jsonResponse
                    jsonResponse = readFromStream(stream);
                }else{
                    Log.e(LOG_TAG, "error in makehttpsrequest"+ urlConnection.getResponseCode());
                }

            } catch (IOException e) {
                Log.e(LOG_TAG, "problem receiving json earthquake json result");
            }
            /**
             *  Finally disconnecting the urlConnection and input stream after fetching the
             *  json Response
             */
            finally{
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                if(stream!=null){
                    stream.close();
                }

            }
            // return the String jsonResponse
            return jsonResponse;

        }

        /*
            Converting the bytecode into human readable form using inputStreamreader
         */
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

