package com.github.ljarka.popularmovies.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;
import com.github.ljarka.popularmovies.home.network.MoviesService;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    private MoviesService moviesService;

    @Inject
    HomeViewModel(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    LiveData<PagedList<MovieItemUi>> getMovies(@MoviesService.SortBy String sortBy) {
        return new MoviesDataProvider(sortBy, moviesService).create(0, createPagedList());
    }

    private PagedList.Config createPagedList() {
        return new PagedList.Config.Builder()
                .setPageSize(20)
                .setInitialLoadSizeHint(20)
                .setEnablePlaceholders(false)
                .build();
    }
}
