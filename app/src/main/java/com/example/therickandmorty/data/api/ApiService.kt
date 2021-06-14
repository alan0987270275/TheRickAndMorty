package com.example.therickandmorty.data.api

import com.example.therickandmorty.data.model.Characters
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacter(@Query("page") page: String): Characters
}