package com.github.ljarka.popularmovies.home.movies;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.ljarka.popularmovies.GlideApp;
import com.github.ljarka.popularmovies.OnMovieItemClickListener;
import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;

import java.util.Optional;

import javax.annotation.Nullable;

public class MoviesServiceRecyclerViewAdapter
        extends PagedListAdapter<MovieItemUi, MoviesServiceRecyclerViewAdapter.MoviesViewHolder> {

    private static final OnMovieItemClickListener EMPTY_LISTENER = (movieItemUi, view) -> {
        //empty
    };

    private OnMovieItemClickListener listener = EMPTY_LISTENER;

    public MoviesServiceRecyclerViewAdapter() {
        super(new MyDiffCallback());
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        GlideApp.with(holder.image.getContext()).load(getItem(position).getPoster()).into(holder.image);
    }

    void setOnMovieItemClickListener(@Nullable OnMovieItemClickListener listener) {
        this.listener = Optional.ofNullable(listener).orElse(EMPTY_LISTENER);
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView image;

        MoviesViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onMovieItemClick(getItem(getAdapterPosition()), image);
        }
    }

    private static class MyDiffCallback extends DiffCallback<MovieItemUi> {
        @Override
        public boolean areItemsTheSame(@NonNull MovieItemUi oldItem, @NonNull MovieItemUi newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MovieItemUi oldItem, @NonNull MovieItemUi newItem) {
            return oldItem.equals(newItem);
        }
    }
}
