package com.example.therickandmorty.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    const val BASE_CHARACTER_URL = BASE_URL + "character/"
    const val BASE_LOCATION_URL = BASE_URL + "location/"
    const val BASE_EPISODE_URL = BASE_URL + "episode/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)

}