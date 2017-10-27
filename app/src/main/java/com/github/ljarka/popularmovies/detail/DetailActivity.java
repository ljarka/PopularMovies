package com.github.ljarka.popularmovies.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.ljarka.popularmovies.ImageBindingComponent;
import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.databinding.ActivityDetailBinding;
import com.github.ljarka.popularmovies.home.favorites.MoviesContract;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;
import com.like.LikeButton;
import com.like.OnLikeListener;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    private ReviewsRecyclerViewAdapter reviewsRecyclerViewAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MovieItemUi movieItem;

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

        movieItem = getIntent().getParcelableExtra(EXTRA_MOVIE_ITEM);
        binding.setMovieItem(movieItem);
        supportPostponeEnterTransition();

        videosRecyclerViewAdapter = new VideosRecyclerViewAdapter();
        binding.moviesRecyclerView.setAdapter(videosRecyclerViewAdapter);
        videosRecyclerViewAdapter.setOnVideoClickListener(this);

        loadVideos();

        reviewsRecyclerViewAdapter = new ReviewsRecyclerViewAdapter();
        binding.reviewsRecyclerView.setAdapter(reviewsRecyclerViewAdapter);
        binding.reviewsRecyclerView.setNestedScrollingEnabled(false);
        loadReviews();
        initStarButton();
    }

    private void initStarButton() {
        binding.starButton.setLiked(isFavorite());
        binding.starButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                addMovieToDatabase(movieItem);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                removeMovieFromDatabase(movieItem.getId(), movieItem.getTitle());
            }
        });
    }

    private boolean isFavorite() {
        Cursor cursor = getContentResolver().query(MoviesContract.FavoriteMovies.CONTENT_URI, null,
                MoviesContract.FavoriteMovies.ID + "= ?", new String[] {
                        String.valueOf(movieItem.getId())
                }, null);

        if (cursor != null) {
            try {
                return cursor.getCount() > 0;
            } finally {
                cursor.close();
            }
        } else {
            return false;
        }
    }

    private void removeMovieFromDatabase(int movieId, String movieTitle) {
        getContentResolver().delete(MoviesContract.FavoriteMovies.CONTENT_URI, MoviesContract.FavoriteMovies.ID + "= ?",
                new String[] {
                        String.valueOf(movieId)
                });
        Snackbar.make(binding.getRoot(), getString(R.string.move_removed_from_favorites, movieTitle), Snackbar.LENGTH_LONG)
                .show();
    }

    private void addMovieToDatabase(MovieItemUi movieItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MoviesContract.FavoriteMovies.ID, movieItem.getId());
        contentValues.put(MoviesContract.FavoriteMovies.BACKDROP, movieItem.getBackdrop());
        contentValues.put(MoviesContract.FavoriteMovies.OVERVIEW, movieItem.getOverview());
        contentValues.put(MoviesContract.FavoriteMovies.POSTER, movieItem.getPoster());
        contentValues.put(MoviesContract.FavoriteMovies.TITLE, movieItem.getTitle());
        contentValues.put(MoviesContract.FavoriteMovies.RELEASE_DATE, movieItem.getReleaseDate());
        contentValues.put(MoviesContract.FavoriteMovies.USER_RATING, movieItem.getUserRating());
        getContentResolver().insert(MoviesContract.FavoriteMovies.buildMovieUri(movieItem.getId()), contentValues);
        Snackbar.make(binding.getRoot(), getString(R.string.movie_added_to_favorites, movieItem.getTitle()), Snackbar.LENGTH_LONG)
                .setAction(R.string.show_button, v -> {

                })
                .show();
    }

    private void loadReviews() {
        Disposable disposable = viewModel.getReviews(binding.getMovieItem().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(items -> {
                    reviewsRecyclerViewAdapter.setItems(items);
                }, throwable -> {
                    binding.reviewsRecyclerView.setVisibility(View.GONE);
                });
        compositeDisposable.add(disposable);
    }

    private void loadVideos() {
        Disposable disposable = viewModel.getVideos(binding.getMovieItem().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .subscribe(items -> {
                    videosRecyclerViewAdapter.setItems(items);
                }, throwable -> {
                    binding.moviesRecyclerView.setVisibility(View.GONE);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
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
