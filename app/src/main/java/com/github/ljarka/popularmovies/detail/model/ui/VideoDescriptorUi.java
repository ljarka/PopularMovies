package com.github.ljarka.popularmovies.detail.model.ui;

import android.net.Uri;

public class VideoDescriptorUi {
    private String name;
    private Uri videoUri;
    private Uri imageUri;

    public VideoDescriptorUi(String name, Uri videoUri, Uri imageUri) {
        this.name = name;
        this.videoUri = videoUri;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public Uri getVideoUri() {
        return videoUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }
}
