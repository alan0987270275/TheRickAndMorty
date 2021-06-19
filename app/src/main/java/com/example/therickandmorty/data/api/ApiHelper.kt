package com.example.therickandmorty.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getCharacters(page: String) = apiService.getCharacter(page)

    suspend fun getMultipleCharacter(ids: String) = apiService.getMultipleCharacter(ids)

    suspend fun getEpisode(id: String) = apiService.getSingleEpisode(id)
}