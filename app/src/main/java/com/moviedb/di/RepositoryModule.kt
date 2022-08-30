package com.moviedb.di

import com.moviedb.repositories.GenreRepository
import com.moviedb.repositories.MovieRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        MovieRepository(database = get(), tmdbApi = get())
    }

    single {
        GenreRepository(database = get(), tmDbApi = get())
    }
}