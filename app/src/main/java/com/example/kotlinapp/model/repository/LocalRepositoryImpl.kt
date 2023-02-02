package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.Movie
import com.example.kotlinapp.model.room.FavouritesDao
import com.example.kotlinapp.utils.convertMovieEntityToMovie
import com.example.kotlinapp.utils.convertMovieToMovieEntity

class LocalRepositoryImpl (
    private val localDataSource: FavouritesDao
    ) : LocalRepository{

    override fun getAllFavourites(): List<Movie> {
        return convertMovieEntityToMovie(localDataSource.all())
    }

    override fun saveEntity(movie: Movie) {
        localDataSource.insert(convertMovieToMovieEntity(movie))
    }

    override fun removeEntity(movie: Movie) {
        //убрать !!
        localDataSource.deleteByFilmId(movie.filmId!!.toLong())
    }

    override fun containsEntity(movie: Movie): Boolean {
        movie.filmId?.let {
            return localDataSource.exists(movie.filmId!!)
        }.run {
            return false
        }
    }
}