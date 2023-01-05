package com.example.kotlinapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.R
import com.example.kotlinapp.databinding.ItemMovieBinding
import com.example.kotlinapp.model.Movie
import com.example.kotlinapp.ui.moviesearch.MovieSearchFragment

class MovieSearchAdapter(private var itemClickListener: MovieSearchFragment.OnItemViewClickListener?)
    : RecyclerView.Adapter<MovieSearchAdapter.MovieSearchViewHolder>(){
        private var movieData: List<Movie> = listOf()
        private lateinit var binding: ItemMovieBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setMovie(data: List<Movie>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSearchViewHolder {
        binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieSearchViewHolder(
            binding.root
        )
    }

    override fun onBindViewHolder(holder: MovieSearchViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    fun removeListener(){
        itemClickListener = null
    }

    inner class MovieSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind (movie: Movie) {
            with(itemView) {
                findViewById<TextView>(R.id.item_name).text = movie.name
                findViewById<RatingBar>(R.id.item_rating).rating = movie.rating
                setOnClickListener { itemClickListener?.onItemViewClick(movie) }
            }
        }
    }
}