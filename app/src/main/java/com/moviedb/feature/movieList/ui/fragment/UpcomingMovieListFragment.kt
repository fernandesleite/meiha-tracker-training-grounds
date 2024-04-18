package com.moviedb.feature.movieList.ui.fragment

import androidx.lifecycle.LiveData
import com.moviedb.persistence.model.Movie

class UpcomingMovieListFragment : MovieListBaseFragment() {
    override fun getMovieList(): LiveData<List<Movie>> {
        return movieListViewModel.upcomingMovies
    }
}