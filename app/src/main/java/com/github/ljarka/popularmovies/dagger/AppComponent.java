package com.github.ljarka.popularmovies.dagger;

import android.app.Application;

import com.github.ljarka.popularmovies.PopularMoviesApplication;
import com.github.ljarka.popularmovies.detail.DetailActivityBuilder;
import com.github.ljarka.popularmovies.home.HomeActivityBuilder;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class, AppModule.class, HomeActivityBuilder.class, ViewModelBuilder.class,
        DetailActivityBuilder.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

    void inject(PopularMoviesApplication app);
}