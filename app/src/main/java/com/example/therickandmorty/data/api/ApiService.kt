package com.example.therickandmorty.data.api

import com.example.therickandmorty.data.model.Characters
import com.example.therickandmorty.data.model.Episode
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacter(@Query("page") page: String): Characters

    @GET("episode/{id}")
    suspend fun getSingleEpisode(@Path("id") id: String): Episode


}