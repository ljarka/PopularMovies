package com.github.ljarka.popularmovies.home;

import static java.lang.annotation.RetentionPolicy.SOURCE;

import android.arch.lifecycle.ViewModel;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import com.github.ljarka.popularmovies.home.model.service.MovieItem;
import com.github.ljarka.popularmovies.home.model.service.MoviesResult;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;
import com.github.ljarka.popularmovies.home.network.MoviesService;

import java.lang.annotation.Retention;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

public class HomeViewModel extends ViewModel {
    @Retention(SOURCE)
    @StringDef({
            SMALL, AVERAGE, BIG, ORIGINAL
    })
    public @interface ImageSize {
    }

    private static final String SMALL = "w92";
    private static final String AVERAGE = "w185";
    private static final String BIG = "w500";
    private static final String ORIGINAL = "original";

    private MoviesService moviesService;

    @Inject
    HomeViewModel(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    Observable<List<MovieItemUi>> getPopularMovies() {
        return moviesService.getPopularMovies().compose(transformResultToUiModel());
    }

    Observable<List<MovieItemUi>> getTopRatedMovies() {
        return moviesService.getTopRatedMovies().compose(transformResultToUiModel());
    }

    private ObservableTransformer<MoviesResult, List<MovieItemUi>> transformResultToUiModel() {
        return upstream -> upstream.flatMap(popularMoviesResult -> Observable.fromIterable(popularMoviesResult.getResults()))
                .map(HomeViewModel.this::convertItemToUiModel)
                .toList()
                .toObservable();
    }

    @NonNull
    private MovieItemUi convertItemToUiModel(MovieItem movieItem) {
        return MovieItemUi.builder()
                .withPoster(buildImageUrlFromPath(movieItem.getPosterPath(), AVERAGE))
                .withTitle(movieItem.getTitle())
                .withOverview(movieItem.getOverview())
                .withReleaseDate(movieItem.getReleaseDate())
                .withUserRating(String.valueOf(movieItem.getVoteAverage()))
                .withBackdrop(buildImageUrlFromPath(movieItem.getBackdropPath(), BIG))
                .build();
    }

    private String buildImageUrlFromPath(String path, @ImageSize String imageSize) {
        return new Uri.Builder().scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendEncodedPath(imageSize + path)
                .build()
                .toString();
    }
}
