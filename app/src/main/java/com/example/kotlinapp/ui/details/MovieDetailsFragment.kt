package com.example.kotlinapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentMovieDetailsBinding
import com.example.kotlinapp.model.Movie
import com.example.kotlinapp.model.dto.Films

class MovieDetailsFragment : Fragment () {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var viewModel : MovieDetailsViewModel
    private lateinit var isFavourite: ImageView
    private lateinit var isNotFavourite: ImageView


    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance (bundle: Bundle): MovieDetailsFragment {
            val fragment = MovieDetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

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
        arguments?.getParcelable<Movie>(BUNDLE_EXTRA)?.let {
            viewModel.setLiveDataMovie(it)
        }

        viewModel.getLiveDataMovie().observe(viewLifecycleOwner){
            movie -> renderData(movie)
        }

        viewModel.getLiveDataIsFavourite().observe(viewLifecycleOwner){
            like ->
            if (like) {
                isFavourite.visibility = ImageView.VISIBLE
                isNotFavourite.visibility = ImageView.GONE
            } else {
                isFavourite.visibility = ImageView.GONE
                isNotFavourite.visibility = ImageView.VISIBLE
            }
        }

        viewModel.containInFavourites()

        isFavourite = binding.favoriteTrue.apply { setOnClickListener {
            changeFavourite()
        } }

        isNotFavourite = binding.favoriteFalse.apply { setOnClickListener { changeFavourite() } }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

    private fun renderData(movie: Movie) {
        with(binding) {
            name.text = movie.nameRu
            rating.rating = movie.rating?.toFloatOrNull() ?: 0.0F
            genre.text = movie.genres[0]// переписать потом
            year.text = movie.year
            poster.load(movie.posterUrlPreview)

            //description.text = movie.
        }
    }

    private fun changeFavourite() {
        if (isFavourite.isVisible) {
            isFavourite.visibility = ImageView.GONE
            isNotFavourite.visibility = ImageView.VISIBLE
            viewModel.setLiveDataFavourite(false)
        } else {
            isFavourite.visibility = ImageView.VISIBLE
            isNotFavourite.visibility = ImageView.GONE
            viewModel.setLiveDataFavourite(true)
        }
    }


}