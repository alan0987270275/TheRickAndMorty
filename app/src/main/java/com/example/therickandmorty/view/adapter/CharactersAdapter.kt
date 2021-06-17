package com.example.therickandmorty.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.therickandmorty.data.model.Character
import com.example.therickandmorty.databinding.RecyclerItemCharacterBinding
import kotlinx.android.synthetic.main.recycler_item_character.view.*

class CharactersAdapter(
    _onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(character: Character, imageView: ImageView)
    }

    private var onItemClickListener: OnItemClickListener? = _onItemClickListener

    private val differCallback = object : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

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
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount() = differ.currentList.size

    inner class CharacterViewHolder(
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