package com.github.ljarka.popularmovies.home;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;

import java.util.ArrayList;
import java.util.List;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MoviesViewHolder> {

    private List<MovieItemUi> items = new ArrayList<>();

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

    public void setItems(List<MovieItemUi> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    static class MoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
