package com.github.ljarka.popularmovies.home.model.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieItemUi implements Parcelable {

    private String poster;
    private String title;
    private String overview;
    private String userRating;
    private String releaseDate;

    private MovieItemUi(String poster, String title, String overview, String userRating,
                        String releaseDate) {
        this.poster = poster;
        this.title = title;
        this.overview = overview;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    protected MovieItemUi(Parcel in) {
        poster = in.readString();
        title = in.readString();
        overview = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<MovieItemUi> CREATOR = new Creator<MovieItemUi>() {
        @Override
        public MovieItemUi createFromParcel(Parcel in) {
            return new MovieItemUi(in);
        }

        @Override
        public MovieItemUi[] newArray(int size) {
            return new MovieItemUi[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(userRating);
        parcel.writeString(releaseDate);
    }

    public static class Builder {
        private String poster;
        private String title;
        private String overview;
        private String userRating;
        private String releaseDate;

        public Builder withPoster(String poster) {
            this.poster = poster;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withOverview(String overview) {
            this.overview = overview;
            return this;
        }

        public Builder withUserRating(String userRating) {
            this.userRating = userRating;
            return this;
        }

        public Builder withReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public MovieItemUi build() {
            return new MovieItemUi(poster, title, overview, userRating, releaseDate);
        }
    }
}
