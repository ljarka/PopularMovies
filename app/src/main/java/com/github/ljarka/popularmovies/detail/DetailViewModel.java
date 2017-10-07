package com.github.ljarka.popularmovies.detail;

import android.arch.lifecycle.ViewModel;
import android.net.Uri;

import com.github.ljarka.popularmovies.detail.model.VideoDescriptor;
import com.github.ljarka.popularmovies.detail.model.ui.VideoDescriptorUi;
import com.github.ljarka.popularmovies.detail.network.VideosService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class DetailViewModel extends ViewModel {
    private VideosService videosService;

    @Inject
    public DetailViewModel(VideosService videosService) {
        this.videosService = videosService;
    }

    Observable<VideoDescriptorUi> getVideos(int movieId) {
        return videosService.getVideos(movieId)
                .flatMap(videosResult -> Observable.fromIterable(videosResult.getResults()).map(this::mapToUiModel));
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
