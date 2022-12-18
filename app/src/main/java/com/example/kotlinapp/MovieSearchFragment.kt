package com.example.kotlinapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.kotlinapp.databinding.FragmentMovieSearchBinding
import com.google.android.material.snackbar.Snackbar

class MovieSearchFragment : Fragment() {
    private var _binding: FragmentMovieSearchBinding? = null
    private val binding
        get() = _binding!!

    companion object {
        fun newInstance() = MovieSearchFragment()
    }

    private lateinit var viewModel: MovieSearchViewModel

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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MovieSearchViewModel::class.java)

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


        binding.chipAnime
            .setOnClickListener {
                Toast.makeText(requireContext(), R.string.anime, Toast.LENGTH_SHORT)
                    .show()
            }
        binding.chipAction
            .setOnClickListener {
                Toast.makeText(requireContext(), R.string.action, Toast.LENGTH_SHORT)
                    .show()
            }
        binding.chipAdventures
            .setOnClickListener {
                Toast.makeText(requireContext(), R.string.adventures, Toast.LENGTH_SHORT)
                    .show()
            }
        //TO DO


        val textYear = binding.textYear
        val seekBar = binding.seekBar
        var year = seekBar.progress
        textYear.text = "$year"


        seekBar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar, i: Int, b: Boolean
                ) {
                    year = i
                    textYear.text = "$year"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    // Do something
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    // Do something
                }
            })


        val ratingBar = binding.ratingBar
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            Toast.makeText(requireContext(), "$rating", Toast.LENGTH_SHORT).show()
        }


//        val observer = Observer<AppState> {
//            renderData(it)
//        }

        viewModel.getLiveData().observe(viewLifecycleOwner
        ) { appState -> renderData(appState) }
        viewModel.getMovie()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movie = appState.movieData
                binding.loadingLayout.visibility = View.GONE
                setData(movie)
            }

            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE

                Snackbar.make(binding.mainView, "Error", Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.reload)) {
                        viewModel.getMovie()
                    }
                    .show()
            }
        }
    }

    private fun setData (movie: Movie) {
        binding.textCenter.text = movie.name
    }
}