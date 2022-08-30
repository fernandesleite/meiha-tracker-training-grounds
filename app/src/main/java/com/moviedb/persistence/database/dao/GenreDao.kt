package com.moviedb.persistence.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moviedb.persistence.model.Genre

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(genre: Genre)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(genres: List<Genre>)

    @Query("SELECT * FROM genre_table WHERE id = :id")
    fun getGenre(id: Int): Genre

    @Query("SELECT * FROM genre_table")
    fun getAllGenres(): LiveData<List<Genre>>
}