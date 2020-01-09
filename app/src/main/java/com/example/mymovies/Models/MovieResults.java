package com.example.mymovies.Models;

import android.content.Context;

import java.sql.Date;

import androidx.annotation.DrawableRes;

public class MovieResults {
    public float popularity;
    public int vote_count;
    public boolean video;
    public String poster_path;
    public int id;
    public boolean adult;
    public String backdrop_path;
    public String original_language;
    public String original_title;
    public int[] genre_ids;
    public String title;
    public float vote_average;
    public String overview;
    public String release_date;

    public MovieResults(){

    }

    MovieResults(float popularity,int vote_count,boolean video,String poster_path,int id,boolean adult,String backdrop_path,String original_language,String original_title,int[] genre_ids,String title,float vote_average,String overview,String release_date){
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.poster_path = poster_path;
        this.id = id;
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.genre_ids = genre_ids;
        this.title = title;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
    }

    public MovieResults(int id,String poster_path , String title, String release_date) {
        this.poster_path = poster_path;
        this.id = id;
        this.title = title;
        this.release_date = release_date;
    }
}
