package com.example.therickandmorty.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.therickandmorty.R
import com.example.therickandmorty.data.model.Character
import com.example.therickandmorty.databinding.RecyclerItemCharacterBinding

class CharactersAdapter(private val characterList: ArrayList<Character>, _onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    private var onItemClickListener: OnItemClickListener? = _onItemClickListener

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

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_characterListFragment_to_characterDetailFragment)
            onItemClickListener?.onItemClick(position)
        }
    }

    override fun getItemCount() = characterList.size

    fun getItem(position: Int) = characterList[position]

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