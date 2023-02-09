package com.example.kotlinapp.utils

import com.example.kotlinapp.model.Movie
import com.example.kotlinapp.model.dto.Countries
import com.example.kotlinapp.model.dto.Genres
import com.example.kotlinapp.model.dto.MoviesFilterDTO
import com.example.kotlinapp.model.dto.MoviesTopDTO
import com.example.kotlinapp.model.room.MovieEntity


fun convertMovieTopDtoToMovie (moviesTopDTO: MoviesTopDTO) : List<Movie> {
    val movies: ArrayList<Movie> = arrayListOf()
    moviesTopDTO.films.let {
        for (films in moviesTopDTO.films) {
            val movie = Movie (
                filmId = films.filmId,
                nameRu = films.nameRu,
                nameEn = films.nameEn,
                year = films.year,
                countries = convertListCountriesToListString(films.countries),
                genres = convertListGenresToListString(films.genres),
                rating = films.rating?.toFloatOrNull(),
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

fun convertMoviesFilterDtoToMovie (moviesFilterDTO: MoviesFilterDTO) : List<Movie> {
    val movies: ArrayList<Movie> = arrayListOf()
    moviesFilterDTO.items.let {
        for (movie in moviesFilterDTO.items) {
            val movie = Movie (
                filmId = movie.kinopoiskId,
                nameRu = movie.nameRu,
                nameEn = movie.nameEn,
                year = movie.year.toString(),
                countries = convertListCountriesToListString(movie.countries),
                genres = convertListGenresToListString(movie.genres),
                rating = movie.ratingKinopoisk?.toFloat(),
                posterUrlPreview = movie.posterUrlPreview
            )
            movies.add(movie)
        }
    }
    return movies
}

private fun convertListCountriesToListString (listCountries: List<Countries>) = listCountries
    .map {
            countries ->  countries.country
    } as ArrayList<String>

private fun convertListGenresToListString (listGenres: List<Genres>) = listGenres
    .map {
            genres -> genres.genre
    } as ArrayList<String>

