package com.example.therickandmorty.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.therickandmorty.data.api.ApiHelper
import com.example.therickandmorty.data.repository.RickAndMortyRepository
import com.example.therickandmorty.view.viewModel.CharactersViewModel
import com.example.therickandmorty.view.viewModel.EpisodeViewModel
import com.example.therickandmorty.view.viewModel.LocationViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            return CharactersViewModel(RickAndMortyRepository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(EpisodeViewModel::class.java)) {
            return EpisodeViewModel(RickAndMortyRepository(apiHelper)) as T
        }
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            return LocationViewModel(RickAndMortyRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
