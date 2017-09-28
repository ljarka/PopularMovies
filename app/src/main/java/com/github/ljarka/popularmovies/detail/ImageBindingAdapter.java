package com.github.ljarka.popularmovies.detail;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

public interface ImageBindingAdapter {

    @BindingAdapter("imageUrl")
    void setImageUrl(ImageView view, String url);
}
