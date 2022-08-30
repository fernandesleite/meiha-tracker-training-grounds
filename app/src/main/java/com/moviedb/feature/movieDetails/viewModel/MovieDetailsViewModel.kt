package com.moviedb.feature.movieDetails.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moviedb.network.model.TMDbMovieCredits
import com.moviedb.network.model.TMDbMovieDetails
import com.moviedb.persistence.model.Movie
import com.moviedb.repositories.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class MovieDetailsViewModel(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _details = MutableLiveData<TMDbMovieDetails>()
    val details: LiveData<TMDbMovieDetails>
        get() = _details

    private val _credits = MutableLiveData<TMDbMovieCredits>()
    val credits: LiveData<TMDbMovieCredits>
        get() = _credits

    private val _recommendations = MutableLiveData<List<Movie>>()
    val recommendations: LiveData<List<Movie>>
        get() = _recommendations

    private val country = Locale.getDefault().country
    private val language = Locale.getDefault().language


    fun getMovieInfo(movieId: Int) {
        try {
            coroutineScope.launch {
                _details.value = movieRepository.getMovieDetails(movieId, language)
                _credits.value = movieRepository.getMovieCredits(movieId)
                _recommendations.value = movieRepository.getMovieRecommendations(movieId, language)
            }
        } catch (e: Exception) {
            Log.e("MovieDetailsViewModel", e.message, e)
        }
    }

    fun getMovie(movieId: Int): LiveData<Int> {
        return movieRepository.getMovie(movieId)
    }

    fun movieToCache(status: Int, movieId: Int) {
        try {
            coroutineScope.launch {
                movieRepository.movieToCache(status, movieId, language)
            }
        } catch (e: Exception) {
            Log.e("MovieDetailsViewModel", e.message, e)
        }
    }

    fun deleteMovie(movieId: Int) {
        try {
            coroutineScope.launch {
                movieRepository.deleteMovies(movieId)
            }
        } catch (e: Exception) {
            Log.e("MovieDetailsViewModel", e.message, e)
        }
    }
}

