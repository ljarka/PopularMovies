package com.github.ljarka.popularmovies.home.model.ui;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieItemUi implements Parcelable {
    private int id;
    private String poster;
    private String title;
    private String overview;
    private String userRating;
    private String releaseDate;
    private String backdrop;

    private MovieItemUi(int id, String poster, String title, String overview, String userRating, String releaseDate,
            String backdrop) {
        this.id = id;
        this.poster = poster;
        this.title = title;
        this.overview = overview;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.backdrop = backdrop;
    }

    protected MovieItemUi(Parcel in) {
        id = in.readInt();
        poster = in.readString();
        title = in.readString();
        overview = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
        backdrop = in.readString();
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

    public String getBackdrop() {
        return backdrop;
    }

    public int getId() {
        return id;
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
        parcel.writeInt(id);
        parcel.writeString(poster);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(userRating);
        parcel.writeString(releaseDate);
        parcel.writeString(backdrop);
    }

    public static class Builder {
        private int id;
        private String poster;
        private String title;
        private String overview;
        private String userRating;
        private String releaseDate;
        private String backdrop;

        public Builder withId(int id) {
            this.id = id;
            return this;
        }

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

        public Builder withBackdrop(String backdrop) {
            this.backdrop = backdrop;
            return this;
        }

        public MovieItemUi build() {
            return new MovieItemUi(id, poster, title, overview, userRating, releaseDate, backdrop);
        }
    }
}
