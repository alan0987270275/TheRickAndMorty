package com.example.therickandmorty.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.therickandmorty.data.api.ApiHelper
import com.example.therickandmorty.data.repository.CharactersRepository
import com.example.therickandmorty.view.viewModel.CharactersViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharactersViewModel::class.java)) {
            return CharactersViewModel(CharactersRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
