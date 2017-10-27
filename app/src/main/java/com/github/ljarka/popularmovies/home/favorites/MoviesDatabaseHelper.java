package com.github.ljarka.popularmovies.home.favorites;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MoviesDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movies";
    private static final int DATABASE_VERSION = 3;

    public MoviesDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createFavoritesMoviesTable = "CREATE TABLE " + MoviesContract.FavoriteMovies.TABLE_NAME +
                "(" + MoviesContract.FavoriteMovies.ID + " INTEGER," + MoviesContract.FavoriteMovies.TITLE + " TEXT, " + MoviesContract.FavoriteMovies.POSTER + " TEXT, " + MoviesContract.FavoriteMovies.OVERVIEW + " TEXT, " + MoviesContract.FavoriteMovies.RELEASE_DATE
                + " TEXT, " + MoviesContract.FavoriteMovies.USER_RATING + " TEXT, " + MoviesContract.FavoriteMovies.BACKDROP + " TEXT, " + "UNIQUE(" + MoviesContract.FavoriteMovies.ID +  ") ON CONFLICT REPLACE"+
                ")";

        db.execSQL(createFavoritesMoviesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.FavoriteMovies.TABLE_NAME);
            onCreate(db);
        }
    }
}
