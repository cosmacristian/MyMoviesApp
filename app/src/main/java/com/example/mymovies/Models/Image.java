package com.example.mymovies.Models;

public class Image {
    public float aspect_ratio;
    public String file_path;
    public int height;
    public String iso_639_1;
    public float vote_average;
    public int vote_count;
    public int width;

    public Image(float aspect_ratio, String file_path, int height, String iso_639_1, float vote_average, int vote_count, int width) {
        this.aspect_ratio = aspect_ratio;
        this.file_path = file_path;
        this.height = height;
        this.iso_639_1 = iso_639_1;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.width = width;
    }
}


