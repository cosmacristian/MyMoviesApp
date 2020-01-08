package com.example.mymovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymovies.Models.MovieResults;
import com.example.mymovies.R;

import java.util.List;

public class PopularMoviesAdapter extends BaseAdapter {

    private final Context mContext;
    private List<MovieResults> movies;

    // 1
    public PopularMoviesAdapter(Context context, List<MovieResults> movies) {
        this.mContext = context;
        this.movies = movies;
    }

    // 2
    @Override
    public int getCount() {
        return movies.size();
    }
/*
    public void setNewDataset(List<MovieResults> newest){
        movies = newest;
    }*/

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MovieResults movie = movies.get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.movieresult_card, null);
        }

        // 3
        final ImageView imageView = convertView.findViewById(R.id.imageview_cover_art);
        final TextView titleTextView = convertView.findViewById(R.id.textview_title);
        final TextView dateTextView = convertView.findViewById(R.id.textview_release_date);
        final ImageView imageViewFavorite = convertView.findViewById(R.id.imageview_favorite);

        // 4
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w200".concat(movie.poster_path)).apply(new RequestOptions().override(200, 200)).into(imageView);
        //imageView.setImageResource(book.getImageResource(mContext));
        titleTextView.setText(movie.title);
        dateTextView.setText(movie.release_date);

        return convertView;
    }

}