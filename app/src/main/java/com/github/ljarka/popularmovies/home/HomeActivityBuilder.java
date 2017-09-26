package com.github.ljarka.popularmovies.home;

import android.arch.lifecycle.ViewModel;

import com.github.ljarka.popularmovies.dagger.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.multibindings.IntoMap;

@Module(includes = HomeActivityModule.class)
public abstract class HomeActivityBuilder {

    @ContributesAndroidInjector(modules = HomeActivityModule.class)
    abstract HomeActivity bindMainActivity();

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);
}
