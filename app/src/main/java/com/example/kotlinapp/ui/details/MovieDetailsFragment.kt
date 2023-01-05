package com.example.kotlinapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlinapp.databinding.FragmentMovieDetailsBinding
import com.example.kotlinapp.model.Movie

class MovieDetailsFragment : Fragment () {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let {
            renderData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun renderData(movie: Movie) {
        with(binding) {
            name.text = movie.name
            rating.rating = movie.rating
            genre.text = movie.genre.rusName
            year.text = movie.year.toString()
            description.text = movie.description
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance (bundle: Bundle): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}