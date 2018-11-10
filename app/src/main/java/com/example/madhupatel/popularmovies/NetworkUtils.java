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

class NetworkUtils {
    private static final String API_KEY = BuildConfig.API_KEY;
    private final static String BASE_URL =
            "http://image.tmdb.org/t/p/";
    private final static String MOVIE_URL =
            "http://api.themoviedb.org/3/movie/";
    private final static String sortByTopRatings = "top_rated?api_key=";
    private final static String apiKey = API_KEY;
    private final static String sortByPopularity = "popular?api_key=";
    private final static String trailerUrl = "/videos?api_key=";
    private final static String reviewUrl = "/reviews?api_key=";
    private final static String langAndPage = "&language=en-US&page=1";

    public static String buildUrl(String imageUrl,char c){
        String finalUrl;
        switch (c)
        {
           case'p': {
               finalUrl = BASE_URL + "w185//" + imageUrl;
               break;
           }

           case'b': {
               finalUrl = BASE_URL + "w342//" + imageUrl;
               break;
           }

           default: finalUrl = null;
        }
        return finalUrl;
    }

    public static JSONArray getResponseFromHttpUrl(String sortId) throws IOException{
        HttpURLConnection urlConnection;
        switch (sortId){
            case "1":urlConnection = (HttpURLConnection) new URL(MOVIE_URL + sortByPopularity + apiKey).openConnection();
                    break;

            case "2":urlConnection = (HttpURLConnection) new URL(MOVIE_URL + sortByTopRatings + apiKey).openConnection();
                    break;

            default:urlConnection = (HttpURLConnection) new URL(MOVIE_URL + sortByTopRatings + apiKey).openConnection();
        }

        return httpConnection(urlConnection);
    }

    public static JSONArray getResponseFromHttpUrl(int id, String choice) throws IOException{
        HttpURLConnection urlConnection;
        switch (choice){
            case "t": urlConnection = (HttpURLConnection) new URL(MOVIE_URL + id +trailerUrl + apiKey).openConnection();
                        break;
            case "r": urlConnection = (HttpURLConnection) new URL(MOVIE_URL + id +reviewUrl + apiKey + langAndPage).openConnection();
                        break;
            default:urlConnection = null;
        }
        return httpConnection(urlConnection);
    }

    private static JSONArray httpConnection(HttpURLConnection urlConnection)  throws IOException{
        try{
            if(urlConnection.getResponseCode() == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder builder = new StringBuilder();
                for(String line; (line = reader.readLine()) != null;){
                    builder.append(line).append("\n");
                }
                try {
                    JSONObject jsonObject = new JSONObject(builder.toString());

                    return jsonObject.getJSONArray("results");
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
