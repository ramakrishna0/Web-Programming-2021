package com.example.vijaya.earthquakeapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Earthquake} objects.
     */
    public static List<Earthquake> fetchEarthquakeData2(String requestUrl) {
        // An empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();
        //  URL object to store the url for a given string
        URL url = null;
        // A string to store the response obtained from rest call in the form of string
        String jsonResponse = "";
        try {
            //TODO: 1. Create a URL from the requestUrl string and make a GET request to it
            //TODO: 2. Read from the Url Connection and store it as a string(jsonResponse)
                /*TODO: 3. Parse the jsonResponse string obtained in step 2 above into JSONObject to extract the values of
                        "mag","place","time","url"for every earth quake and create corresponding Earthquake objects with them
                        Add each earthquake object to the list(earthquakes) and return it.
                */
            Log.d("RamaKrishna", "fetchEarthquakeData2: ");
            url = new URL(requestUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");
            //Log.d("RamaKrishna", "fetchEarthquakeData3: " + urlConnection.getInputStream());
            if (urlConnection.getResponseCode() == 200 ) {
                InputStream stream = urlConnection.getInputStream();
                InputStreamReader output = new InputStreamReader(stream);
                BufferedReader buffer = new BufferedReader(output);
                StringBuilder result = new StringBuilder();
                String line;
                while((line = buffer.readLine()) != null ) {
                    result.append(line).append(System.getProperty("line.separator"));
                }
                JSONObject jsonObject = new JSONObject(result.toString());
                JSONArray featuresArray = jsonObject.getJSONArray("features");
                Earthquake earthquake = null;
                for(int i = 0; i <featuresArray.length(); i++) {
                    JSONObject properties = featuresArray.getJSONObject(i).getJSONObject("properties");
                    earthquake = new Earthquake(
                            properties.getDouble("mag"),
                            properties.getString("place"),
                            properties.getLong("time"),
                            properties.getString("url")
                    );
                    earthquakes.add(earthquake);
                }
            }
            else
                throw new IOException("GET request error code: " + urlConnection.getResponseCode());
            // Return the list of earthquakes

        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception:  ", e);
        }
        // Return the list of earthquakes
        return earthquakes;
    }
}
