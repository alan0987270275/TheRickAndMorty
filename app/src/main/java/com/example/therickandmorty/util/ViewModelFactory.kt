package com.example.therickandmorty.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.therickandmorty.data.api.ApiHelper
import com.example.therickandmorty.data.repository.RickAndMortyRepository
import com.example.therickandmorty.view.CharactersViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            return CharactersViewModel(RickAndMortyRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
