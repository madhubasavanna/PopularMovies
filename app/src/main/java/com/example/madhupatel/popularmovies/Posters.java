package com.example.madhupatel.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

class Posters implements Parcelable {
    private String name;
    private String posterUrl;
    private String rating;
    private String backDropPath;
    private String overview;
    private String releaseDate;
    private List<Trailer> trailer;
    private int id;

    public int getId() {
        return id;
    }

    private Posters(Parcel in) {
        this.name = in.readString();
        this.posterUrl = in.readString();
        this.rating = in.readString();
        this.backDropPath = in.readString();
        this.releaseDate = in.readString();
        this.overview = in.readString();
        this.id = in.readInt();
        //in.readStringList(this.trailer);
    }

    public Posters(){ }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(posterUrl);
        dest.writeString(rating);
        dest.writeString(backDropPath);
        dest.writeString(releaseDate);
        dest.writeString(overview);
        dest.writeInt(id);
        //dest.writeList(trailer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Posters(String name, String url, String rating, String backDropPath, String releaseDate, String overview, int id)
    {
        this.name = name;
        this.posterUrl = url;
        this.rating = rating;
        this.backDropPath = backDropPath;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.id = id;
    }

    public void setTrailers(List<Trailer> data) { this.trailer = data; }

    public List<Trailer> getTrailerList() { return trailer; }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public static final Parcelable.Creator<Posters> CREATOR
            = new Parcelable.Creator<Posters>() {

        @Override
        public Posters createFromParcel(Parcel in) {
            return new Posters(in);
        }

        @Override
        public Posters[] newArray(int size) {
            return new Posters[size];
        }
    };
}