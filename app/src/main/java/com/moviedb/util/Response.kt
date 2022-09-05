package com.moviedb.util

sealed class Response<T>(val data: T? = null, val message: Exception? = null) {
    class Success<T>(data: T): Response<T>(data)
    class NetworkError<T>(error: Exception?): Response<T>(null, error)
    class GenericError<T>(error: Exception?): Response<T>(null, error)
}
