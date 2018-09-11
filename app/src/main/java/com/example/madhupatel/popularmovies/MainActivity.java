package com.example.madhupatel.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.ListItemClickListener{

    private RecyclerView recyclerView;
    private MovieListAdapter adapter;
    private List<Posters> albumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup RecyclerView
        recyclerView = findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new MovieListAdapter(this, albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();
    }

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        Posters a = new Posters("True Romance", covers[0]);
        albumList.add(a);

        a = new Posters("Xscpae", covers[1]);
        albumList.add(a);

        a = new Posters("Maroon 5", covers[2]);
        albumList.add(a);

        a = new Posters("Born to Die", covers[3]);
        albumList.add(a);

        a = new Posters("Honeymoon", covers[4]);
        albumList.add(a);

        a = new Posters("I Need a Doctor", covers[5]);
        albumList.add(a);

        a = new Posters("Loud", covers[6]);
        albumList.add(a);

        a = new Posters("Legend", covers[7]);
        albumList.add(a);

        a = new Posters("Hello", covers[8]);
        albumList.add(a);

        a = new Posters("Greatest Hits", covers[9]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(int clickedItemIndex, String title) {
        Intent intent = new Intent(this,MovieDetail.class);
        intent.putExtra("name",title);
        startActivity(intent);
        //Toast.makeText(this,"selected the movie " + title, Toast.LENGTH_LONG).show();
    }
}
