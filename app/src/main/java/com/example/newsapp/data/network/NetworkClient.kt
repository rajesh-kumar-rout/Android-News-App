package com.example.newsapp.data.network

import com.example.newsapp.util.BASE_URL
import com.example.newsapp.util.CommunicationException
import com.example.newsapp.util.UnKnownException
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(BaseInterceptor())
    .connectTimeout(20, TimeUnit.SECONDS)
    .readTimeout(20, TimeUnit.SECONDS)
    .build()

val networkDataSource: NetworkDataSource = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()
    .create(NetworkDataSource::class.java)

private suspend fun <T> call(func: suspend () -> Response<T>): Response<T> {
    try {
        return func()
    } catch (exception: Exception) {
        throw CommunicationException()
    }
}

suspend fun <T> fetch(func: suspend () -> Response<T>): T {
    val response = call(func)
    if (response.isSuccessful && response.body() != null) {
        return response.body()!!
    } else {
        throw UnKnownException()
    }
}