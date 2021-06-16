package com.example.therickandmorty.view.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therickandmorty.data.model.Characters
import com.example.therickandmorty.data.repository.CharactersRepository
import com.example.therickandmorty.util.Resource
import kotlinx.coroutines.launch

class CharactersViewModel (private val charactersRepository: CharactersRepository) : ViewModel() {

    private val characterList = MutableLiveData<Resource<Characters>>()

    init {
        val randomPage = (1..34).random().toString()
        fetchCharacters(randomPage)
    }
    fun fetchCharacters(page: String) {
        viewModelScope.launch {
            characterList.postValue(Resource.loading(null))
            try {
                val data = charactersRepository.getCharacters(page)
                if(data.results.isNotEmpty()) {
                    characterList.postValue(Resource.success(data))
                } else {
                    characterList.postValue(Resource.success(null))
                }
            } catch (e: Exception) {
                characterList.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getCharacterList() = characterList
}