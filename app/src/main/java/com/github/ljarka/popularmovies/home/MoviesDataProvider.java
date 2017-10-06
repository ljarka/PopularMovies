package com.github.ljarka.popularmovies.home;

import static java.lang.annotation.RetentionPolicy.SOURCE;

import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListProvider;
import android.arch.paging.TiledDataSource;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.github.ljarka.popularmovies.home.model.service.MovieItem;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;
import com.github.ljarka.popularmovies.home.network.MoviesService;

import java.lang.annotation.Retention;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

class MoviesDataProvider extends LivePagedListProvider<Integer, MovieItemUi> {
    @Retention(SOURCE)
    @StringDef({
            SMALL, AVERAGE, BIG, ORIGINAL
    })
    @interface ImageSize {
    }

    private static final String SMALL = "w92";
    private static final String AVERAGE = "w185";
    private static final String BIG = "w500";
    private static final String ORIGINAL = "original";

    private MoviesService moviesService;

    @MoviesService.SortBy
    private String sortBy;

    MoviesDataProvider(@MoviesService.SortBy String sortBy, MoviesService moviesService) {
        this.moviesService = moviesService;
        this.sortBy = sortBy;
    }

    @Override
    protected DataSource<Integer, MovieItemUi> createDataSource() {
        return new TiledDataSource<MovieItemUi>() {
            @Override
            public int countItems() {
                return DataSource.COUNT_UNDEFINED;
            }

            @Override
            public List<MovieItemUi> loadRange(int startPosition, int count) {
                return moviesService.getMovies(sortBy, (startPosition + count) / 20)
                        .flatMap(popularMoviesResult -> Observable.fromIterable(popularMoviesResult.getResults()))
                        .map(this::convertItemToUiModel)
                        .toList()
                        .onErrorReturn(throwable -> Collections.emptyList())
                        .blockingGet();
            }

            @NonNull
            private MovieItemUi convertItemToUiModel(MovieItem movieItem) {
                return MovieItemUi.builder()
                        .withId(movieItem.getId())
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
        };
    }
}
