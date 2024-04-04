package com.moviedb.repositories

import androidx.lifecycle.LiveData
import com.moviedb.network.TMDbApiService
import com.moviedb.network.model.TMDbMovieCredits
import com.moviedb.network.model.TMDbMovieDetails
import com.moviedb.persistence.database.MoviesAppDatabase
import com.moviedb.persistence.model.Movie
import com.moviedb.persistence.model.MovieStatus
import com.moviedb.util.Response
import com.moviedb.util.toDatabase
import com.moviedb.util.toMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

class MovieRepository(
    private val database: MoviesAppDatabase,
    private val tmdbApi: TMDbApiService
) {

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

    fun getMovieCategory(movieId: Int): LiveData<Int> {
        return database.movieDao.getMovieCategory(movieId)
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

    @Suppress("UNCHECKED_CAST")
    suspend fun getPopularMovies(
        page: Int,
        region: String,
        language: String
    ): Response<List<Movie>> {
        return withContext(Dispatchers.IO) {
            val response =
                tmdbApi.getPopularMovies(page, region, language).results.toDatabase(database)
            fetchNetworkData(response) as Response<List<Movie>>
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getUpcomingMovies(
        page: Int,
        region: String,
        language: String
    ): Response<List<Movie>> {
        return withContext(Dispatchers.IO) {
            val response =
                tmdbApi.getUpcomingMovies(page, region, language).results.toDatabase(database)
            fetchNetworkData(response) as Response<List<Movie>>
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getTopRatedMovies(
        page: Int,
        region: String,
        language: String
    ): Response<List<Movie>> {
        return withContext(Dispatchers.IO) {
            val response =
                tmdbApi.getTopRatedMovies(page, region, language).results.toDatabase(database)
            return@withContext fetchNetworkData(response) as Response<List<Movie>>
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getNowPlayingMovies(
        page: Int,
        region: String,
        language: String
    ): Response<List<Movie>> {
        return withContext(Dispatchers.IO) {
            val response =
                tmdbApi.getNowPlayingMovies(page, region, language).results.toDatabase(database)
            return@withContext fetchNetworkData(response) as Response<List<Movie>>
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getSearchMovie(
        page: Int,
        query: String,
        region: String,
        language: String
    ): Response<List<Movie>> {
        return withContext(Dispatchers.IO) {
            val response =
                tmdbApi.getSearchMovie(page, query, region, language).results.toDatabase(database)
            return@withContext fetchNetworkData(response) as Response<List<Movie>>
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getMovieDetails(movieId: Int, language: String): Response<TMDbMovieDetails> {
        return withContext(Dispatchers.IO) {
            val response =
                tmdbApi.getMovieDetails(movieId, language)
            return@withContext fetchNetworkData(response) as Response<TMDbMovieDetails>
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getMovieCredits(movieId: Int): Response<TMDbMovieCredits> {
        return withContext(Dispatchers.IO) {
            val response =
                tmdbApi.getMovieCredits(movieId)
            return@withContext fetchNetworkData(response) as Response<TMDbMovieCredits>
        }
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun getMovieRecommendations(movieId: Int, language: String): Response<List<Movie>> {
        return withContext(Dispatchers.IO) {
            val response =
                tmdbApi.getMovieRecommendations(movieId, language).results.toDatabase(database)
            return@withContext fetchNetworkData(response) as Response<List<Movie>>
        }
    }

    private fun fetchNetworkData(
        response: Any
    ): Response<Any> {
        return try {
            Response.Success(response)
        } catch (e: HttpException) {
            Response.GenericError(e)
        } catch (e: IOException) {
            Response.NetworkError(e)
        } catch (e: Exception) {
            Response.NetworkError(e)
        }
    }
}