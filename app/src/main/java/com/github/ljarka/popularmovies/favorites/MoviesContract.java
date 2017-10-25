package com.github.ljarka.popularmovies.favorites;

import static com.github.ljarka.popularmovies.favorites.MoviesProvider.BASE_CONTENT_URI;
import static com.github.ljarka.popularmovies.favorites.MoviesProvider.PATH_MOVIE;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

public class MoviesContract {
    interface FavoriteMovies extends android.provider.BaseColumns {
        Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_URI + "/" + PATH_MOVIE;
        String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_URI + "/" + PATH_MOVIE;

        String TABLE_NAME = "favorite_movies";
        String ID = "id";
        String POSTER = "poster";
        String TITLE = "title";
        String OVERVIEW = "overview";
        String RELEASE_DATE = "release_date";
        String BACKDROP = "backdrop";

        static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
