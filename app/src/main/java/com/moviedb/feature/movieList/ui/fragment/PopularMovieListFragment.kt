package com.moviedb.feature.movieList.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.moviedb.persistence.model.Movie

class PopularMovieListFragment : MovieListBaseFragment() {
    override fun getMovieList(): LiveData<List<Movie>> {
        return movieListViewModel.popularMovies
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addPagination(binding.movieList) { loadMoreItems() }
    }
}