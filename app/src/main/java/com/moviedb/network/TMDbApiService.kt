package com.moviedb.network

import com.moviedb.network.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("region") region: String,
        @Query("language") language: String
    ): TMDbMoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int,
        @Query("region") region: String,
        @Query("language") language: String
    ): TMDbMoviesResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("region") region: String,
        @Query("language") language: String
    ): TMDbMoviesResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("region") region: String,
        @Query("language") language: String
    ): TMDbMoviesResponse

    @GET("search/movie")
    suspend fun getSearchMovie(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("region") region: String,
        @Query("language") language: String
    ): TMDbMoviesResponse

    @GET("movie/{movieId}?append_to_response=recommendations,credits,videos&")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("language") language: String
    ): TMDbMovieDetails

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(@Path("movieId") movieId: Int): TMDbMovieCredits

    @GET("movie/{movieId}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("movieId") movieId: Int,
        @Query("language") language: String
    ): TMDbMovieRecommendations

    @GET("genre/movie/list")
    suspend fun getGenreList(@Query("language") language: String): TMDbGenresResponse
}

