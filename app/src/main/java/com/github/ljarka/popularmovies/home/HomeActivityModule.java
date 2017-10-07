package com.github.ljarka.popularmovies.home;

import com.github.ljarka.popularmovies.home.network.MoviesListService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class HomeActivityModule {

    @Provides
    MoviesListService providePopularMoviesService(Retrofit retrofit) {
        return retrofit.create(MoviesListService.class);
    }
}
