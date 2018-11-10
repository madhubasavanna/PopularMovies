package com.example.madhupatel.popularmovies;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.madhupatel.popularmovies.Favorites_db.FavoritesDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MovieDetail extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private final String TAG = this.getClass().getSimpleName();
    Posters mPoster;
    String movieName;
    String backDrop;
    String ratingString;
    String overview;
    String poster;
    String release_date;
    ListView mTrailersListView;
    Button btnReviews;
    ImageView btn_star;

    ArrayList<Trailer> mTrailers;
    Review mReview;

    TrailerAdapter trailersAdapter;

    private static final int LOADER_ID = 517;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        trailersAdapter = new TrailerAdapter(this);
        mTrailersListView = findViewById(R.id.trailer_list);
        mTrailersListView.setAdapter(trailersAdapter);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(getString(R.string.movie_details));

        mPoster = getIntent().getParcelableExtra("movie_details");

        movieName = mPoster.getName();
        backDrop = mPoster.getBackDropPath();
        ratingString = mPoster.getRating();
        overview = mPoster.getOverview();
        poster = mPoster.getPosterUrl();
        release_date = mPoster.getReleaseDate();

        TextView title = findViewById(R.id.movie_name);
        title.setText(movieName);

        ImageView imageView = findViewById(R.id.backdrop);
        Picasso.with(this).load(backDrop).into(imageView);

        TextView rating = findViewById(R.id.rating);
        rating.setText(ratingString);

        TextView filmOverview = findViewById(R.id.overview);
        filmOverview.setText(overview);

        TextView releaseDate = findViewById(R.id.release_date);
        releaseDate.setText(release_date);

        ImageView posterUrl = findViewById(R.id.poster);
        Picasso.with(this).load(poster).into(posterUrl);

        Bundle args = new Bundle();


        getLoaderManager().restartLoader(LOADER_ID, null, this);

        mTrailersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = trailersAdapter.getTrailerUri(position);

                if (uri != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public Loader<Object> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<Object>(this){
            @Override
            protected void onStartLoading() { forceLoad(); }

            @Override
            public Void loadInBackground() {
                mTrailers = new ArrayList<>();
                try {
                    JSONArray jsontrailer = NetworkUtils.getResponseFromHttpUrl(mPoster.getId(),"t");
                    JSONArray jsonreview = NetworkUtils.getResponseFromHttpUrl(mPoster.getId(),"r");
                    for(int i=0; i<jsontrailer.length(); i++) {
                        JSONObject jsonObject = jsontrailer.getJSONObject(i);
                        mTrailers.add(new Trailer(jsonObject.getString("name"), "https://www.youtube.com/watch?v=" +jsonObject.getString("key")));
                    }

                    if(jsonreview.length()<=0){
                        mReview = new Review("no author","no reviews");
                    }else {
                        JSONObject jsonObject = jsonreview.getJSONObject(0);
                        mReview = new Review(jsonObject.getString("author"), jsonObject.getString("content"));
                    }

                }catch (IOException | JSONException e) {e.printStackTrace(); }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Object o) {
        mPoster.setTrailers(mTrailers);
        mPoster.setReview(mReview);
        btnReviews = findViewById(R.id.btn_review);
        btnReviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MovieDetail.this,ReviewsActivity.class);
                intent.putExtra("reviews",mReview);
                startActivity(intent);
            }
        });
        btn_star = findViewById(R.id.star);
        btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoritesDatabase mfavoritesDb = FavoritesDatabase.getsInstance(getApplicationContext());
                if(mfavoritesDb.favoritesDao().isBookmarked(mPoster.getId()) > 0){
                    btn_star.setImageResource(android.R.drawable.btn_star_big_off);
                    //args.putBoolean("local",true);
                }
                else {
                    btn_star.setImageResource(android.R.drawable.btn_star_big_on);
                }
            }
        });
        if (mTrailers!=null) {
            trailersAdapter.setTrailers(mTrailers);
            setListViewHeightBasedOnChildren(mTrailersListView);
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {

        TrailerAdapter listAdapter = (TrailerAdapter) listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int elements = listAdapter.getCount();
        Log.d("AAA","Got " + elements + " elements");

        if (elements>0) {
            View listItem = listAdapter.getView(0, null, listView);
            listItem.measure(0,0);
            // get the height of a single item, multiply by the number of items and get the total height for the item,
            // extra space (more elements) is added
            int totalHeight = listItem.getMeasuredHeight() * (elements+2);

            ViewGroup.LayoutParams params = listView.getLayoutParams();

            //calculate the total height summing the height of the dividers too
            params.height = totalHeight
                    + (listView.getDividerHeight() * (listAdapter.getCount()-1));

            //set the height
            listView.setLayoutParams(params);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) { }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) { onBackPressed(); }
        else{ return true; }
        return(super.onOptionsItemSelected(item));
    }
}
