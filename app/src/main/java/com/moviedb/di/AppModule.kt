package com.moviedb.di

import androidx.room.Room
import com.moviedb.persistence.database.MoviesAppDatabase
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            get(),
            MoviesAppDatabase::class.java,
            "movies_database"
        ).fallbackToDestructiveMigration().build()
    }
}