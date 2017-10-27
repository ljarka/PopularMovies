package com.github.ljarka.popularmovies.home.favorites;

import static com.github.ljarka.popularmovies.home.favorites.MoviesContract.FavoriteMovies.BACKDROP;
import static com.github.ljarka.popularmovies.home.favorites.MoviesContract.FavoriteMovies.ID;
import static com.github.ljarka.popularmovies.home.favorites.MoviesContract.FavoriteMovies.OVERVIEW;
import static com.github.ljarka.popularmovies.home.favorites.MoviesContract.FavoriteMovies.POSTER;
import static com.github.ljarka.popularmovies.home.favorites.MoviesContract.FavoriteMovies.RELEASE_DATE;
import static com.github.ljarka.popularmovies.home.favorites.MoviesContract.FavoriteMovies.TITLE;
import static com.github.ljarka.popularmovies.home.favorites.MoviesContract.FavoriteMovies.USER_RATING;

import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.ljarka.popularmovies.OnMovieItemClickListener;
import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;

import java.util.Optional;

public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.FavoritesViewHolder> {

    private static final OnMovieItemClickListener EMPTY_LISTENER = (movieItemUi, view) -> {
        //empty
    };

    private OnMovieItemClickListener listener = EMPTY_LISTENER;

    private Cursor cursor;

    @Override
    public FavoritesViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new FavoritesViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movies_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(FavoritesViewHolder favoritesViewHolder, int position) {
        cursor.moveToPosition(position);
        String posterUrl = cursor.getString(cursor.getColumnIndex(POSTER));
        Glide.with(favoritesViewHolder.itemView.getContext()).load(posterUrl).into(favoritesViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
    }

    public void swapCursor(Cursor cursor) {
        if (this.cursor != null) {
            this.cursor.close();
        } else {
            this.cursor = cursor;
        }
        notifyDataSetChanged();
    }

    class FavoritesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView image;

        public FavoritesViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onMovieItemClick(getItem(getAdapterPosition()), image);
        }
    }

    public void setOnMovieItemClickListener(@Nullable OnMovieItemClickListener listener) {
        this.listener = Optional.ofNullable(listener).orElse(EMPTY_LISTENER);
    }

    private MovieItemUi getItem(int adapterPosition) {
        cursor.moveToPosition(adapterPosition);
        return MovieItemUi.builder()
                .withId(cursor.getInt(cursor.getColumnIndex(ID)))
                .withPoster(cursor.getString(cursor.getColumnIndex(POSTER)))
                .withTitle(cursor.getString(cursor.getColumnIndex(TITLE)))
                .withOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)))
                .withUserRating(cursor.getString(cursor.getColumnIndex(USER_RATING)))
                .withReleaseDate(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)))
                .withBackdrop(cursor.getString(cursor.getColumnIndex(BACKDROP)))
                .build();
    }
}
