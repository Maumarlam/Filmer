package com.example.filmer2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import Utilities.NetworkUtils;
import Utilities.NetworkUtils.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String BASE_URL ="https://image.tmdb.org/t/p/w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();
        Movie mov_intent = (Movie)        intent.getSerializableExtra("detail");
    }

}
