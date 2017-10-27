package com.github.ljarka.popularmovies.home.favorites;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ljarka.popularmovies.OnMovieItemClickListener;
import com.github.ljarka.popularmovies.R;

public class FavoritesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 1;
    private FavoritesRecyclerViewAdapter adapter;
    private OnMovieItemClickListener onMovieItemClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieItemClickListener) {
            onMovieItemClickListener = (OnMovieItemClickListener) context;
        } else {
            throw new ClassCastException("Parent activity has to implement " + OnMovieItemClickListener.class.getSimpleName());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.favorites_recycler_view);
        adapter = new FavoritesRecyclerViewAdapter();
        adapter.setOnMovieItemClickListener(onMovieItemClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.span_count),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        loadFavoriteMovies();
    }

    private void loadFavoriteMovies() {
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    public static FavoritesFragment createInstance() {
        return new FavoritesFragment();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        return new CursorLoader(getContext(), MoviesContract.FavoriteMovies.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
