package com.example.mymovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymovies.Activities.DetailsActivity;
import com.example.mymovies.Models.Image;
import com.example.mymovies.Models.MovieResults;
import com.example.mymovies.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MovieRelatedAdapter extends RecyclerView.Adapter<MovieRelatedAdapter.ImageViewHolder> {
    private List<MovieResults> mDataset;
    private Context actualContext;
    private static ClickListener clickListener;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ImageViewHolder  extends RecyclerView.ViewHolder  implements View.OnClickListener {
        // each data item is just a string in this case
        public ImageView image;
        public TextView name;
        Context actualContext;
        MovieRelatedAdapter movieRelatedAdapter;
        public ImageViewHolder(View v, Context actualContext, MovieRelatedAdapter movieRelatedAdapter) {
            super(v);
            this.actualContext = actualContext;
            this.movieRelatedAdapter = movieRelatedAdapter;
            image = v.findViewById(R.id.imageView_related_image);
            name = v.findViewById(R.id.textView_related_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MovieRelatedAdapter(List<MovieResults> myDataset,Context context) {
        mDataset = myDataset;
        actualContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieRelatedAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movierelatedlistitem, parent, false);
        MovieRelatedAdapter.ImageViewHolder vh = new MovieRelatedAdapter.ImageViewHolder(v,actualContext,this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MovieRelatedAdapter.ImageViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.image.set(mDataset[position]);
        MovieResults movie = mDataset.get(position);
        holder.name.setText(movie.title);
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w200".concat(movie.poster_path)).apply(new RequestOptions().override(200, 200)).placeholder(R.drawable.ic_launcher_background).into(holder.image);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateList(List<MovieResults> data) {
        mDataset = data;
        this.notifyDataSetChanged();
    }


    public void setOnItemClickListener(ClickListener clickListener) {
        MovieRelatedAdapter.clickListener = clickListener;
    }

    public List<MovieResults> getDataset() {
        return mDataset;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }
}