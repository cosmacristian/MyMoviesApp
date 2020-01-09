package com.example.mymovies.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.mymovies.Activities.DetailsActivity;
import com.example.mymovies.Adapters.PopularMoviesAdapter;
import com.example.mymovies.Api.ApiClient;
import com.example.mymovies.Api.ApiInterface;
import com.example.mymovies.Models.MovieResults;
import com.example.mymovies.Models.PageResult;
import com.example.mymovies.Models.User;
import com.example.mymovies.R;
import com.example.mymovies.SQLiteDB.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreenFragment extends Fragment {
    Context actualContext;
    User currentUser;
    ApiInterface apiInterface;
    List<MovieResults> movies=null;
    GridView gridView;
    Button pagenr;
    Button next;
    Button prev;
    int pageNumber = 1;
    int totalPages;
    SQLiteHelper sqliteHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.homescreen_fragment, container, false);
        actualContext = getContext();
        currentUser = (User) getArguments().getSerializable(
                "currentUser");
        sqliteHelper = new SQLiteHelper(getContext());
        apiInterface = ApiClient.GetClient().create(ApiInterface.class);
        if(savedInstanceState != null){
            return view;
        }
        gridView = view.findViewById(R.id.gridview_home);
        pagenr = view.findViewById(R.id.btn_actual_page_nr);
        pagenr.setText((Integer.toString(pageNumber)));
        next = view.findViewById(R.id.btn_home_next);
        prev = view.findViewById(R.id.btn_home_prev);
        getPopularMovies();
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView parent, View view, int position, long id) {
                MovieResults movie = movies.get(position);
                //book.toggleFavorite();
                ImageView star = view.findViewById(R.id.imageview_favorite);
                star.setImageResource(android.R.drawable.btn_star_big_on);
                sqliteHelper.addToFavorites(movie,currentUser);
                // This tells the GridView to redraw itself//todo add to favrites
                // in turn calling your BooksAdapter's getView method again for each cell
                //booksAdapter.notifyDataSetChanged();
                return true;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                MovieResults movie = movies.get(position);
                Intent detailPage = new Intent(actualContext, DetailsActivity.class);
                detailPage.putExtra("movieId", movie.id);
                startActivity(detailPage);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pageNumber < totalPages) {
                    pageNumber += 1;
                    pagenr.setText((Integer.toString(pageNumber)));
                    getPopularMovies();
                }
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pageNumber > 1){
                    pageNumber-=1;
                    pagenr.setText((Integer.toString(pageNumber)));
                    getPopularMovies();
                }
            }
        });

        return view;
    }

    public void getPopularMovies(){
        Call<PageResult> call = apiInterface.getPopular(pageNumber);
        call.enqueue(new Callback<PageResult>() {
            @Override
            public void onResponse(Call<PageResult> call, Response<PageResult> response) {
                Log.e("HOME","OnResponse: "+response.body().total_results);
                /*for(int i=0;i<20;++i){
                    Log.e("HOME",response.body().results.get(i).title);
                }*/
                totalPages = response.body().total_pages;
                movies = response.body().results;
                PopularMoviesAdapter moviesAdapter = new PopularMoviesAdapter(actualContext, movies);
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
