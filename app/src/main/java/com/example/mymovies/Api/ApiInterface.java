package com.example.mymovies.Api;

import com.example.mymovies.Models.PageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/3/movie/popular?api_key=891e5edb9ec5a92e0d2274135b1926af&language=en-US")
    Call<PageResult> getPopular(@Query("page") int page);
}
