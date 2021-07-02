package com.example.therickandmorty.data.repository

import com.example.therickandmorty.data.api.ApiHelper

class RickAndMortyRepository(private val apiHelper: ApiHelper) {

    suspend fun getCharacters() = apiHelper.getCharacters()

}
