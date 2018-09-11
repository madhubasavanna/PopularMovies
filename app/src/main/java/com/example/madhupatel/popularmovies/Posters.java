package com.example.madhupatel.popularmovies;

public class Posters {
    private String name;
    private int moviePoster;

    public Posters(String name, int id)
    {
        this.name = name;
        this.moviePoster = id;
    }

    public int getResourceId() {
        return moviePoster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResourceId(int resourceId) {
        this.moviePoster = resourceId;
    }
}