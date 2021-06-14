package com.example.therickandmorty.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.therickandmorty.databinding.RecyclerItemCharacterBinding

class CharactersAdapter() : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(
            RecyclerItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 20
    }

    class CharacterViewHolder(private val itemCharacterBinding: RecyclerItemCharacterBinding) :
        RecyclerView.ViewHolder(itemCharacterBinding.root) {
        fun bind() = with(itemCharacterBinding) {

        }
    }
}