package com.github.ljarka.popularmovies.home;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.github.ljarka.popularmovies.home.model.service.MovieItem;
import com.github.ljarka.popularmovies.home.model.service.MoviesResult;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;
import com.github.ljarka.popularmovies.home.network.MoviesService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

public class HomeViewModel extends ViewModel {
    private MoviesService moviesService;

    @Inject
    HomeViewModel(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    Observable<List<MovieItemUi>> getPopularMovies() {
        return moviesService.getPopularMovies()
                .compose(transformResultToUiModel());
    }

    Observable<List<MovieItemUi>> getTopRatedMovies() {
        return moviesService.getTopRatedMovies()
                .compose(transformResultToUiModel());
    }

    ObservableTransformer<MoviesResult, List<MovieItemUi>> transformResultToUiModel() {
        return upstream -> upstream.flatMap(popularMoviesResult -> Observable.fromIterable(popularMoviesResult.getResults()))
                .map(HomeViewModel.this::convertItemToUiModel)
                .toList()
                .toObservable();
    }

    @NonNull
    private MovieItemUi convertItemToUiModel(MovieItem movieItem) {
        return new MovieItemUi(createPosterUrl(movieItem));
    }

    private String createPosterUrl(MovieItem movieItem) {
        return "http://image.tmdb.org/t/p/w185" + movieItem.getPosterPath();
    }
}
