package com.example.therickandmorty.view.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.therickandmorty.data.repository.CharactersRepository
import com.example.therickandmorty.util.Resource
import kotlinx.coroutines.Dispatchers

class CharactersViewModel (private val charactersRepository: CharactersRepository) : ViewModel() {
    fun getCharacters(page: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = charactersRepository.getCharacters(page)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}