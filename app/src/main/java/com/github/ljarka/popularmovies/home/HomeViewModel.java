package com.github.ljarka.popularmovies.home;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.github.ljarka.popularmovies.home.model.service.MovieItem;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;
import com.github.ljarka.popularmovies.home.network.PopularMoviesService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class HomeViewModel extends ViewModel {
    private PopularMoviesService popularMoviesService;

    @Inject
    public HomeViewModel(PopularMoviesService popularMoviesService) {
        this.popularMoviesService = popularMoviesService;
    }

    public Single<List<MovieItemUi>> getPopularMovies() {
        return popularMoviesService.getPopularMovies()
                .flatMap(popularMoviesResult -> Observable.fromIterable(popularMoviesResult.getResults()))
                .map(this::convertToUiModel)
                .toList();
    }

    @NonNull
    private MovieItemUi convertToUiModel(MovieItem movieItem) {
        return new MovieItemUi(createPosterUrl(movieItem));
    }

    private String createPosterUrl(MovieItem movieItem) {
        return "http://image.tmdb.org/t/p/w185" + movieItem.getPosterPath();
    }
}
