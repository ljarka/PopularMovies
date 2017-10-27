package com.github.ljarka.popularmovies.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;
import com.github.ljarka.popularmovies.home.network.MoviesListService;

import javax.inject.Inject;

public class MoviesListFragmentViewModel extends ViewModel {

    private static final int PAGE_SIZE = 20;
    private MoviesListService moviesListService;

    @Inject
    MoviesListFragmentViewModel(MoviesListService moviesListService) {
        this.moviesListService = moviesListService;
    }

    LiveData<PagedList<MovieItemUi>> getMovies(@MoviesListService.SortBy String sortBy) {
        return new MoviesDataProvider(sortBy, moviesListService).create(0, createPagedList());
    }

    private PagedList.Config createPagedList() {
        return new PagedList.Config.Builder().setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build();
    }
}
