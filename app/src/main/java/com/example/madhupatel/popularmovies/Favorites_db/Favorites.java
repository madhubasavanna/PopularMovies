package com.example.madhupatel.popularmovies.Favorites_db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Blob;

@Entity
public class Favorites {

    @PrimaryKey
    private int id;
    private String title;
    private String overview;
    //@ColumnInfo(name = "poster_back_drop")
    //private Blob posterBackDrop;
    //private Blob poster;
    @ColumnInfo(name = "poster_back_drop_path")
    private String posterBackDropPath;
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    private String release_date;
    private int rating;
    private String trailers;
    private String reviews;

    public String getPosterBackDropPath() {
        return posterBackDropPath;
    }

    public void setPosterBackDropPath(String posterBackDropPath) {
        this.posterBackDropPath = posterBackDropPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

//    public Blob getPosterBackDrop() {
//        return posterBackDrop;
//    }
//
//    public void setPosterBackDrop(Blob posterBackDrop) {
//        this.posterBackDrop = posterBackDrop;
//    }

//    public Blob getPoster() {
//        return poster;
//    }
//
//    public void setPoster(Blob poster) {
//        this.poster = poster;
//    }

    public String getPoster_back_drop_path() {
        return posterBackDropPath;
    }

    public void setPoster_back_drop_path(String poster_back_drop_path) {
        this.posterBackDropPath = poster_back_drop_path;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTrailers() {
        return trailers;
    }

    public void setTrailers(String trailers) {
        this.trailers = trailers;
    }

    public String getReviews() {
        return reviews;
    }

    public void setReviews(String reviews) {
        this.reviews = reviews;
    }

    public Favorites(int id, String title, String overview, String poster_back_drop_path, String poster_path,
                     int rating, String trailers, String release_date, String reviews) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.posterBackDropPath = poster_back_drop_path;
        this.rating = rating;
        this.trailers = trailers;
        this.release_date = release_date;
        this.reviews = reviews;
    }
}
