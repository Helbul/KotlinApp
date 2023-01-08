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
import com.example.kotlinapp.model.dto.Films
import com.example.kotlinapp.ui.moviesearch.MovieSearchFragment

class MovieSearchAdapter(private var itemClickListener: MovieSearchFragment.OnItemViewClickListener?)
    : RecyclerView.Adapter<MovieSearchAdapter.MovieSearchViewHolder>(){
        private var movieData: List<Films> = listOf()
        private lateinit var binding: ItemMovieBinding

    @SuppressLint("NotifyDataSetChanged")
    fun setMovie(data: ArrayList<Films>) {
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
        fun bind (movie: Films) {
            with(itemView) {
                findViewById<TextView>(R.id.item_name).text = movie.nameRu
                val ratingFloat = movie.rating?.toFloatOrNull()
                ratingFloat?.let {
                    findViewById<RatingBar>(R.id.item_rating).rating = ratingFloat
                } ?: run {
                    findViewById<RatingBar>(R.id.item_rating).rating = 0.0F
                }
                setOnClickListener { itemClickListener?.onItemViewClick(movie) }
            }
        }
    }
}