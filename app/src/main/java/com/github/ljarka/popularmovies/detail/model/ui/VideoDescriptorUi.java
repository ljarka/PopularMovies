package com.github.ljarka.popularmovies.detail.model.ui;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class VideoDescriptorUi implements Parcelable {
    private String name;
    private Uri videoUri;
    private Uri imageUri;

    public VideoDescriptorUi(String name, Uri videoUri, Uri imageUri) {
        this.name = name;
        this.videoUri = videoUri;
        this.imageUri = imageUri;
    }

    protected VideoDescriptorUi(Parcel in) {
        name = in.readString();
        videoUri = in.readParcelable(Uri.class.getClassLoader());
        imageUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<VideoDescriptorUi> CREATOR = new Creator<VideoDescriptorUi>() {
        @Override
        public VideoDescriptorUi createFromParcel(Parcel in) {
            return new VideoDescriptorUi(in);
        }

        @Override
        public VideoDescriptorUi[] newArray(int size) {
            return new VideoDescriptorUi[size];
        }
    };

    public String getName() {
        return name;
    }

    public Uri getVideoUri() {
        return videoUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeParcelable(videoUri, flags);
        dest.writeParcelable(imageUri, flags);
    }
}
