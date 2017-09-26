package com.github.ljarka.popularmovies.home.model.service;

import java.util.Arrays;
import java.util.Objects;

public class MovieItem {
    private int voteCount;
    private int id;
    private boolean video;
    private float voteAverage;
    private String title;
    private double popularity;
    private String posterPath;
    private String originalLanguage;
    private String originalTitle;
    private int[] genreIds;
    private String backdropPath;
    private boolean adult;
    private String overview;
    private String releaseDate;

    public int getVoteCount() {
        return voteCount;
    }

    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieItem movieItem = (MovieItem) o;
        return voteCount == movieItem.voteCount &&
                id == movieItem.id &&
                video == movieItem.video &&
                Float.compare(movieItem.voteAverage, voteAverage) == 0 &&
                Double.compare(movieItem.popularity, popularity) == 0 &&
                adult == movieItem.adult &&
                Objects.equals(title, movieItem.title) &&
                Objects.equals(posterPath, movieItem.posterPath) &&
                Objects.equals(originalLanguage, movieItem.originalLanguage) &&
                Objects.equals(originalTitle, movieItem.originalTitle) &&
                Arrays.equals(genreIds, movieItem.genreIds) &&
                Objects.equals(backdropPath, movieItem.backdropPath) &&
                Objects.equals(overview, movieItem.overview) &&
                Objects.equals(releaseDate, movieItem.releaseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(voteCount, id, video, voteAverage, title, popularity, posterPath,
                originalLanguage, originalTitle, genreIds, backdropPath, adult, overview, releaseDate);
    }
}
