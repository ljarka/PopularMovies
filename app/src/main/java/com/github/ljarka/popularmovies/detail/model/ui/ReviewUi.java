package com.github.ljarka.popularmovies.detail.model.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class ReviewUi implements Parcelable {

    private String author;
    private String content;

    public ReviewUi(String author, String content) {
        this.author = author;
        this.content = content;
    }

    protected ReviewUi(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<ReviewUi> CREATOR = new Creator<ReviewUi>() {
        @Override
        public ReviewUi createFromParcel(Parcel in) {
            return new ReviewUi(in);
        }

        @Override
        public ReviewUi[] newArray(int size) {
            return new ReviewUi[size];
        }
    };

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
    }
}
