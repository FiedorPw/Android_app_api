package com.example.myapplication.models;

import java.io.Serializable;

public class Movie implements Serializable {
    private final String title;
    private final String posterUrl;
    private final String releaseDate;
    private final String overview;

    public Movie(String title, String posterUrl, String releaseDate, String overview) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.overview = overview;
    }

    public String getTitle() { return title; }
    public String getPosterUrl() { return posterUrl; }
    public String getReleaseDate() { return releaseDate; }
    public String getOverview() { return overview; }
}
