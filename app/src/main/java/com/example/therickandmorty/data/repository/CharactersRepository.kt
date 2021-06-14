package com.example.therickandmorty.data.repository

import com.example.therickandmorty.data.api.ApiHelper

class CharactersRepository(private val apiHelper: ApiHelper) {
    suspend fun getCharacters(page: String) = apiHelper.getCharacters(page)
}