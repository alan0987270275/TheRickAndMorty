package com.example.therickandmorty.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.therickandmorty.R
import com.example.therickandmorty.data.api.ApiHelper
import com.example.therickandmorty.data.api.RetrofitBuilder
import com.example.therickandmorty.data.model.Character
import com.example.therickandmorty.data.model.Characters
import com.example.therickandmorty.databinding.FragmentCharacterListBinding
import com.example.therickandmorty.util.Status
import com.example.therickandmorty.util.ViewModelFactory
import com.example.therickandmorty.view.adapter.CharactersAdapter
import com.example.therickandmorty.view.viewModel.CharactersViewModel
import com.example.therickandmorty.view.viewModel.ShareSelectedCharacterViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CharacterListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CharacterListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val TAG: String = CharacterListFragment::class.java.name
    private val VISIBLE_THRESHOLD = 1

    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharactersViewModel
    private val shareViewModel: ShareSelectedCharacterViewModel by navGraphViewModels(R.id.nav_graph_main)
    private lateinit var adapter: CharactersAdapter
    private var loadMore = true

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
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fixPressBackSharedTransition()
        initView()
        initObservers()
    }


    /**
     * To solve the return transition problem
     */
    private fun fixPressBackSharedTransition() = with(binding) {
        postponeEnterTransition()
        recyclerView.post {
            startPostponedEnterTransition()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(CharactersViewModel::class.java)
    }

    private fun initView() = with(binding) {
        val gridLayoutManager = GridLayoutManager(context, 2)

        adapter = CharactersAdapter(object : CharactersAdapter.OnItemClickListener {

            override fun onItemClick(character: Character, imageView: ImageView) {
                shareViewModel.select(character)
                /**
                 * Using the FragmentNavigatorExtras to pass the view reference
                 * with the transition name to the Destination Fragment, for it to work it has to be
                 * unique otherwise it will target the last element with the same transition name.
                 */
                val extras = FragmentNavigatorExtras(
                    imageView to character.id.toString()
                )
                findNavController().navigate(
                    R.id.action_characterListFragment_to_characterDetailFragment,
                    null,
                    null,
                    extras
                )
            }
        })
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (loadMore) {
                    val lastItemPosition =
                        gridLayoutManager.findLastVisibleItemPosition() + VISIBLE_THRESHOLD
                    if (dy > 0 && lastItemPosition >= gridLayoutManager.itemCount) {
                        loadMore = false
                        val randomPage = (1..34).random().toString()
                        viewModel.addCharacters(randomPage)
                    }
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            val randomPage = (1..34).random().toString()
            viewModel.fetchCharacters(randomPage)
        }
    }

    private fun initObservers() {
        setDataToRecyclerView()
    }

    private fun setDataToRecyclerView() = with(binding) {
        viewModel.getCharacterList().observe(requireActivity(), Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { list ->
                        /**
                         * Must use toList() to create a new List everytime, due to
                         * using the AsyncListDiffer.
                         */
                        adapter.differ.submitList(list.results.toList())
                    }
                    loadMore = true
                    swipeRefreshLayout.isRefreshing = false

                }
                Status.LOADING -> {
                    swipeRefreshLayout.isRefreshing = true
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
         * @return A new instance of fragment CharacterListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CharacterListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}