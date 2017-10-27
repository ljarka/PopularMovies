package com.github.ljarka.popularmovies.home.movies;

import android.arch.lifecycle.ViewModel;

import com.github.ljarka.popularmovies.dagger.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module(includes = MoviesListFragmentModule.class)
public abstract class MoviesServiceListFragmentBuilder {

    @ContributesAndroidInjector(modules = MoviesListFragmentModule.class)
    abstract MoviesServiceListFragment bindMoviesListFragment();

    @Binds
    @IntoMap
    @ViewModelKey(MoviesServiceListFragmentViewModel.class)
    abstract ViewModel bindMoviesListFragmentViewModel(MoviesServiceListFragmentViewModel homeViewModel);
}
