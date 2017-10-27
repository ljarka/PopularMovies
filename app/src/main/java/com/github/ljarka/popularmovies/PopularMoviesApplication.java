package com.github.ljarka.popularmovies;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class PopularMoviesApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        com.github.ljarka.popularmovies.dagger.DaggerAppComponent.builder().application(this).build().inject(this);
        Stetho.initializeWithDefaults(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
