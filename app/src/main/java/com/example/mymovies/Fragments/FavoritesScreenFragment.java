package com.example.mymovies.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.mymovies.Activities.DetailsActivity;
import com.example.mymovies.Adapters.PopularMoviesAdapter;
import com.example.mymovies.Api.ApiClient;
import com.example.mymovies.Api.ApiInterface;
import com.example.mymovies.Models.MovieResults;
import com.example.mymovies.Models.PageResult;
import com.example.mymovies.Models.User;
import com.example.mymovies.R;
import com.example.mymovies.SQLiteDB.SQLiteHelper;

import java.util.List;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesScreenFragment extends Fragment {
    Context actualContext;
    User currentUser;
    List<MovieResults> movies=null;
    GridView gridView;
    SQLiteHelper sqliteHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.favoritescreen_fragment, container, false);
        currentUser = (User) getArguments().getSerializable(
                "currentUser");
        actualContext = getContext();
        sqliteHelper = new SQLiteHelper(getContext());
        gridView = view.findViewById(R.id.gridview_favorite);
        getFavoriteMovies();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                MovieResults movie = movies.get(position);
                Intent detailPage = new Intent(actualContext, DetailsActivity.class);
                detailPage.putExtra("movieId", movie.id);
                startActivity(detailPage);
            }
        });
        return view;
    }

    public void getFavoriteMovies(){

        PageResult result = sqliteHelper.getFavorites(currentUser);
        movies = result.results;
        PopularMoviesAdapter moviesAdapter = new PopularMoviesAdapter(actualContext, movies);
        gridView.setAdapter(moviesAdapter);
        gridView.invalidate();
    }
}