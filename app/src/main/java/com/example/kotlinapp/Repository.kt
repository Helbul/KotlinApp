package com.example.kotlinapp

object Repository {
    val movies: ArrayList<Movie> = arrayListOf(
        Movie("Гарри Поттер", "Фильм о мальчике который выжил."),
        Movie("Матрица", "Все не то, чем кажется.")
    )

    fun addMovie (movie: Movie) {
        movies.add(movie)
    }

}