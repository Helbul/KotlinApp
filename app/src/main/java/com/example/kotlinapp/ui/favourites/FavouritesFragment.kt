package com.example.kotlinapp.ui.favourites

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.kotlinapp.AppState
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.FragmentFavouritesBinding
import com.example.kotlinapp.databinding.FragmentMovieSearchBinding
import com.example.kotlinapp.model.Movie
import com.example.kotlinapp.ui.adapters.MovieSearchAdapter
import com.example.kotlinapp.ui.adapters.OnItemViewClickListener
import com.example.kotlinapp.ui.details.MovieDetailsFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favourites.*

class FavouritesFragment : Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var viewModel: FavouritesViewModel

    private var adapter : MovieSearchAdapter? = null

    companion object {
        fun newInstance() = FavouritesFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter?.removeListener()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.favouritesRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.favouritesRecyclerView.adapter = adapter
        viewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)

        viewModel.favouritesLiveData.observe(viewLifecycleOwner)
        { appState -> renderData(appState)}

        viewModel.getMovie()
    }

    private fun renderData(appState: AppState) {
        when(appState) {

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
                favouritesRecyclerView.adapter = adapter
            }

            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }

            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE

                Snackbar.make(binding.mainFavouritesLayout, "Error", Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.reload)) {
                        viewModel.getMovie()
                    }
                    .show()
            }
        }
    }

}