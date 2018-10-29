package com.example.madhupatel.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TrailerAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Trailer> mTrailers;

    TrailerAdapter(Context context){
        this.context = context;
        this.mTrailers = new ArrayList<>();
    }

    private void clear(){
        mTrailers.clear();
        notifyDataSetChanged();
    }

    void setTrailers(ArrayList<Trailer> trailers){
        clear();
        mTrailers.addAll(trailers);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View trailerItem = view;
        Trailer trailer = getItem(i);
        if(trailerItem==null){
            try {
                LayoutInflater vi;
                vi = LayoutInflater.from(context);
                trailerItem = vi.inflate(R.layout.trailer_list,viewGroup,false);

            }catch (Exception e){
                Log.e(context.getClass().getSimpleName(),e.toString());
            }
        }
        if(trailerItem!=null){
            ((TextView) trailerItem.findViewById(R.id.trailer1)).setText(trailer.name);
        }
        return trailerItem;
    }

    Uri getTrailerUri(int position){
        Trailer trailer = getItem(position);
        if (trailer!=null){
            return Uri.parse(trailer.url);
        }
        return null;
    }

    @Override
    public Trailer getItem(int i) {
        return mTrailers.get(i);
    }

    @Override
    public long getItemId(int i) {
        if (getItem(i) == null){
            return -1L;
        }
        return i;
    }

    @Override
    public int getCount() {
        return mTrailers.size();
    }
}
