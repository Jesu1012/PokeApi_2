package com.example.pokeapi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    val instance: Retrofit by lazy {
        // Configurar interceptor de logging
        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        // Configurar cliente HTTP con interceptor
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // Configurar Retrofit con cliente HTTP
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
