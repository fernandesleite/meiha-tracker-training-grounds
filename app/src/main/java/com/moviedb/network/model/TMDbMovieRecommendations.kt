package com.moviedb.network.model

data class TMDbMovieRecommendations(
    val page: Int,
    val results: List<TMDbMovie>
)