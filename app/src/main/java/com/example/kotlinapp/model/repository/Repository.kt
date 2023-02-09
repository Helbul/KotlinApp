package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.dto.FilterParametersDTO
import com.example.kotlinapp.model.dto.MoviesFilterDTO
import com.example.kotlinapp.model.dto.MoviesTopDTO
import retrofit2.Callback

interface Repository {
    fun getMoviesTop(callback: Callback<MoviesTopDTO>)

    fun getMoviesFilter(
        year: Int?,
        genreId: Int?,
        rating: Int?,
        callback: Callback<MoviesFilterDTO>
    )

    fun getFilterParameters(callback: Callback<FilterParametersDTO>)
}