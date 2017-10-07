package com.github.ljarka.popularmovies.detail.network;

import com.github.ljarka.popularmovies.detail.model.VideosResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VideosService {
    @GET("movie/{movie_id}/videos")
    Observable<VideosResult> getVideos(@Path("movie_id") int movieId);
}
