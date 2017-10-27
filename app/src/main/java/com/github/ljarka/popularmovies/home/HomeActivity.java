package com.github.ljarka.popularmovies.home;

import static com.github.ljarka.popularmovies.home.network.MoviesListService.POPULAR;
import static com.github.ljarka.popularmovies.home.network.MoviesListService.TOP_RATED;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.detail.DetailActivity;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;
import com.github.ljarka.popularmovies.home.network.MoviesListService;

public class HomeActivity extends AppCompatActivity implements MoviesRecyclerViewAdapter.OnMovieItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (R.id.action_popular == item.getItemId()) {
                loadMoviesFromService(POPULAR);
                return true;
            } else if (R.id.action_top_rated == item.getItemId()) {
                loadMoviesFromService(TOP_RATED);
                return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.action_popular);
    }

    private void loadMoviesFromService(@MoviesListService.SortBy String movieType) {
        MoviesServiceListFragment moviesServiceListFragment = MoviesServiceListFragment.createInstance(movieType);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, moviesServiceListFragment).commit();
    }

    @Override
    public void onMovieItemClick(MovieItemUi movieItemUi, View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, getString(R.string.imageTransitionName));
        startActivity(DetailActivity.createStartIntent(this, movieItemUi), options.toBundle());
    }
}
