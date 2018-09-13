package com.example.madhupatel.popularmovies;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    final static String BASE_URL =
            "http://image.tmdb.org/t/p/";
    final static String MOVIE_URL =
            "http://api.themoviedb.org/3/movie/popular?api_key=";
    final static String apiKey = "";
    final static String PARAM_QUERY = "q";
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";

    public static String buildUrl(String imageUrl){
        String finalUrl = BASE_URL + "w185//" + imageUrl;

        return finalUrl;
    }

    public static JSONArray getResponseFromHttpUrl() throws IOException{

        HttpURLConnection urlConnection = (HttpURLConnection) new URL(MOVIE_URL + apiKey).openConnection();
        try{
            if(urlConnection.getResponseCode() == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                for(String line; (line = reader.readLine()) != null;){
                    builder.append(line).append("\n");
                }
                try {
                    JSONObject jsonObject = new JSONObject(builder.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    return jsonArray;
                }catch (JSONException e){
                    e.printStackTrace();
                    return null;
                }
            }
            else
            {
                Log.i("HTTP request error", urlConnection.getResponseMessage() + urlConnection.getResponseCode());
                return null;
            }

        }
        finally {
            urlConnection.disconnect();
        }
    }
}
