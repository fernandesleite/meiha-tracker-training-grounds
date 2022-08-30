package com.moviedb.feature.movieList.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.moviedb.R
import com.moviedb.persistence.model.Movie

class WatchedMovieListFragment: MovieListBaseFragment() {
    override fun getMovieList(): LiveData<List<Movie>> {
        return movieListViewModel.watched
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarLayout.appBar.visibility = View.VISIBLE
        binding.toolbarLayout.toolbar.title = getString(R.string.watched)
        mAdapter.removeItems()
    }

}
