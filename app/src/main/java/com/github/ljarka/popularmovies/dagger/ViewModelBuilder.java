package com.github.ljarka.popularmovies.dagger;


import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelBuilder {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(PopularMoviesViewModelsFactory factory);
}