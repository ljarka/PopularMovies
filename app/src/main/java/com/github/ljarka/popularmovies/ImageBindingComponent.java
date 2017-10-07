package com.github.ljarka.popularmovies;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingComponent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.ljarka.popularmovies.detail.ImageBindingAdapter;

public class ImageBindingComponent implements DataBindingComponent {

    public interface OnImageLoadedListener {
        void onImageLoaded();
    }

    private OnImageLoadedListener onImageLoadedListener;

    public ImageBindingComponent(@NonNull OnImageLoadedListener listener) {
        onImageLoadedListener = listener;
    }

    @Override
    public ImageBindingAdapter getImageBindingAdapter() {
        return (view, url) -> loadImage(view, url);
    }

    private void loadImage(ImageView view, String url) {
        GlideApp.with(view).load(url).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                view.setImageDrawable(resource);
                onImageLoadedListener.onImageLoaded();
            }
        });
    }
}
