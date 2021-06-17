package com.example.therickandmorty.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.therickandmorty.R
import com.example.therickandmorty.data.model.Character
import com.example.therickandmorty.databinding.RecyclerItemCharacterBinding
import kotlinx.android.synthetic.main.recycler_item_character.view.*

class CharactersAdapter(
    private val characterList: ArrayList<Character>,
    _onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(character: Character, imageView: ImageView)
    }

    private var onItemClickListener: OnItemClickListener? = _onItemClickListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(
            RecyclerItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickListener
        )

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characterList[position])
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

    class CharacterViewHolder(
        itemCharacterBinding: RecyclerItemCharacterBinding,
        private var onItemClickListener: OnItemClickListener? = null
    ) :
        RecyclerView.ViewHolder(itemCharacterBinding.root) {
        fun bind(character: Character) {
            itemView.apply {

                setOnClickListener {
                    onItemClickListener?.onItemClick(character, itemView.headImageView)
                }

                headImageView.apply {
                    transitionName = character.id.toString()
                    Glide.with(this.context)
                        .load(character.image)
                        .into(this)
                }

                nameTextView.text = character.name
            }
        }
    }
}