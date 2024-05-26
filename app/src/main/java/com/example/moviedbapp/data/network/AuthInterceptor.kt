package com.example.moviedbapp.data.network

import com.example.moviedbapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val apiKey = BuildConfig.API_KEY
        apiKey.let{
            requestBuilder.addHeader("Authorization", "Bearer $apiKey")
        }
        return chain.proceed(requestBuilder.build())
    }
}