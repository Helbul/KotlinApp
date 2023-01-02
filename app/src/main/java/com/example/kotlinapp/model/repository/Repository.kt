package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.Movie

interface Repository {
    fun getALL(): ArrayList<Movie>

    fun add(movie: Movie)

    fun remove(movie: Movie)

    fun remove(index: Int)
}