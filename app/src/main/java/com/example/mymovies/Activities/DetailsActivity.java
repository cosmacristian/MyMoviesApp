package com.example.mymovies.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.mymovies.Adapters.MovieImagesAdapter;
import com.example.mymovies.Adapters.MovieRelatedAdapter;
import com.example.mymovies.Adapters.PopularMoviesAdapter;
import com.example.mymovies.Api.ApiClient;
import com.example.mymovies.Api.ApiInterface;
import com.example.mymovies.Models.Image;
import com.example.mymovies.Models.ImageResult;
import com.example.mymovies.Models.MovieDetails;
import com.example.mymovies.Models.MovieResults;
import com.example.mymovies.Models.PageResult;
import com.example.mymovies.Models.VideoResult;
import com.example.mymovies.R;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    int movieId;
    TextView tv_title;
    TextView tv_desc;
    List<Image> posters;
    List<MovieResults> related;
    RecyclerView ImagesListRecycler;
    LinearLayoutManager layoutManager;
    MovieImagesAdapter mAdapter;
    RecyclerView RelatedListRecycler;
    LinearLayoutManager RelatedlayoutManager;
    MovieRelatedAdapter RelatedAdapter;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        apiInterface = ApiClient.GetClient().create(ApiInterface.class);
        Intent intent = getIntent();
        movieId = intent.getIntExtra("movieId",550);
        tv_title = findViewById(R.id.tv_DetailPage_Title);
        tv_desc = findViewById(R.id.tv_DetailPage_Description);
        webView = findViewById(R.id.webView_DetailPage_Video);
        getMovieDetails();
        ImagesListRecycler = findViewById(R.id.recyclerView_DetailPage_Images);
        ImagesListRecycler.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        ImagesListRecycler.setLayoutManager(layoutManager);
        posters = new ArrayList<Image>();
        mAdapter = new MovieImagesAdapter(posters,getApplicationContext());
        ImagesListRecycler.setAdapter(mAdapter);
        getMovieImages();
        getMovieVideo();
        RelatedListRecycler = findViewById(R.id.recyclerView_DetailPage_Related);
        RelatedListRecycler.setHasFixedSize(true);
        RelatedlayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        RelatedListRecycler.setLayoutManager(RelatedlayoutManager);
        related = new ArrayList<MovieResults>();
        RelatedAdapter = new MovieRelatedAdapter(related,getApplicationContext());
        RelatedListRecycler.setAdapter(RelatedAdapter);
        RelatedAdapter.setOnItemClickListener(new MovieRelatedAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                MovieResults movie = RelatedAdapter.getDataset().get(position);
                Intent detailPage = new Intent(DetailsActivity.this, DetailsActivity.class);
                detailPage.putExtra("movieId", movie.id);
                startActivity(detailPage);
            }
        });
        getRelated();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btn_Detail_Back) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getMovieDetails(){
        Call<MovieDetails> call = apiInterface.getMovieDetails(movieId);
        call.enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                Log.e("DETAIL","OnResponse: "+response.body().original_title);
                tv_title.setText(response.body().original_title);
                tv_desc.setText(response.body().overview);
                /*for(int i=0;i<20;++i){
                    Log.e("HOME",response.body().results.get(i).title);
                }*/
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                Log.e("HOME","OnFail: "+t.getLocalizedMessage());
            }
        });
    }

    public void getMovieImages(){
        Call<ImageResult> call = apiInterface.getMovieImages(movieId);
        call.enqueue(new Callback<ImageResult>() {
            @Override
            public void onResponse(Call<ImageResult> call, Response<ImageResult> response) {
                Log.e("DETAIL","OnResponse: "+response.body().id);
                posters = response.body().backdrops;
                MovieImagesAdapter newAdapter = new MovieImagesAdapter(posters,getApplicationContext());
                mAdapter.updateList(posters);
                ImagesListRecycler.swapAdapter(newAdapter,true);
                newAdapter.notifyDataSetChanged();
                Log.i("TAG", Thread.currentThread().getName());
            }

            @Override
            public void onFailure(Call<ImageResult> call, Throwable t) {
                Log.e("HOME","OnFail: "+t.getLocalizedMessage());
            }
        });
    }

    public void getMovieVideo(){
        Call<VideoResult> call = apiInterface.getMovieVideo(movieId);
        call.enqueue(new Callback<VideoResult>() {
            @Override
            public void onResponse(Call<VideoResult> call, Response<VideoResult> response) {
                Log.e("DETAIL","OnResponse: "+response.body().id);
                int height = webView.getHeight();
                int width = webView.getWidth();
                String frameVideo;
                if(response.body().results.size() > 0) {
                    frameVideo = "<html><body><iframe width=\"" + width + "\" height=\"" + height + "\" src=\"https://www.youtube.com/embed/" + response.body().results.get(0).key + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
                }else{
                    frameVideo = "<html><body>There is no YouTube video...</body></html>";
                }
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        return false;
                    }
                });
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webView.loadData(frameVideo, "text/html", "utf-8");

            }

            @Override
            public void onFailure(Call<VideoResult> call, Throwable t) {
                Log.e("HOME","OnFail: "+t.getLocalizedMessage());
            }
        });
    }


    public void getRelated(){
        Call<PageResult> call = apiInterface.getRelated(movieId);
        call.enqueue(new Callback<PageResult>() {
            @Override
            public void onResponse(Call<PageResult> call, Response<PageResult> response) {
                Log.e("DETAIL","OnResponse: "+response.body().page);
                related = response.body().results;
                MovieRelatedAdapter newAdapter = new MovieRelatedAdapter(related,getApplicationContext());
                RelatedAdapter.updateList(related);
                RelatedListRecycler.swapAdapter(newAdapter,true);
                newAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PageResult> call, Throwable t) {
                Log.e("HOME","OnFail: "+t.getLocalizedMessage());
            }
        });
    }
}
