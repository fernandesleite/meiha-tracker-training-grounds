package com.moviedb.network

import okhttp3.Interceptor
import okhttp3.Response


class ApiInterceptor : Interceptor {
    // Interceptor to add the api_key parameter to retrofit
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val httpUrl = request.url.newBuilder().addQueryParameter("api_key", API_KEY).build()
        request = request.newBuilder().url(httpUrl).build()
        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY = "d686c93461aba88b229359f910bd76d1"
    }
}