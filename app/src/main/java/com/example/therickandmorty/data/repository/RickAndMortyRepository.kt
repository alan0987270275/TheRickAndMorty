package com.example.therickandmorty.data.repository

import com.example.therickandmorty.data.api.ApiHelper

class RickAndMortyRepository(private val apiHelper: ApiHelper) {
    suspend fun getCharacters(page: String) = apiHelper.getCharacters(page)

    suspend fun getMultipleCharacter(ids: String) = apiHelper.getMultipleCharacter(ids)

    suspend fun getSingleEpisode(id: String) = apiHelper.getEpisode(id)
}