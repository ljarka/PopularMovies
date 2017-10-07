package com.github.ljarka.popularmovies.detail;

import android.arch.lifecycle.ViewModel;

import com.github.ljarka.popularmovies.dagger.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module(includes = DetailActivityModule.class)
public abstract class DetailActivityBuilder {

    @ContributesAndroidInjector(modules = DetailActivityModule.class)
    abstract DetailActivity bindDetailActivity();

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.class)
    abstract ViewModel bindDetailViewModel(DetailViewModel homeViewModel);
}