package com.example.therickandmorty.view.fragment

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.therickandmorty.R
import com.example.therickandmorty.databinding.FragmentCharacterDetailBinding
import com.example.therickandmorty.databinding.FragmentCharacterListBinding
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

    private val sharedModel: ShareSelectedCharacterViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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

    private fun initView() = with(binding)  {
        sharedModel.selected.value?.apply {
            Glide.with(headImageView.context)
                .load(this.image)
                .into(headImageView)

            nameTextView.text = this.name

            aliveStatusTextView.text = this.status
            when(this.status) {
                "Alive" -> {
                    aliveStatusImageView.colorFilter =
                        PorterDuffColorFilter(resources.getColor(R.color.green), PorterDuff.Mode.SRC_ATOP)
                }
                "unknown" -> {
                    aliveStatusImageView.colorFilter =
                        PorterDuffColorFilter(resources.getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP)
                }
                "Dead" -> {
                    aliveStatusImageView.colorFilter =
                        PorterDuffColorFilter(resources.getColor(R.color.red), PorterDuff.Mode.SRC_ATOP)
                }

            }

            locationTextView.text = this.location.name
            firstSeenTextView.text = this.origin.name
        }

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