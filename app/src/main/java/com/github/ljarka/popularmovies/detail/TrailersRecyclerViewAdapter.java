package com.github.ljarka.popularmovies.detail;

import static java.util.Collections.emptyList;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ljarka.popularmovies.databinding.VideoItemBinding;
import com.github.ljarka.popularmovies.detail.model.ui.VideoDescriptorUi;

import java.util.List;
import java.util.Optional;

public class TrailersRecyclerViewAdapter extends RecyclerView.Adapter<TrailersRecyclerViewAdapter.TrailersViewHolder> {
    public interface OnVideoClickListener {
        void onVideoClick(Uri videoUri);
    }

    private static final OnVideoClickListener EMPTY_LISTENER = videoUri -> {
        // empty
    };

    private List<VideoDescriptorUi> items = emptyList();
    private OnVideoClickListener onVideoClickListener = EMPTY_LISTENER;

    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TrailersViewHolder(VideoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(TrailersViewHolder holder, int position) {
        holder.videoItemBinding.setVideo(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<VideoDescriptorUi> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public List<VideoDescriptorUi> getItems() {
        return items;
    }

    public void setOnVideoClickListener(@Nullable OnVideoClickListener onVideoClickListener) {
        this.onVideoClickListener = Optional.ofNullable(onVideoClickListener).orElse(EMPTY_LISTENER);
    }

    class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        VideoItemBinding videoItemBinding;

        public TrailersViewHolder(VideoItemBinding binding) {
            super(binding.getRoot());
            this.videoItemBinding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onVideoClickListener.onVideoClick(items.get(getAdapterPosition()).getVideoUri());
        }
    }
}
