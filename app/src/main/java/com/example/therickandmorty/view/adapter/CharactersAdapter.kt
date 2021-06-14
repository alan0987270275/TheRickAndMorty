package com.example.therickandmorty.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.therickandmorty.data.model.Character
import com.example.therickandmorty.databinding.RecyclerItemCharacterBinding

class CharactersAdapter(private val characterList: ArrayList<Character>) :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(
            RecyclerItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    override fun getItemCount() = characterList.size

    fun renderCharacters(list: List<Character>) {
        characterList.apply {
            clear()
            addAll(list)
        }
    }

    fun addAllCharacters(list: List<Character>) {
        characterList.addAll(list)
    }

    fun addCharacter(character: Character) {
        characterList.add(character)
    }

    class CharacterViewHolder(private val itemCharacterBinding: RecyclerItemCharacterBinding) :
        RecyclerView.ViewHolder(itemCharacterBinding.root) {
        fun bind(character: Character) = with(itemCharacterBinding) {
            itemView.apply {
                itemView.apply {

                    Glide.with(headImageView.context)
                        .load(character.image)
                        .into(headImageView)

                    nameTextView.text = character.name
                }
            }
        }
    }
}