package com.github.ljarka.popularmovies.dagger;

import com.github.ljarka.popularmovies.home.network.PopularMoviesService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainActivityModule {

    @Provides
    PopularMoviesService providePopularMoviesService(Retrofit retrofit) {
        return retrofit.create(PopularMoviesService.class);
    }
}
