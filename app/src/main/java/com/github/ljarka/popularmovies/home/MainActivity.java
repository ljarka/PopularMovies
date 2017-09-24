package com.github.ljarka.popularmovies.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.home.network.PopularMoviesService;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Inject
    PopularMoviesService popularMoviesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularMoviesService.getPopularMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(popularMoviesResult -> {
                    Log.d("result", "Success");

                }, throwable -> {
                    Log.d("result", "error");
                });

    }
}
