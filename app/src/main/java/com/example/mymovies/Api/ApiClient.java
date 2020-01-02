package com.example.mymovies.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String baseURL = "https://api.themoviedb.org/";
    private static Retrofit retrofit = null;

    public static Retrofit GetClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit;
    }
}
