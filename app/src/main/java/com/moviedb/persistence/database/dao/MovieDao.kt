package com.moviedb.persistence.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.moviedb.persistence.model.Movie

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movies: List<Movie>)

    @Update
    fun update(movie: Movie)

    @Query("DELETE FROM movie_table")
    fun clear()

    @Query("SELECT category FROM movie_table where id = :id")
    fun getMovieCategory(id: Int): LiveData<Int>

    @Query("SELECT * FROM movie_table ORDER BY popularity DESC")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table WHERE category = 1 ORDER BY added_timestamp DESC")
    fun getWatchedMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie_table WHERE category = 2 ORDER BY added_timestamp DESC")
    fun getToWatchedMovies(): LiveData<List<Movie>>

    @Query("DELETE FROM movie_table WHERE id = :id")
    fun deleteMovie(id: Int)

}