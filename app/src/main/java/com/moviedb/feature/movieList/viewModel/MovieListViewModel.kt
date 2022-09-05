package com.moviedb.feature.movieList.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moviedb.R
import com.moviedb.persistence.model.Movie
import com.moviedb.repositories.GenreRepository
import com.moviedb.repositories.MovieRepository
import com.moviedb.util.Response
import kotlinx.coroutines.launch
import java.util.*

class MovieListViewModel(
    private val application: Application,
    private val movieRepository: MovieRepository,
    private val genreRepository: GenreRepository
) : ViewModel() {

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int>
        get() = _page

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>>
        get() = _upcomingMovies

    private val _topRatedMovies = MutableLiveData<List<Movie>>()
    val topRatedMovies: LiveData<List<Movie>>
        get() = _topRatedMovies

    private val _nowPlayingMovies = MutableLiveData<List<Movie>>()
    val nowPlayingMovies: LiveData<List<Movie>>
        get() = _nowPlayingMovies

    private val _searchMovies = MutableLiveData<List<Movie>>()
    val searchMovies: LiveData<List<Movie>>
        get() = _searchMovies

    private var _searchQuery = ""
    val searchQuery: String
        get() = _searchQuery

    val watched: LiveData<List<Movie>>
        get() = movieRepository.getWatchedMovies()

    val toWatch: LiveData<List<Movie>>
        get() = movieRepository.getToWatchMovies()

    private val _errorEvent = MutableLiveData<String>()
    val errorEvent: LiveData<String>
        get() = _errorEvent

    private val country = Locale.getDefault().country
    private val language = Locale.getDefault().language

    init {
        _page.value = 1
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                genreRepository.refreshGenresOfflineCache(language)
                movieRepository.refreshMoviesOfflineCache()
                _page.value?.let { page ->
                    fetchMovieListData(
                        movieRepository.getPopularMovies(page, country, language),
                        _popularMovies
                    )
                    fetchMovieListData(
                        movieRepository.getUpcomingMovies(page, country, language),
                        _upcomingMovies
                    )
                    fetchMovieListData(
                        movieRepository.getTopRatedMovies(page, country, language),
                        _topRatedMovies
                    )
                    fetchMovieListData(
                        movieRepository.getNowPlayingMovies(page, country, language),
                        _nowPlayingMovies
                    )
                }
            } catch (e: Exception) {
                Log.e("MovieListViewModel", e.message, e)
            }
        }
    }

    private fun fetchMovieListData(
        response: Response<List<Movie>>,
        list: MutableLiveData<List<Movie>>
    ) {
        when (response) {
            is Response.Success -> {
                list.value = response.data!!
            }
            is Response.GenericError -> {
                _errorEvent.value = application.getString(R.string.generic_error_message)
            }
            is Response.NetworkError -> {
                _errorEvent.value = application.getString(R.string.internet_connection_error)
            }
        }
    }

    fun resetPage() {
        _page.value = 1
    }

    fun nextPage() {
        _page.value = _page.value?.plus(1)
        refreshDataFromRepository()
    }

    fun getMovieCategory(movieId: Int): LiveData<Int> {
        return movieRepository.getMovieCategory(movieId)
    }

    fun getSearchQuery(query: String) {
        _searchQuery = query
        viewModelScope.launch {
            try {
                _page.value?.let { page ->
                    fetchMovieListData(
                        movieRepository.getSearchMovie(page, _searchQuery, country, language),
                        _searchMovies
                    )
                }

            } catch (e: Exception) {
                Log.e("MovieListViewModel", e.message, e)
            }
        }
    }
}


