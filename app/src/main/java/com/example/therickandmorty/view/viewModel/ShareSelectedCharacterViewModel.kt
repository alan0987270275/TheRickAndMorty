package com.example.therickandmorty.view.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.therickandmorty.data.model.Character

class ShareSelectedCharacterViewModel : ViewModel() {
    val selected = MutableLiveData<Character>()

    fun select(item: Character) {
        selected.value = item
    }
}