package com.example.kotlinapp

interface Repository {
    fun getALL(): ArrayList<Movie>

    fun add(movie: Movie)

    fun remove(movie: Movie)

    fun remove(index: Int)
}