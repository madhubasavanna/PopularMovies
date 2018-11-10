package com.example.madhupatel.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ReviewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(getString(R.string.reviews));

        Review review = getIntent().getParcelableExtra("reviews");

        TextView author = findViewById(R.id.author);
        author.setText(review.author);

        TextView content = findViewById(R.id.content);
        content.setText(review.content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) { onBackPressed(); }
        else{ return true; }
        return(super.onOptionsItemSelected(item));
    }

}
