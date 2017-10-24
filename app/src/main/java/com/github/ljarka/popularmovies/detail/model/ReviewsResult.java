package com.github.ljarka.popularmovies.detail.model;

import java.util.List;

public class ReviewsResult {
    private int id;
    private int page;
    private List<Review> results;
    private int totalPages;
    private int totalResults;

    public int getId() {
        return id;
    }

    public int getPage() {
        return page;
    }

    public List<Review> getResults() {
        return results;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}