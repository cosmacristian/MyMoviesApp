package com.example.mymovies.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymovies.Api.ApiClient;
import com.example.mymovies.Api.ApiInterface;
import com.example.mymovies.Models.PageResult;
import com.example.mymovies.R;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesScreenFragment extends Fragment {
    Context actualContext;
    ApiInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.favoritescreen_fragment, container, false);
        actualContext = getContext();
        apiInterface = ApiClient.GetClient().create(ApiInterface.class);
        if(savedInstanceState != null){
            return view;
        }
        getPopularMovies();
        return view;
    }

    public void getPopularMovies(){
        Call<PageResult> call = apiInterface.getPopular(1);
        call.enqueue(new Callback<PageResult>() {
            @Override
            public void onResponse(Call<PageResult> call, Response<PageResult> response) {
                Log.e("HOME","OnResponse: "+response.body().total_results);
                for(int i=0;i<20;++i){
                    Log.e("HOME",response.body().results.get(i).title);
                }
            }

            @Override
            public void onFailure(Call<PageResult> call, Throwable t) {
                Log.e("HOME","OnFail: "+t.getLocalizedMessage());
            }
        });
    }
}