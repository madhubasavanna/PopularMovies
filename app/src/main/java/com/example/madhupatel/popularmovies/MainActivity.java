package com.example.madhupatel.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.ListItemClickListener{

    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private List<Posters> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup RecyclerView
        recyclerView = findViewById(R.id.recycler_view);

        movieList = new ArrayList<>();
        adapter = new MovieListAdapter(this, movieList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        LoadPostersAsyncTask task = new LoadPostersAsyncTask();
        task.execute();
    }

    private class LoadPostersAsyncTask extends AsyncTask<URL, Void, Integer>{
        @Override
        protected Integer doInBackground(URL... urls) {
            int size = preparePosters();
            return size;
        }

        @Override
        protected void onPostExecute(Integer size) {
            if(size<0){ return; }

            adapter.notifyDataSetChanged();
        }
    }

    private int preparePosters() {
        JSONArray movieJsonList;
        try {
            movieJsonList = NetworkUtils.getResponseFromHttpUrl();
            for(int i=0; i<movieJsonList.length();i++){
                try {
                    JSONObject jsonObject = movieJsonList.getJSONObject(i);
                    movieList.add(new Posters(jsonObject.getString("title"), NetworkUtils.buildUrl(jsonObject.getString("poster_path"))));
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) { e.printStackTrace(); }

        return movieList.size();
    }

    @Override
    public void onListItemClick(int clickedItemIndex, String title) {
        Intent intent = new Intent(this,MovieDetail.class);
        intent.putExtra("name",title);
        startActivity(intent);
    }
}
