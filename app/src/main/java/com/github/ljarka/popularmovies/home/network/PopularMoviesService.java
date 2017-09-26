package com.github.ljarka.popularmovies.home.network;

import com.github.ljarka.popularmovies.home.model.service.PopularMoviesResult;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface PopularMoviesService {

    @GET("popular")
    Observable<PopularMoviesResult> getPopularMovies();
}
