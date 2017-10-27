package com.github.ljarka.popularmovies.home.favorites;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MoviesProvider extends ContentProvider {
    public static final String CONTENT_AUTHORITY = "com.github.ljarka.movies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    private static final int MOVIES = 100;
    private static final int MOVIE_ID = 101;

    private MoviesDatabaseHelper moviesDatabaseHelper;
    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;
        matcher.addURI(authority, PATH_MOVIE, MOVIES);
        matcher.addURI(authority, PATH_MOVIE + "/#", MOVIE_ID);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        moviesDatabaseHelper = new MoviesDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (uriMatcher.match(uri)) {
            case MOVIES: {
                retCursor = moviesDatabaseHelper.getReadableDatabase()
                        .query(MoviesContract.FavoriteMovies.TABLE_NAME, projection, selection, selectionArgs, null, null,
                                sortOrder);
                return retCursor;
            }
            case MOVIE_ID: {
                retCursor = moviesDatabaseHelper.getReadableDatabase()
                        .query(MoviesContract.FavoriteMovies.TABLE_NAME, projection, MoviesContract.FavoriteMovies._ID + " = ?",
                                new String[] {String.valueOf(ContentUris.parseId(uri))}, null, null, sortOrder);
                return retCursor;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (uriMatcher.match(uri)) {
            case MOVIES: {
                return MoviesContract.FavoriteMovies.CONTENT_TYPE;
            }
            case MOVIE_ID: {
                return MoviesContract.FavoriteMovies.CONTENT_ITEM_TYPE;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = moviesDatabaseHelper.getWritableDatabase();
        Uri returnUri;
        switch (uriMatcher.match(uri)) {
            case MOVIE_ID: {
                long id = db.insert(MoviesContract.FavoriteMovies.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = MoviesContract.FavoriteMovies.buildMovieUri(id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = moviesDatabaseHelper.getWritableDatabase();
        int numDeleted;
        switch (uriMatcher.match(uri)) {
            case MOVIES:
                numDeleted = db.delete(MoviesContract.FavoriteMovies.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_ID:
                numDeleted = db.delete(MoviesContract.FavoriteMovies.TABLE_NAME, MoviesContract.FavoriteMovies._ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = moviesDatabaseHelper.getWritableDatabase();
        int numUpdated;

        if (contentValues == null) {
            throw new IllegalArgumentException("Cannot have null content values");
        }

        switch (uriMatcher.match(uri)) {
            case MOVIES: {
                numUpdated = db.update(MoviesContract.FavoriteMovies.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            }
            case MOVIE_ID: {
                numUpdated = db.update(MoviesContract.FavoriteMovies.TABLE_NAME, contentValues,
                        MoviesContract.FavoriteMovies._ID + " = ?", new String[] {String.valueOf(ContentUris.parseId(uri))});
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }

        if (numUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numUpdated;
    }
}
