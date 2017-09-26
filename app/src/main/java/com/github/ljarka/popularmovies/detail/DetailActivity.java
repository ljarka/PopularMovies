package com.github.ljarka.popularmovies.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;

public class DetailActivity extends AppCompatActivity {
    private static final String EXTRA_MOVIE_ITEM = "extra_movie_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }

    public static Intent createStartIntent(Context context, MovieItemUi movieItemUi) {
        return new Intent(context, DetailActivity.class)
                .putExtra(EXTRA_MOVIE_ITEM, movieItemUi);
    }
}
