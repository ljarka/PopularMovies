package com.github.ljarka.popularmovies.detail.model;

import java.util.List;

public class VideosResult {
    private int id;
    private List<VideoDescriptor> results;

    public int getId() {
        return id;
    }

    public List<VideoDescriptor> getResults() {
        return results;
    }
}
