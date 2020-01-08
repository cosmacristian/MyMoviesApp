package com.example.mymovies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymovies.Models.Image;
import com.example.mymovies.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MovieImagesAdapter extends RecyclerView.Adapter<MovieImagesAdapter.ImageViewHolder> {
    private List<Image> mDataset;
    private Context actualContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView image;
        Context actualContext;
        MovieImagesAdapter movieImagesAdapter;
        public ImageViewHolder(View v, Context actualContext, MovieImagesAdapter movieImagesAdapter) {
            super(v);
            this.actualContext = actualContext;
            this.movieImagesAdapter = movieImagesAdapter;
            image = v.findViewById(R.id.imageView_ListItemImage);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MovieImagesAdapter(List<Image> myDataset,Context context) {
        mDataset = myDataset;
        actualContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MovieImagesAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movieimagelistitem, parent, false);
        ImageViewHolder vh = new ImageViewHolder(v,actualContext,this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.image.set(mDataset[position]);
        Image image = mDataset.get(position);
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w300".concat(image.file_path)).apply(new RequestOptions().override(300, 300)).placeholder(R.drawable.ic_launcher_background).into(holder.image);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void updateList(List<Image> data) {
        mDataset = data;
        this.notifyDataSetChanged();
    }
}