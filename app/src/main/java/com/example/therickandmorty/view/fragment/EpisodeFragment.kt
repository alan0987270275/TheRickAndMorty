package com.example.therickandmorty.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.therickandmorty.data.api.ApiHelper
import com.example.therickandmorty.data.api.RetrofitBuilder
import com.example.therickandmorty.data.model.Episode
import com.example.therickandmorty.databinding.FragmentEpisodeBinding
import com.example.therickandmorty.util.Status
import com.example.therickandmorty.util.ViewModelFactory
import com.example.therickandmorty.view.viewModel.EpisodeViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EpisodeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EpisodeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val TAG = EpisodeFragment::javaClass.name
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentEpisodeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: EpisodeViewModel
    val args: EpisodeFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }


    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(EpisodeViewModel::class.java)
    }

    private fun initView(episode: Episode) = with(binding) {
        episodeNameTextView.text = episode.episode + " - "+ episode.name
        episodeAirDateTextView.text = episode.air_date
    }

    private fun initObservers() {
        /**
         * Get data from API and set the path param with episode id.
         */
        viewModel.fetchEpisode(args.episode)
        viewModel.getEpisode().observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { episode ->
                        initView(episode)
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Log.e(TAG, "VIEWMODEL ERROR: ${it.message}")
                }
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EpisodeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EpisodeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}