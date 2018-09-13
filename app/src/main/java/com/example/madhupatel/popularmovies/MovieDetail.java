package com.example.madhupatel.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        String movieName = getIntent().getStringExtra("name");

        TextView title = findViewById(R.id.movie_name);
        title.setText(movieName);

    }
}
