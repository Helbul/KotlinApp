package com.example.kotlinapp.utils

import com.example.kotlinapp.model.Movie
import com.example.kotlinapp.model.dto.Countries
import com.example.kotlinapp.model.dto.Genres
import com.example.kotlinapp.model.dto.MoviesDTO
import com.example.kotlinapp.model.room.MovieEntity

fun startThread (f: () -> Unit) {
    Thread {
        try {
            f()
        } catch (e: Exception) {
            e.stackTrace
        }

    }.start()
}

fun convertMovieDtoToMovie (moviesDTO: MoviesDTO) : List<Movie> {
    var movies: ArrayList<Movie> = arrayListOf()
    moviesDTO.films.let {
        for (films in moviesDTO.films) {
            val movie = Movie (
                filmId = films.filmId,
                nameRu = films.nameRu,
                nameEn = films.nameEn,
                year = films.year,
                countries = convertListCountriesToListString(films.countries),
                genres = convertListGenresToListString(films.genres),
                rating = films.rating,
                posterUrlPreview = films.posterUrlPreview
                    )
            movies.add(movie)
        }
    }
    return movies
}

fun convertMovieEntityToMovie (entityList: List<MovieEntity>) = entityList
    .map {
        Movie(
            it.filmId,
            it.nameRu,
            it.nameEn,
            it.year,
            it.countries,
            it.genres,
            it.rating,
            it.posterUrlPreview
        )
    }

fun convertMovieToMovieEntity (movie: Movie) = MovieEntity (
        0,
        movie.filmId,
        movie.nameRu,
        movie.nameEn,
        movie.year,
        movie.countries,
        movie.genres,
        movie.rating,
        movie.posterUrlPreview
    )

private fun convertListCountriesToListString (listCountries: List<Countries>) = listCountries
    .map {
            countries ->  countries.country
    } as ArrayList<String>

private fun convertListGenresToListString (listGenres: List<Genres>) = listGenres
    .map {
            genres -> genres.genre
    } as ArrayList<String>

