package com.github.ljarka.popularmovies.detail.network;

import com.github.ljarka.popularmovies.detail.model.ReviewsResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReviewsService {
    @GET("movie/{movie_id}/reviews")
    Observable<ReviewsResult> getReviews(@Path("movie_id") int movieId);
}
