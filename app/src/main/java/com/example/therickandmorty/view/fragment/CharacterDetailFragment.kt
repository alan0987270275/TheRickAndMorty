package com.example.therickandmorty.view.fragment

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.example.therickandmorty.R
import com.example.therickandmorty.data.model.Character
import com.example.therickandmorty.databinding.FragmentCharacterDetailBinding
import com.example.therickandmorty.view.adapter.EpisodeAdapter
import com.example.therickandmorty.view.viewModel.ShareSelectedCharacterViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CharacterDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CharacterDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val sharedModel: ShareSelectedCharacterViewModel by navGraphViewModels(R.id.nav_graph_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        /***
         * For the transition animation
         */
        sharedElementEnterTransition =
            TransitionInflater
                .from(context)
                .inflateTransition(android.R.transition.move)

        /**
         *   Implement Custom Back Navigation:
         *   when user pressBack in CharacterDetailFragment, we also need to pop out the data
         *   that stored in the ShareSelectedCharacterViewModel to retrieve previous one.
         */
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().popBackStack()
            sharedModel.pop()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        sharedModel.selected.value?.apply {
            setUpView(this)
        }
    }

    private fun setUpView(character: Character) = with(binding) {
        Glide.with(headImageView.context)
            .load(character.image)
            .into(headImageView)

        /**
         * Update the transitionNames for sharedElementEnterTransition
         */

        /**
         * Update the transitionNames for sharedElementEnterTransition
         */
        headImageView.transitionName = character.id.toString()

        nameTextView.text = character.name

        aliveStatusTextView.text = character.status
        when (character.status) {
            "Alive" -> {
                aliveStatusImageView.colorFilter =
                    PorterDuffColorFilter(
                        resources.getColor(R.color.green),
                        PorterDuff.Mode.SRC_ATOP
                    )
            }
            "unknown" -> {
                aliveStatusImageView.colorFilter =
                    PorterDuffColorFilter(
                        resources.getColor(R.color.gray),
                        PorterDuff.Mode.SRC_ATOP
                    )
            }
            "Dead" -> {
                aliveStatusImageView.colorFilter =
                    PorterDuffColorFilter(
                        resources.getColor(R.color.red),
                        PorterDuff.Mode.SRC_ATOP
                    )
            }

        }

        locationTextView.text = character.location.name
        firstSeenTextView.text = character.origin.name
        val locationOnClickListener = View.OnClickListener { view ->
            when (view) {
                firstSeenTextView -> {
                    val action =
                        CharacterDetailFragmentDirections.actionCharacterDetailFragmentToLocationFragment(
                            character.origin.url
                        )
                    findNavController().navigate(action)
                }
                locationTextView -> {
                    val action =
                        CharacterDetailFragmentDirections.actionCharacterDetailFragmentToLocationFragment(
                            character.location.url
                        )
                    findNavController().navigate(action)

                }
            }
        }
        locationTextView.setOnClickListener(
            if (locationTextView.text != "unknown") locationOnClickListener else null
        )
        firstSeenTextView.setOnClickListener {
            if (firstSeenTextView.text != "unknown") locationOnClickListener else null
        }
        initRecyclerView(character.episode)
    }

    private fun initRecyclerView(episode: List<String>) = with(binding) {
        val linearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        episodeRecyclerView.adapter =
            EpisodeAdapter(episode, object : EpisodeAdapter.OnItemClickListener {
                override fun onItemClick(episode: String) {
                    val action =
                        CharacterDetailFragmentDirections.actionCharacterDetailFragmentToEpisodeFragment(
                            episode
                        )
                    findNavController().navigate(action)
                }

            })
        episodeRecyclerView.layoutManager = linearLayoutManager
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CharacterDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}