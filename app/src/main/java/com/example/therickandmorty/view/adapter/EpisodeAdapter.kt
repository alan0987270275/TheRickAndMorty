package com.example.therickandmorty.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.therickandmorty.data.api.RetrofitBuilder.BASE_EPISODE_URL
import com.example.therickandmorty.data.model.Character
import com.example.therickandmorty.databinding.RecyclerItemEpisodeBinding

class EpisodeAdapter(private val episodeList: List<String>) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EpisodeViewHolder(
            RecyclerItemEpisodeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(episodeList[position])
    }

    override fun getItemCount() = episodeList.size

    class EpisodeViewHolder(private val itemEpisodeBinding: RecyclerItemEpisodeBinding) :
        RecyclerView.ViewHolder(itemEpisodeBinding.root) {
        fun bind(episode: String) = with(itemEpisodeBinding) {
            itemView.apply {
                val episodeNumber = episode.substring(
                    BASE_EPISODE_URL.length,
                    episode.length
                )
                episodeTextView.text = episodeNumber
            }
        }
    }
}