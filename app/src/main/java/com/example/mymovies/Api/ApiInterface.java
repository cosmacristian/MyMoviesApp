package com.example.mymovies.Api;

import com.example.mymovies.Models.ImageResult;
import com.example.mymovies.Models.MovieDetails;
import com.example.mymovies.Models.MovieResults;
import com.example.mymovies.Models.PageResult;
import com.example.mymovies.Models.VideoResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/3/movie/popular?api_key=891e5edb9ec5a92e0d2274135b1926af&language=en-US")
    Call<PageResult> getPopular(@Query("page") int page);

    @GET("/3/search/movie?api_key=891e5edb9ec5a92e0d2274135b1926af&language=en-US")
    Call<PageResult> searchMovie(@Query("query") String query,@Query("page") int page);

    @GET("/3/movie/{Id}?api_key=891e5edb9ec5a92e0d2274135b1926af&language=en-US")
    Call<MovieDetails> getMovieDetails(@Path("Id") int id);

    @GET("/3/movie/{movie_id}/images?api_key=891e5edb9ec5a92e0d2274135b1926af")
    Call<ImageResult> getMovieImages(@Path("movie_id") int id);

    @GET("/3/movie/{movie_id}/videos?api_key=891e5edb9ec5a92e0d2274135b1926af")
    Call<VideoResult> getMovieVideo(@Path("movie_id") int id);

    @GET("/3/movie/{movie_id}/recommendations?api_key=891e5edb9ec5a92e0d2274135b1926af&page=1")
    Call<PageResult> getRelated(@Path("movie_id") int id);
}
