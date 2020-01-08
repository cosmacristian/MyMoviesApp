package com.example.mymovies.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.mymovies.Api.ApiClient;
import com.example.mymovies.Api.ApiInterface;
import com.example.mymovies.Fragments.FavoritesScreenFragment;
import com.example.mymovies.Fragments.HomeScreenFragment;
import com.example.mymovies.Fragments.NowInCinemaScreenFragment;
import com.example.mymovies.Models.PageResult;
import com.example.mymovies.Models.User;
import com.example.mymovies.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    User currentUser;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    HomeScreenFragment fragmenthome = new HomeScreenFragment();
                    fragmentTransaction.replace(R.id.fragment_container, fragmenthome);
                    fragmentTransaction.commit();

                    return true;
                case R.id.navigation_dashboard:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    FavoritesScreenFragment fragmentfav = new FavoritesScreenFragment();
                    fragmentTransaction.replace(R.id.fragment_container, fragmentfav);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    fragmentTransaction = fragmentManager.beginTransaction();
                    NowInCinemaScreenFragment fragmentcine = new NowInCinemaScreenFragment();
                    fragmentTransaction.replace(R.id.fragment_container, fragmentcine);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");
        fragmentManager = getSupportFragmentManager();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        fragmentTransaction = fragmentManager.beginTransaction();
        HomeScreenFragment fragment = new HomeScreenFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE); //Ra iratkozni az
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Menu","dfnsidfise");
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Log.e("Menu","dfnsidfise");
            }
        });

        MenuItem profileButton = menu.findItem(R.id.menu_profile);
        profileButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent=new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("currentUser", currentUser);
                startActivity(intent);
                return false;
            }
        });
/*
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/
        return true;
    }

}
