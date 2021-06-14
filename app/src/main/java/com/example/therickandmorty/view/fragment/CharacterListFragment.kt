package com.example.therickandmorty.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.therickandmorty.R
import com.example.therickandmorty.data.api.ApiHelper
import com.example.therickandmorty.data.api.RetrofitBuilder
import com.example.therickandmorty.data.model.Characters
import com.example.therickandmorty.databinding.FragmentCharacterListBinding
import com.example.therickandmorty.util.Status
import com.example.therickandmorty.util.ViewModelFactory
import com.example.therickandmorty.view.adapter.CharactersAdapter
import com.example.therickandmorty.view.viewModel.CharactersViewModel

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
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharactersViewModel
    private lateinit var adapter: CharactersAdapter

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
        initView()
        initObservers()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(CharactersViewModel::class.java)
    }

    private fun initView() = with(binding) {
        val gridLayoutManager = GridLayoutManager(context, 2)
        adapter = CharactersAdapter(arrayListOf())

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = adapter
    }

    private fun initObservers() {
        val randomPage = (1..34).random().toString()
        setDataToRecyclerView(randomPage, "render")
    }

    private fun setDataToRecyclerView(randomPage: String, action: String) = with(binding) {
        viewModel.getCharacterList().observe(requireActivity(), Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { list -> renderList(list) }

                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Log.e(TAG,"VIEWMODEL ERROR: ${it.message}")
                }
            }
        })
    }

    private fun renderList(characters: Characters) {
        adapter.apply {
            renderCharacters(characters.results)
            notifyDataSetChanged()
        }
    }

    private fun addAllList(characters: Characters) {
        adapter.apply {
            addAllCharacters(characters.results)
            notifyDataSetChanged()
        }
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