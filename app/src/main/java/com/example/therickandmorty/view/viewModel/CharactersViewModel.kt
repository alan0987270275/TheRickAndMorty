package com.example.therickandmorty.view.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therickandmorty.data.model.Characters
import com.example.therickandmorty.data.repository.RickAndMortyRepository
import com.example.therickandmorty.util.Resource
import kotlinx.coroutines.launch

class CharactersViewModel (private val rickAndMortyRepository: RickAndMortyRepository) : ViewModel() {

    private val characterList = MutableLiveData<Resource<Characters>>()
    private var oldCharacterList: Characters? = null

    init {
        val randomPage = (1..34).random().toString()
        fetchCharacters(randomPage)
    }

    /***
     * For first time fetch and refresh.
     */
    fun fetchCharacters(page: String) {
        viewModelScope.launch {
            characterList.postValue(Resource.loading(null))
            try {
                val data = rickAndMortyRepository.getCharacters(page)
                /**
                 * Save first time fetched data into a buffer for the load more action.
                 */
                oldCharacterList = data
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

    /***
     * For load more.
     */
    fun addCharacters(page: String) {
        viewModelScope.launch {
            characterList.postValue(Resource.loading(null))
            try {
                val data = rickAndMortyRepository.getCharacters(page)
                if(data.results.isNotEmpty()) {
                    /**
                     * Get old data and add new data into it.
                     */
                    oldCharacterList?.results?.addAll(data.results)
                    characterList.postValue(Resource.success(oldCharacterList))
                }
            } catch (e: Exception) {
                characterList.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    private fun <T> MutableLiveData<T>.notifyObserver() {
        // Make this call can using the background thread to change the LiveData,
        this.postValue(this.value)
        // Using setValue can only call in MainThread
//        this.value = this.value
    }

    fun getCharacterList() : LiveData<Resource<Characters>>{
        return characterList
    }
}