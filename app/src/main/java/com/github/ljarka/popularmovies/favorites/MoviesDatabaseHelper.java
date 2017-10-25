package com.github.ljarka.popularmovies.favorites;

import static com.github.ljarka.popularmovies.favorites.MoviesContract.FavoriteMovies.BACKDROP;
import static com.github.ljarka.popularmovies.favorites.MoviesContract.FavoriteMovies.ID;
import static com.github.ljarka.popularmovies.favorites.MoviesContract.FavoriteMovies.OVERVIEW;
import static com.github.ljarka.popularmovies.favorites.MoviesContract.FavoriteMovies.POSTER;
import static com.github.ljarka.popularmovies.favorites.MoviesContract.FavoriteMovies.RELEASE_DATE;
import static com.github.ljarka.popularmovies.favorites.MoviesContract.FavoriteMovies.TABLE_NAME;
import static com.github.ljarka.popularmovies.favorites.MoviesContract.FavoriteMovies.TITLE;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies";
    private static final int DATABASE_VERSION = 1;

    public MoviesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createFavoritesMoviesTable = "CREATE TABLE " + TABLE_NAME +
                "(" + ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT, " + POSTER + " TEXT, " + OVERVIEW + "TEXT, " + RELEASE_DATE
                + "TEXT, " + BACKDROP + "TEXT," +
                ")";

        db.execSQL(createFavoritesMoviesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
