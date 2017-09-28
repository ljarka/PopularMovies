package com.github.ljarka.popularmovies.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.detail.DetailActivity;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity implements MoviesRecyclerViewAdapter.OnMovieItemClickListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private HomeViewModel viewModel;
    private MoviesRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this,
                viewModelFactory).get(HomeViewModel.class);

        initRecyclerView();
        loadPopularMovies();

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,
                getResources().getInteger(R.integer.span_count),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        adapter = new MoviesRecyclerViewAdapter();
        adapter.setOnMovieItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private void loadPopularMovies() {
        viewModel.getPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::setItems, throwable -> {

                });
    }

    private void loadTopRatedMovies() {
        viewModel.getTopRatedMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter::setItems, throwable -> {

                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_popular == item.getItemId()) {
            loadPopularMovies();
            return true;
        } else if (R.id.action_top_rated == item.getItemId()) {
            loadTopRatedMovies();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemClick(MovieItemUi movieItemUi) {
        startActivity(DetailActivity.createStartIntent(this, movieItemUi));
    }
}