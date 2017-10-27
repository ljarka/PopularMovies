package com.github.ljarka.popularmovies.home;

import static java.lang.annotation.RetentionPolicy.SOURCE;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.ljarka.popularmovies.OnMovieItemClickListener;
import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.detail.DetailActivity;
import com.github.ljarka.popularmovies.home.favorites.FavoritesFragment;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;
import com.github.ljarka.popularmovies.home.movies.MoviesServiceListFragment;
import com.github.ljarka.popularmovies.home.network.MoviesListService;

import java.lang.annotation.Retention;
import java.util.Optional;

public class HomeActivity extends AppCompatActivity implements OnMovieItemClickListener {
    @Retention(SOURCE)
    @StringDef({
            POPULAR_MOVIES, TOP_RATED_MOVIES, FAVORITE_MOVIES
    })
    @interface ListType {
    }

    public static final String POPULAR_MOVIES = "popular";
    public static final String TOP_RATED_MOVIES = "top_rated";
    public static final String FAVORITE_MOVIES = "favorites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (R.id.action_popular == item.getItemId()) {
                loadMoviesFromService(MoviesListService.POPULAR);
                setTitle(getString(R.string.popular));
                return true;
            } else if (R.id.action_top_rated == item.getItemId()) {
                loadMoviesFromService(MoviesListService.TOP_RATED);
                setTitle(getString(R.string.top_rated));
                return true;
            } else if (R.id.action_favorites == item.getItemId()) {
                loadFavoriteMovies();
                setTitle(getString(R.string.favorites));
                return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(mapIntentParameterToActionId(
                Optional.ofNullable(getIntent().getStringExtra(Intent.EXTRA_TEXT)).orElse(POPULAR_MOVIES)));
    }

    private void loadFavoriteMovies() {
        FavoritesFragment favoritesFragment = FavoritesFragment.createInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, favoritesFragment).commit();
    }

    private void loadMoviesFromService(@MoviesListService.SortBy String movieType) {
        MoviesServiceListFragment moviesServiceListFragment = MoviesServiceListFragment.createInstance(movieType);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, moviesServiceListFragment).commit();
    }

    @IdRes
    private int mapIntentParameterToActionId(@NonNull String listType) {
        switch (listType) {
            case POPULAR_MOVIES: {
                return R.id.action_popular;
            }
            case TOP_RATED_MOVIES: {
                return R.id.action_top_rated;
            }
            case FAVORITE_MOVIES: {
                return R.id.action_favorites;
            }
            default: {
                return R.id.action_popular;
            }
        }
    }

    @Override
    public void onMovieItemClick(MovieItemUi movieItemUi, View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, getString(R.string.imageTransitionName));
        startActivity(DetailActivity.createStartIntent(this, movieItemUi), options.toBundle());
    }

    public static Intent createStartIntent(Context context, @ListType String listType) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, listType);
        return intent;
    }
}
