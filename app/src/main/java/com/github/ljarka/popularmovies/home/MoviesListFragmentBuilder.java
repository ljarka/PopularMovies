package com.github.ljarka.popularmovies.home;

import android.arch.lifecycle.ViewModel;

import com.github.ljarka.popularmovies.dagger.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module(includes = MoviesListFragmentModule.class)
public abstract class MoviesListFragmentBuilder {

    @ContributesAndroidInjector(modules = MoviesListFragmentModule.class)
    abstract MoviesServiceListFragment bindMoviesListFragment();

    @Binds
    @IntoMap
    @ViewModelKey(MoviesListFragmentViewModel.class)
    abstract ViewModel bindMoviesListFragmentViewModel(MoviesListFragmentViewModel homeViewModel);
}
