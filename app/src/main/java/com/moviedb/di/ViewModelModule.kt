package com.moviedb.di

import com.moviedb.feature.movieDetails.viewModel.MovieDetailsViewModel
import com.moviedb.feature.movieList.viewModel.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieDetailsViewModel(movieRepository = get(), application = get()) }

    viewModel { MovieListViewModel(movieRepository = get(), genreRepository = get(), application = get()) }
}