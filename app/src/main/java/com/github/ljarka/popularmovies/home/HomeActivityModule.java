package com.github.ljarka.popularmovies.home;

import com.github.ljarka.popularmovies.home.network.MoviesService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class HomeActivityModule {

    @Provides
    MoviesService providePopularMoviesService(Retrofit retrofit) {
        return retrofit.create(MoviesService.class);
    }
}
