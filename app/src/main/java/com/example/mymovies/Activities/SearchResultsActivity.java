package com.example.mymovies.Activities;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.mymovies.Adapters.PopularMoviesAdapter;
import com.example.mymovies.Api.ApiClient;
import com.example.mymovies.Api.ApiInterface;
import com.example.mymovies.Models.MovieResults;
import com.example.mymovies.Models.PageResult;
import com.example.mymovies.R;

import java.util.List;

public class SearchResultsActivity extends Activity {
    List<MovieResults> foundMovies=null;
    GridView gridView;
    ApiInterface apiInterface;
    int pageNumber=1;
    int totalPages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        apiInterface = ApiClient.GetClient().create(ApiInterface .class);
        gridView = findViewById(R.id.gridview_search);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchMovies(query);
            //use the query to search your data somehow
        }
    }

    public void searchMovies(String query){
        Call<PageResult> call = apiInterface.searchMovie(query,pageNumber);
        call.enqueue(new Callback<PageResult>() {
            @Override
            public void onResponse(Call<PageResult> call, Response<PageResult> response) {
                Log.e("HOME","OnResponse: "+response.body().total_results);
                /*for(int i=0;i<20;++i){
                    Log.e("HOME",response.body().results.get(i).title);
                }*/
                totalPages = response.body().total_pages;
                foundMovies = response.body().results;
                PopularMoviesAdapter moviesAdapter = new PopularMoviesAdapter(getApplicationContext(), foundMovies);
                gridView.setAdapter(moviesAdapter);
                gridView.invalidate();
            }

            @Override
            public void onFailure(Call<PageResult> call, Throwable t) {
                Log.e("HOME","OnFail: "+t.getLocalizedMessage());
            }
        });
    }
}
