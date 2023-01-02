package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.Genre
import com.example.kotlinapp.model.Movie

object InMemoryMovieRepository: Repository {
    private val movies: ArrayList<Movie> = arrayListOf(
        Movie("Гарри Поттер", "Фильм о мальчике который выжил.", 5.2F, Genre.ACTION, 2001),
        Movie("Гарри Поттер", "Фильм о мальчике который выжил.", 10.0F, Genre.ACTION, 2001),
        Movie("Гарри Поттер", "Фильм о мальчике который выжил.", 6.3F, Genre.ACTION, 2001),
        Movie("Гарри Поттер", "Фильм о мальчике который выжил.", 10.0F, Genre.ACTION, 2001),
        Movie("Гарри Поттер", "Фильм о мальчике который выжил.", 10.0F, Genre.ACTION, 2001),
        Movie("Матрица", "Все не то, чем кажется.",9.0F, Genre.ANIME, 1999),
        Movie("Матрица", "Все не то, чем кажется.",8.0F, Genre.ANIME, 1999),
        Movie("Матрица", "Все не то, чем кажется.",7.0F, Genre.ANIME, 1999),
        Movie("Матрица", "Все не то, чем кажется.",10.0F, Genre.ANIME, 1999),
        Movie("Матрица", "Все не то, чем кажется.",2.5F, Genre.ANIME, 1999),
        Movie("Матрица", "Все не то, чем кажется.",10.0F, Genre.ANIME, 1999)
    )

    override fun getALL(): ArrayList<Movie> {
        return movies;
    }

    override fun add(movie: Movie) {
        movies.add(movie)
    }

    override fun remove(movie: Movie) {
        movies.remove(movie)
    }

    override fun remove(index: Int) {
        movies.removeAt(index)
    }

}