package com.github.ljarka.popularmovies.home;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewAnimator;

import com.github.ljarka.popularmovies.R;
import com.github.ljarka.popularmovies.home.network.MoviesListService;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.reactivex.disposables.CompositeDisposable;

public class MoviesServiceListFragment extends Fragment {
    private static final String MOVIES_SORT_TYPE = "movies_sort_type";
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private MoviesListFragmentViewModel viewModel;
    private MoviesRecyclerViewAdapter adapter;
    private ViewAnimator viewAnimator;
    private View errorView;
    private View progressView;
    private RecyclerView recyclerView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MoviesRecyclerViewAdapter.OnMovieItemClickListener onMovieItemClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MoviesRecyclerViewAdapter.OnMovieItemClickListener) {
            onMovieItemClickListener = (MoviesRecyclerViewAdapter.OnMovieItemClickListener) context;
        } else {
            throw new ClassCastException("Parent activity has to implement "
                    + MoviesRecyclerViewAdapter.OnMovieItemClickListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesListFragmentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewAnimator = view.findViewById(R.id.view_animator);
        errorView = view.findViewById(R.id.tv_loading_error);
        progressView = view.findViewById(R.id.progress_view);
        initRecyclerView(view);
        loadList(getArguments().getString(MOVIES_SORT_TYPE));
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.span_count),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        adapter = new MoviesRecyclerViewAdapter();
        adapter.setOnMovieItemClickListener(onMovieItemClickListener);
        recyclerView.setAdapter(adapter);
    }

    private void loadList(@MoviesListService.SortBy String sortBy) {
        showProgress();
        viewModel.getMovies(sortBy).observe(this, pagedList -> {
            if (pagedList.isEmpty()) {
                showError();
            } else {
                showContent();
                adapter.setList(pagedList);
            }
        });
    }

    private void showError() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(errorView));
    }

    private void showContent() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(recyclerView));
    }

    private void showProgress() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(progressView));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
    }

    public static MoviesServiceListFragment createInstance(@MoviesListService.SortBy String moviesType) {
        MoviesServiceListFragment moviesServiceListFragment = new MoviesServiceListFragment();
        Bundle arguments = new Bundle();
        arguments.putString(MOVIES_SORT_TYPE, moviesType);
        moviesServiceListFragment.setArguments(arguments);
        return moviesServiceListFragment;
    }
}
