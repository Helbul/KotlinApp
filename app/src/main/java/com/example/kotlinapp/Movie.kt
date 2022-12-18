package com.example.kotlinapp

data class Movie(
    val name: String = "Фильм",
    val description: String = "Описание",
    val rating: Float = 5.0F,
    val genre: Genre = Genre.ACTION)
