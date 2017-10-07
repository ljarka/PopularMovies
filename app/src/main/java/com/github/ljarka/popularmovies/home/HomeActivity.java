package com.github.ljarka.popularmovies.home;

import static com.github.ljarka.popularmovies.home.network.MoviesListService.POPULAR;
import static com.github.ljarka.popularmovies.home.network.MoviesListService.TOP_RATED;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewAnimator;

import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.detail.DetailActivity;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;
import com.github.ljarka.popularmovies.home.network.MoviesListService;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;

public class HomeActivity extends AppCompatActivity implements MoviesRecyclerViewAdapter.OnMovieItemClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HomeViewModel viewModel;
    private MoviesRecyclerViewAdapter adapter;
    private ViewAnimator viewAnimator;
    private View errorView;
    private View progressView;
    private RecyclerView recyclerView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewAnimator = findViewById(R.id.view_animator);
        errorView = findViewById(R.id.tv_loading_error);
        progressView = findViewById(R.id.progress_view);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel.class);

        initRecyclerView();
        loadMovies(POPULAR);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(
                new GridLayoutManager(this, getResources().getInteger(R.integer.span_count), LinearLayoutManager.VERTICAL,
                        false));
        recyclerView.setHasFixedSize(true);
        adapter = new MoviesRecyclerViewAdapter();
        adapter.setOnMovieItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void loadMovies(@MoviesListService.SortBy String sortBy) {
        showProgress();
        viewModel.getMovies(sortBy).observe(this, pagedList -> {
            if (pagedList.isEmpty()) {
                showError();
            } else {
                showContent();
                adapter.setList(pagedList);
            }
        });
    }

    private void showError() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(errorView));
    }

    private void showContent() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(recyclerView));
    }

    private void showProgress() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(progressView));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_popular == item.getItemId()) {
            loadMovies(POPULAR);
            return true;
        } else if (R.id.action_top_rated == item.getItemId()) {
            loadMovies(TOP_RATED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.dispose();
    }

    @Override
    public void onMovieItemClick(MovieItemUi movieItemUi, View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, getString(R.string.imageTransitionName));
        startActivity(DetailActivity.createStartIntent(this, movieItemUi), options.toBundle());
    }
}
