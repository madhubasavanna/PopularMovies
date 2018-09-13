package com.example.madhupatel.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.NumberViewHolder>{

    private Context context;
    private List<Posters> moviewList;
    private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex, String title);
    }

    public MovieListAdapter(ListItemClickListener listener, List<Posters> list){
        this.mOnClickListener = listener;
        this.moviewList = list;
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
        return moviewList.size();
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title;
        public ImageView image;
        private Posters poster;

        public NumberViewHolder(View view){
            super(view);
            this.title = view.findViewById(R.id.title);
            this.image = view.findViewById(R.id.image);
        }

        void bind(int position){
            poster = moviewList.get(position);
            title.setText(poster.getName());

            //loading movie poster using picasso library
            Picasso.with(context).load(poster.getPosterUrl()).into(image);

            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition, poster.getName());
        }
    }
}
