package com.github.ljarka.popularmovies.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.ljarka.popularmovies.ImageBindingComponent;
import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.databinding.ActivityDetailBinding;
import com.github.ljarka.popularmovies.detail.model.VideosRecyclerViewAdapter;
import com.github.ljarka.popularmovies.detail.model.ui.VideoDescriptorUi;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;

public class DetailActivity extends AppCompatActivity
        implements ImageBindingComponent.OnImageLoadedListener, VideosRecyclerViewAdapter.OnVideoClickListener {
    private static final String EXTRA_MOVIE_ITEM = "extra_movie_item";
    private ActivityDetailBinding binding;

    @BindingAdapter("imageUri")
    public static void setImageUri(ImageView view, Uri uri) {
        Glide.with(view.getContext()).load(uri).into(view);
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private DetailViewModel viewModel;
    private VideosRecyclerViewAdapter videosRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail, new ImageBindingComponent(this));
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        binding.setMovieItem(getIntent().getParcelableExtra(EXTRA_MOVIE_ITEM));
        supportPostponeEnterTransition();

        videosRecyclerViewAdapter = new VideosRecyclerViewAdapter();
        binding.moviesRecyclerView.setAdapter(videosRecyclerViewAdapter);
        videosRecyclerViewAdapter.setOnVideoClickListener(this);

        loadVideos();
    }

    private void loadVideos() {
        viewModel.getVideos(binding.getMovieItem().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(items -> {
                    videosRecyclerViewAdapter.setItems(items);
                }, throwable -> {

                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent createStartIntent(Context context, @NonNull MovieItemUi movieItemUi) {
        return new Intent(context, DetailActivity.class).putExtra(EXTRA_MOVIE_ITEM, movieItemUi);
    }

    @Override
    public void onImageLoaded() {
        startPostponedEnterTransition();
    }

    @Override
    public void onVideoClick(Uri videoUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, videoUri);
        startActivity(intent);
    }
}
