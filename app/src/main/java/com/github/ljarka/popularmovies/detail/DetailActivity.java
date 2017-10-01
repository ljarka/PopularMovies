package com.github.ljarka.popularmovies.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.github.ljarka.popularmovies.ImageBindingComponent;
import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.databinding.ActivityDetailBinding;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;

public class DetailActivity extends AppCompatActivity implements ImageBindingComponent.OnImageLoadedListener {
    private static final String EXTRA_MOVIE_ITEM = "extra_movie_item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail, new ImageBindingComponent(this));
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        binding.setMovieItem(getIntent().getParcelableExtra(EXTRA_MOVIE_ITEM));
        supportPostponeEnterTransition();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent createStartIntent(Context context, @NonNull MovieItemUi movieItemUi) {
        return new Intent(context, DetailActivity.class).putExtra(EXTRA_MOVIE_ITEM, movieItemUi);
    }

    @Override
    public void onImageLoaded() {
        startPostponedEnterTransition();
    }
}
