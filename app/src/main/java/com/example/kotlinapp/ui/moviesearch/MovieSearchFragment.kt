package com.example.kotlinapp.ui.moviesearch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinapp.AppState
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentMovieSearchBinding
import com.example.kotlinapp.model.Movie
import com.example.kotlinapp.ui.adapters.MovieSearchAdapter
import com.example.kotlinapp.ui.adapters.OnItemViewClickListener
import com.example.kotlinapp.ui.details.MovieDetailsFragment
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movie_details.view.*
import kotlinx.android.synthetic.main.fragment_movie_search.*



class MovieSearchFragment : Fragment() {
    private var _binding: FragmentMovieSearchBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var viewModel : MovieSearchViewModel

    private var adapter : MovieSearchAdapter? = null

    companion object {
        fun getInstance() = MovieSearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter?.removeListener()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movieSearchRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.movieSearchRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(MovieSearchViewModel::class.java)

        viewModel.liveData.observe(viewLifecycleOwner
        ) { appState -> renderData(appState) }

        viewModel.getMoviesTop()

        var id = viewModel.getLiveDataChipId()
        viewModel.getLiveDataChipId()?.let {
            binding.chipGroup.check(viewModel.getLiveDataChipId()!!)
        }

        viewModel.liveDataRating.value?.let {
            binding.ratingBar.rating = it
        }

        viewModel.liveDataYear.value?.let {
            binding.textYear.text = it.toString()
            binding.seekBar.progress = it
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutFilter: ConstraintLayout = binding.layoutFilter


        val toolbar: Toolbar = binding.toolbar
        toolbar.setOnMenuItemClickListener { item ->
            if (item != null) {
                when (item.itemId) {
                    R.id.menu_filter -> {
                        if (layoutFilter.isVisible) {
                            layoutFilter.visibility = View.GONE
                        } else {
                            layoutFilter.visibility = View.VISIBLE
                        }
                    }
                }
            }
            true
        }

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == View.NO_ID) {
                viewModel.setLiveDataChipId(null)
            } else {
                viewModel.setLiveDataChipId(checkedId)
            }
            //val genre = group.findViewById<Chip>(checkedId).text
            //viewModel.getMoviesFilter()
        }


        val textYear = binding.textYear
        val seekBar = binding.seekBar


        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar, i: Int, b: Boolean
                ) {
                    // Do something
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    // Do something
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    textYear.text = seekBar.progress.toString()
                    viewModel.liveDataYear.value = seekBar.progress
                    viewModel.getMoviesFilter()
                }
            })


        val ratingBar = binding.ratingBar
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(requireContext(), "$rating", Toast.LENGTH_SHORT).show()
            viewModel.liveDataRating.value = rating
            viewModel.getMoviesFilter()
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                adapter = MovieSearchAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(movie: Movie) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putParcelable(MovieDetailsFragment.BUNDLE_EXTRA, movie)
                            }
                            manager.beginTransaction()
                                .add(R.id.container, MovieDetailsFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
                    }
                }).apply {
                    setMovie(appState.movieData)
                }
                movieSearchRecyclerView.adapter = adapter
            }

            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE

                Snackbar.make(binding.mainView, "Error", Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.reload)) {
                        viewModel.getMoviesTop()
                    }
                    .show()
            }
        }
    }
}