package com.github.ljarka.popularmovies.home.network;

import com.github.ljarka.popularmovies.home.model.service.MoviesResult;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MoviesService {

    @GET("popular")
    Observable<MoviesResult> getPopularMovies();

    @GET("top_rated")
    Observable<MoviesResult> getTopRatedMovies();
}
