package com.example.kotlinapp.ui.adapters

import com.example.kotlinapp.model.Movie

interface OnItemViewClickListener {
    fun onItemViewClick(movie: Movie)
}