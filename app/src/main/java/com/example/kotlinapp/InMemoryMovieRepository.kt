package com.example.kotlinapp

object InMemoryMovieRepository: Repository {
    private val movies: ArrayList<Movie> = arrayListOf(
        Movie("Гарри Поттер", "Фильм о мальчике который выжил.", 10.0F, Genre.ACTION),
        Movie("Матрица", "Все не то, чем кажется.",10.0F, Genre.ACTION)
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