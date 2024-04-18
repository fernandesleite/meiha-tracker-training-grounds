package com.moviedb.feature.movieList.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import com.moviedb.persistence.model.Movie

class NowPlayingMovieListFragment : MovieListBaseFragment() {
    override fun getMovieList(): LiveData<List<Movie>> {
        return movieListViewModel.nowPlayingMovies
    }

}