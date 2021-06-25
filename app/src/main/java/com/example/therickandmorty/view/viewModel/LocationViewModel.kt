package com.example.therickandmorty.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therickandmorty.data.model.Character
import com.example.therickandmorty.data.model.Episode
import com.example.therickandmorty.data.model.Location
import com.example.therickandmorty.data.repository.RickAndMortyRepository
import com.example.therickandmorty.util.Resource
import kotlinx.coroutines.launch

class LocationViewModel(
    private val rickAndMortyRepository: RickAndMortyRepository
) : ViewModel() {

    private val characterList = MutableLiveData<Resource<List<Character>>>()
    private val location  = MutableLiveData<Resource<Location>>()

    /***
     * For first time fetching.
     */
    fun fetchLocation(id: String) {
        viewModelScope.launch {
            location.postValue(Resource.loading(null))
            try {
                val data = rickAndMortyRepository.getSingleLocation(id)
                /**
                 * Save first time fetched data into a buffer for the load more action.
                 */
                if(data != null) {
                    location.postValue(Resource.success(data))
                } else {
                    location.postValue(Resource.success(null))
                }
            } catch (e: Exception) {
                location.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun fetchCharacters(ids: String) {
        viewModelScope.launch {
            characterList.postValue(Resource.loading(null))
            try {
                val data = rickAndMortyRepository.getMultipleCharacter(ids)
                if(data != null) {
                    characterList.postValue(Resource.success(data))
                } else {
                    characterList.postValue(Resource.success(null))
                }
            } catch (e: Exception) {
                characterList.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getCharacterList() : LiveData<Resource<List<Character>>> {
        return characterList
    }

    fun getLocation() : LiveData<Resource<Location>> {
        return location
    }
}