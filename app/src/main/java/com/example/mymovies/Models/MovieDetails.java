package com.example.mymovies.Models;

import java.util.List;

public class MovieDetails {
    public boolean adult;
    public String backdrop_path;
    public Collection belongs_to_collection;
    public long budget;
    public List<Genre> genres;
    public String homepage;
    public int id;
    public String imdb_id;
    public String original_language;
    public String original_title;
    public String overview;
    public float popularity;
    public String poster_path;
    public List<ProductionCompany> production_companies;
    public String release_date;
    public long revenue;
    public int runtime;
    public List<Language> spoken_languages;
    public String status;
    public String tagline;
    public String title;
    public boolean video;
    public float vote_average;
    public long vote_count;

    public MovieDetails(boolean adult, String backdrop_path, Collection belongs_to_collection, long budget, List<Genre> genres, String homepage, int id, String imdb_id, String original_language, String original_title, String overview, float popularity, String poster_path, List<ProductionCompany> production_companies, String release_date, long revenue, int runtime, List<Language> spoken_languages, String status, String tagline, String title, boolean video, float vote_average, long vote_count) {
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.belongs_to_collection = belongs_to_collection;
        this.budget = budget;
        this.genres = genres;
        this.homepage = homepage;
        this.id = id;
        this.imdb_id = imdb_id;
        this.original_language = original_language;
        this.original_title = original_title;
        this.overview = overview;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.production_companies = production_companies;
        this.release_date = release_date;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spoken_languages = spoken_languages;
        this.status = status;
        this.tagline = tagline;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
    }
}
