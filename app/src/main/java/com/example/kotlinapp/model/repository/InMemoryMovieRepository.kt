package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.MoviesLoader
import com.example.kotlinapp.model.dto.Films
import kotlin.collections.ArrayList

object InMemoryMovieRepository: Repository {
    //private var movies: List<MovieDTO>? = null

    override fun getALL(): ArrayList<Films>? {
        val dto = MoviesLoader.loadMoviesTop()
        //movies = dto?.films?.toList()
        return dto?.films
    }
}