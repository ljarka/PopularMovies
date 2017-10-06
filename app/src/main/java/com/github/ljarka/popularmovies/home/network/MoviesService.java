package com.github.ljarka.popularmovies.home.network;

import static java.lang.annotation.RetentionPolicy.SOURCE;

import android.support.annotation.StringDef;

import com.github.ljarka.popularmovies.home.model.service.MoviesResult;

import java.lang.annotation.Retention;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoviesService {
    @Retention(SOURCE)
    @StringDef({
            POPULAR, TOP_RATED
    })
    @interface SortBy {
    }

    String POPULAR = "popular";
    String TOP_RATED = "top_rated";

    @GET("movie/{sort_by}")
    Observable<MoviesResult> getMovies(@SortBy @Path("sort_by") String sortBy, @Query("page") int page);
}
