package com.github.ljarka.popularmovies.detail.model.ui;

public class ReviewUi {

    private String author;
    private String content;

    public ReviewUi(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
