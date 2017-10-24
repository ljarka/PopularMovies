package com.github.ljarka.popularmovies.detail;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.github.ljarka.popularmovies.databinding.ReviewItemBinding;
import com.github.ljarka.popularmovies.detail.model.ui.ReviewUi;

import java.util.Collections;
import java.util.List;

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ReviewsViewHolder> {
    private List<ReviewUi> reviews = Collections.emptyList();

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        return new ReviewsViewHolder(ReviewItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder reviewsViewHolder, int position) {
        reviewsViewHolder.binding.setReview(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setItems(List<ReviewUi> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder {
        ReviewItemBinding binding;

        public ReviewsViewHolder(ReviewItemBinding reviewItemBinding) {
            super(reviewItemBinding.getRoot());
            this.binding = reviewItemBinding;
        }
    }
}
