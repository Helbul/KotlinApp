package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.Movie
import com.example.kotlinapp.model.room.MovieEntity

interface LocalRepository {
    fun getAllFavourites(): List<Movie>

    fun saveEntity(movie: Movie)

    fun removeEntity(movie: Movie)

    fun containsEntity(movie: Movie) : Boolean

}