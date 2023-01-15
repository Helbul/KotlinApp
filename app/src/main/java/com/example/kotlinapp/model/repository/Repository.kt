package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.dto.Films
import com.example.kotlinapp.model.dto.MoviesDTO
import retrofit2.Callback

interface Repository {
    fun getMoviesTop(callback: Callback<MoviesDTO>)
}