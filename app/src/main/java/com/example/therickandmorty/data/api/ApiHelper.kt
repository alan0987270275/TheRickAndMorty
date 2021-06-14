package com.example.therickandmorty.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getCharacters(page: String) = apiService.getCharacter(page)
}