package com.example.therickandmorty.view.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.therickandmorty.data.model.Character
import java.util.*

/**
 * Using stack to store the sharedData and pop the stored data
 * when user pressBack.
 */
class ShareSelectedCharacterViewModel : ViewModel() {
    private val stack = Stack<Character>()
    val selected = MutableLiveData<Character>()

    fun select(item: Character) {
        selected.value = item
        stack.add(item)
    }

    fun pop() {
        stack.pop()
        if(stack.isNotEmpty()) {
            selected.value = stack.peek()
        }

    }
}