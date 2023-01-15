package com.example.kotlinapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentMovieDetailsBinding
import com.example.kotlinapp.model.dto.Films

class MovieDetailsFragment : Fragment () {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : MovieDetailsViewModel

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
        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)
        arguments?.getParcelable<Films>(BUNDLE_EXTRA)?.let {
            viewModel.setLiveDataMovie(it)
        }
        viewModel.getLiveDataMovie().observe(viewLifecycleOwner){
            movie -> renderData(movie)
        }
//        arguments?.getParcelable<Films>(BUNDLE_EXTRA)?.let {
//            renderData(it)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun renderData(movie: Films) {
        with(binding) {
            name.text = movie.nameRu
            rating.rating = movie.rating?.toFloatOrNull() ?: 0.0F
            genre.text = movie.genres[0].genre// переписать потом
            year.text = movie.year
            poster.load(movie.posterUrlPreview)

            //description.text = movie.
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