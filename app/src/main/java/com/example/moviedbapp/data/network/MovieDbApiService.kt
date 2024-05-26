package com.example.moviedbapp.data.network
import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.themoviedb.org/3/"
fun getRetrofitInstance(): Retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .client(okhttpClient())
    .baseUrl(BASE_URL)
    .build()
fun okhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()
}
interface MovieDbApiService{
    @GET("discover/movie")
    suspend fun getDiscoverMovies() : ApiMovieDiscoverResponse
}