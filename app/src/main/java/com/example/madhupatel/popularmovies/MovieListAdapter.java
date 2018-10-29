package com.example.madhupatel.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.NumberViewHolder>{

    private Context context;
    private final List<Posters> movieList;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(Posters object);
    }

    public MovieListAdapter(ListItemClickListener listener, List<Posters> list){
        this.mOnClickListener = listener;
        this.movieList = list;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.listofmovies,viewGroup,false);

        return new NumberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder numberViewHolder, int position) {
        numberViewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView title;
        final ImageView image;
        private Posters poster;

        NumberViewHolder(View view){
            super(view);
            this.title = view.findViewById(R.id.title);
            this.image = view.findViewById(R.id.image);
        }

        void bind(int position){
            poster = movieList.get(position);
            title.setText(poster.getName());
            //Log.i("trailermovielistadapter", poster.getTrailerList().get(0));

            //loading movie poster using picasso library
            Picasso.with(context).load(poster.getPosterUrl()).into(image);

            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnClickListener.onListItemClick(poster);
        }
    }
}
