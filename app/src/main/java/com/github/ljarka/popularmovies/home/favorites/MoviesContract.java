package com.github.ljarka.popularmovies.home.favorites;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

public class MoviesContract {
    public static final class FavoriteMovies implements android.provider.BaseColumns {
        public static Uri CONTENT_URI = MoviesProvider.BASE_CONTENT_URI.buildUpon().appendPath(MoviesProvider.PATH_MOVIE).build();

        public static String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_URI + "/" + MoviesProvider.PATH_MOVIE;
        public static String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_URI + "/" + MoviesProvider.PATH_MOVIE;

        public static String TABLE_NAME = "favorite_movies";
        public static String ID = "id";
        public static String POSTER = "poster";
        public static String TITLE = "title";
        public static String OVERVIEW = "overview";
        public static String RELEASE_DATE = "release_date";
        public static String BACKDROP = "backdrop";
        public static String USER_RATING = "user_rating";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
