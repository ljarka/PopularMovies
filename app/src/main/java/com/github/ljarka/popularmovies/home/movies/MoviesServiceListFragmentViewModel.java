package com.github.ljarka.popularmovies.home.movies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.github.ljarka.popularmovies.home.model.ui.MovieItemUi;
import com.github.ljarka.popularmovies.home.network.MoviesListService;

import javax.inject.Inject;

public class MoviesServiceListFragmentViewModel extends ViewModel {

    private static final int PAGE_SIZE = 20;
    private MoviesListService moviesListService;
    private LiveData<PagedList<MovieItemUi>> moviesLiveData;
    private String currentSortType;

    @Inject
    MoviesServiceListFragmentViewModel(MoviesListService moviesListService) {
        this.moviesListService = moviesListService;
    }

    LiveData<PagedList<MovieItemUi>> getMovies(@MoviesListService.SortBy String sortBy) {
        if (moviesLiveData == null && !sortBy.equals(currentSortType)) {
            moviesLiveData = new MoviesDataProvider(sortBy, moviesListService).create(0, createPagedList());
            currentSortType = sortBy;
        }
        return moviesLiveData;
    }

    private PagedList.Config createPagedList() {
        return new PagedList.Config.Builder().setPageSize(PAGE_SIZE)
                .setInitialLoadSizeHint(PAGE_SIZE)
                .setEnablePlaceholders(false)
                .build();
    }
}
