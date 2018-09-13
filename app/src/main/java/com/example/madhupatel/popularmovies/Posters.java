package com.example.madhupatel.popularmovies;

public class Posters {
    private String name;
    private String posterUrl;

    public Posters(String name, String url)
    {
        this.name = name;
        this.posterUrl = url;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResourceId(String url) {
        this.posterUrl = url;
    }
}