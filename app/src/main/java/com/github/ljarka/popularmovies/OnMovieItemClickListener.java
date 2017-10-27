package com.github.ljarka.popularmovies;

import android.view.View;

import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;

public interface OnMovieItemClickListener {
    void onMovieItemClick(MovieItemUi movieItemUi, View view);
}