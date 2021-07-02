package com.example.therickandmorty.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therickandmorty.data.model.Characters
import com.example.therickandmorty.data.repository.RickAndMortyRepository
import com.example.therickandmorty.util.Resource
import com.example.therickandmortyserver.CharacterListQuery
import kotlinx.coroutines.launch

class CharactersViewModel (private val rickAndMortyRepository: RickAndMortyRepository) : ViewModel() {
    private val TAG = "CharactersViewModel"
    private val characterList = MutableLiveData<Resource<List<CharacterListQuery.Result?>>>()

    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        viewModelScope.launch {
            characterList.postValue(Resource.loading(null))
            try {
                val response = rickAndMortyRepository.getCharacters()
                if(response.data?.characters?.results?.isNotEmpty() == true) {
                    Log.d(TAG, "${response?.data?.characters?.results!![0]}")
                    if(response?.data?.characters?.results != null) {
                        characterList.postValue(Resource.success(response.data!!.characters?.results))
                    }

                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }

    fun getCharacterList() = characterList

}