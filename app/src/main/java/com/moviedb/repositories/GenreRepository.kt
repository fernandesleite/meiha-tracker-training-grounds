package com.moviedb.repositories

import androidx.lifecycle.LiveData
import com.moviedb.network.TMDbApiService
import com.moviedb.persistence.model.Genre
import com.moviedb.persistence.database.MoviesAppDatabase
import com.moviedb.util.toDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenreRepository(private val database: MoviesAppDatabase, private val tmDbApi: TMDbApiService) {
    suspend fun refreshGenresOfflineCache(language: String) {
        withContext(Dispatchers.IO) {
            val getGenreListSuspended = tmDbApi.getGenreList(language)
            database.genreDao.insertAll(getGenreListSuspended.genres.toDatabase())
        }
    }

    val genres: LiveData<List<Genre>> = database.genreDao.getAllGenres()
}