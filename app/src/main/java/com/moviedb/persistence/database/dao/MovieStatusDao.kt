package com.moviedb.persistence.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.moviedb.persistence.model.MovieStatus

@Dao
interface MovieStatusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(movieStatus: List<MovieStatus>)
}