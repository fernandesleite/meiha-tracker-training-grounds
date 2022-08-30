package com.moviedb.repositories

import androidx.lifecycle.LiveData
import com.moviedb.network.*
import com.moviedb.network.model.TMDbMovieCredits
import com.moviedb.network.model.TMDbMovieDetails
import com.moviedb.persistence.model.Movie
import com.moviedb.persistence.model.MovieStatus
import com.moviedb.persistence.database.MoviesAppDatabase
import com.moviedb.util.toDatabase
import com.moviedb.util.toMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: MoviesAppDatabase, private val tmdbApi: TMDbApiService) {

    suspend fun refreshMoviesOfflineCache() {
        withContext(Dispatchers.IO) {
            database.movieStatusDao.insertAll(MovieStatus.populateData())
        }
    }

    suspend fun movieToCache(status: Int, movieId: Int, language: String) {
        withContext(Dispatchers.IO) {
            val getMovieListSuspended = tmdbApi.getMovieDetails(movieId, language)
            val movie = getMovieListSuspended.toMovie(database)
            movie.category = status
            database.movieDao.insert(movie)
        }
    }

    fun getMovie(movieId: Int): LiveData<Int> {
        return database.movieDao.getMovie(movieId)
    }

    fun getWatchedMovies(): LiveData<List<Movie>> {
        return database.movieDao.getWatchedMovies()
    }

    fun getToWatchMovies(): LiveData<List<Movie>> {
        return database.movieDao.getToWatchedMovies()
    }

    suspend fun deleteMovies(id: Int) {
        withContext(Dispatchers.IO) {
            database.movieDao.deleteMovie(id)
        }
    }

    suspend fun getPopularMovies(page: Int, region: String, language: String): List<Movie> =
        withContext(Dispatchers.IO) {
            tmdbApi.getPopularMovies(page, region, language).results.toDatabase(database)
        }

    suspend fun getUpcomingMovies(page: Int, region: String, language: String): List<Movie> =
        withContext(Dispatchers.IO) {
            tmdbApi.getUpcomingMovies(page, region, language).results.toDatabase(database)
        }

    suspend fun getTopRatedMovies(page: Int, region: String, language: String): List<Movie> =
        withContext(Dispatchers.IO) {
            tmdbApi.getTopRatedMovies(page, region, language).results.toDatabase(database)
        }

    suspend fun getNowPlayingMovies(page: Int, region: String, language: String): List<Movie> =
        withContext(Dispatchers.IO) {
            tmdbApi.getNowPlayingMovies(page, region, language).results.toDatabase(database)
        }

    suspend fun getSearchMovie(page: Int, query: String, region: String, language: String): List<Movie> =
        withContext(Dispatchers.IO) {
            tmdbApi.getSearchMovie(page, query, region, language).results.toDatabase(database)
        }

    suspend fun getMovieDetails(movieId: Int, language: String): TMDbMovieDetails =
        tmdbApi.getMovieDetails(movieId, language)

    suspend fun getMovieCredits(movieId: Int): TMDbMovieCredits =
        tmdbApi.getMovieCredits(movieId)

    suspend fun getMovieRecommendations(movieId: Int, language: String): List<Movie> =
        withContext(Dispatchers.IO) {
            tmdbApi.getMovieRecommendations(movieId, language).results.toDatabase(database)
        }
}