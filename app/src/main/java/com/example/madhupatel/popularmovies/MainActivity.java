package com.example.madhupatel.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.ListItemClickListener{

    private MovieListAdapter adapter;
    private static List<Posters> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        movieList = new ArrayList<>();
        adapter = new MovieListAdapter(this, movieList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if(checkInternet()){
            LoadPostersAsyncTask task = new LoadPostersAsyncTask();
            task.execute(1);
        }
        else {
            Toast.makeText(this,"Check the internet connection",Toast.LENGTH_SHORT).show();
        }


    }

    private boolean checkInternet(){
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        return netInfo != null;
    }

    private class LoadPostersAsyncTask extends AsyncTask<Integer, Void, Integer>{

        @Override
        protected Integer doInBackground(Integer... sort) {
            Integer size = 0;
            if(sort.length>0){
                if(1 == sort[0]){
                    movieList.clear();
                    size = preparePosters("1");
                }
                else if(2 == sort[0]){
                    movieList.clear();
                    size = preparePosters("2");
                }
                return size;
            }
            else{
                size = preparePosters("1");
                return size;
            }
        }

        @Override
        protected void onPostExecute(Integer size) {
            if(size<0){
                return;
            }
            else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private static Integer preparePosters(String sort) {
        JSONArray movieJsonList;
        try {
            movieJsonList = NetworkUtils.getResponseFromHttpUrl(sort);
            for(int i=0; i<movieJsonList.length();i++){
                try {
                    JSONObject jsonObject = movieJsonList.getJSONObject(i);
                    //creating movie list
                    Posters temp = new Posters(jsonObject.getString("title"),
                            NetworkUtils.buildUrl(jsonObject.getString("poster_path"),'p')
                            ,jsonObject.getString("vote_average"),
                            NetworkUtils.buildUrl(jsonObject.getString("backdrop_path"),'b')
                            ,jsonObject.getString("release_date"),
                            jsonObject.getString("overview"), jsonObject.getInt("id"));
                    movieList.add(temp);

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }catch (IOException e){ e.printStackTrace();}

        return movieList.size();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LoadPostersAsyncTask task;
        switch (item.getItemId()){
            case R.id.action_popular: {
                if(checkInternet()){
                    task = new LoadPostersAsyncTask();
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,1);
                    getSupportActionBar().setTitle(getString(R.string.app_name));
                }else {
                    Toast.makeText(this,"Check the internet connection",Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case R.id.action_top_rated:{
                if(checkInternet()){
                    task = new LoadPostersAsyncTask();
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,2);
                    getSupportActionBar().setTitle(getString(R.string.top_rated));
                }else {
                    Toast.makeText(this,"Check the internet connection",Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(Posters obj) {
        Intent intent = new Intent(this,MovieDetail.class);
        intent.putExtra("movie_details", obj);
        startActivity(intent);
    }
}