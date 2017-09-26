package com.github.ljarka.popularmovies.home.model.service;

import java.util.List;
import java.util.Objects;

public class PopularMoviesResult {
    private int page;
    private int totalResults;
    private int totalPages;
    private List<MovieItem> results;

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<MovieItem> getResults() {
        return results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PopularMoviesResult that = (PopularMoviesResult) o;
        return page == that.page &&
                totalResults == that.totalResults &&
                totalPages == that.totalPages &&
                Objects.equals(results, that.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, totalResults, totalPages, results);
    }
}
