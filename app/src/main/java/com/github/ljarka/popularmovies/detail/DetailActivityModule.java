package com.github.ljarka.popularmovies.detail;

import com.github.ljarka.popularmovies.detail.network.ReviewsService;
import com.github.ljarka.popularmovies.detail.network.VideosService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class DetailActivityModule {

    @Provides
    VideosService providePopularMoviesService(Retrofit retrofit) {
        return retrofit.create(VideosService.class);
    }

    @Provides
    ReviewsService provideReviewsService(Retrofit retrofit) {
        return retrofit.create(ReviewsService.class);
    }
}
