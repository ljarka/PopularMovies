package com.github.ljarka.popularmovies.home;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviesViewHolder> {
    public interface OnMovieItemClickListener {
        void onMovieItemClick(MovieItemUi movieItemUi, View view);
    }

    private static final OnMovieItemClickListener EMPTY_LISTENER = (movieItemUi, view) -> {
        //empty
    };

    private List<MovieItemUi> items = new ArrayList<>();
    private OnMovieItemClickListener listener = EMPTY_LISTENER;

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MoviesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        Glide.with(holder.image.getContext())
                .load(items.get(position).getPoster())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void setItems(List<MovieItemUi> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setOnMovieItemClickListener(@Nullable OnMovieItemClickListener listener) {
        this.listener = Optional.ofNullable(listener).orElse(EMPTY_LISTENER);
    }

    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onMovieItemClick(items.get(getAdapterPosition()), image);
        }
    }
}
