package com.github.ljarka.popularmovies.detail;

import android.arch.lifecycle.ViewModel;
import android.net.Uri;

import com.github.ljarka.popularmovies.detail.model.VideoDescriptor;
import com.github.ljarka.popularmovies.detail.model.ui.ReviewUi;
import com.github.ljarka.popularmovies.detail.model.ui.VideoDescriptorUi;
import com.github.ljarka.popularmovies.detail.network.ReviewsService;
import com.github.ljarka.popularmovies.detail.network.VideosService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class DetailViewModel extends ViewModel {
    private VideosService videosService;
    private ReviewsService reviewsService;

    @Inject
    public DetailViewModel(VideosService videosService, ReviewsService reviewsService) {
        this.videosService = videosService;
        this.reviewsService = reviewsService;
    }

    Observable<VideoDescriptorUi> getVideos(int movieId) {
        return videosService.getVideos(movieId)
                .flatMap(videosResult -> Observable.fromIterable(videosResult.getResults()).map(this::mapToUiModel));
    }

    Observable<ReviewUi> getReviews(int movieId) {
        return reviewsService.getReviews(movieId)
                .flatMap(reviewsResult -> Observable.fromIterable(reviewsResult.getResults()))
                .map(review -> new ReviewUi(review.getAuthor(), review.getContent()));
    }

    private VideoDescriptorUi mapToUiModel(@NonNull VideoDescriptor videoDescriptor) {
        if ("YouTube".equals(videoDescriptor.getSite())) {
            return new VideoDescriptorUi(videoDescriptor.getName(), buildYoutubeVideoUrl(videoDescriptor.getKey()),
                    buildYoutubeThumbnail(videoDescriptor.getKey()));
        } else {
            return new VideoDescriptorUi("", null, null);
        }
    }

    private Uri buildYoutubeVideoUrl(String key) {
        return new Uri.Builder().scheme("https")
                .authority("www.youtube.com")
                .appendPath("watch")
                .appendQueryParameter("v", key)
                .build();
    }

    private Uri buildYoutubeThumbnail(String key) {
        return new Uri.Builder().scheme("http")
                .authority("img.youtube.com")
                .appendPath("vi")
                .appendPath(key)
                .appendPath("hqdefault.jpg")
                .build();
    }
}
