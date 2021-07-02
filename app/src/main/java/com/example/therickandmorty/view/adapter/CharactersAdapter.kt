package com.example.therickandmorty.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.therickandmorty.databinding.RecyclerItemCharacterBinding
import com.example.therickandmortyserver.CharacterListQuery

class CharactersAdapter(private val characterList: ArrayList<CharacterListQuery.Result?>) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

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

    fun addData(list: List<CharacterListQuery.Result?>) {
        characterList.addAll(list)
    }

    class CharacterViewHolder(private val itemCharacterBinding: RecyclerItemCharacterBinding) :
        RecyclerView.ViewHolder(itemCharacterBinding.root) {
        fun bind(data: CharacterListQuery.Result?) = with(itemCharacterBinding) {
            itemView.apply {
                headImageView.apply {
                    Glide.with(this.context)
                        .load(data?.image)
                        .into(this)
                }
                nameTextView.text = data?.name
            }
        }
    }
}