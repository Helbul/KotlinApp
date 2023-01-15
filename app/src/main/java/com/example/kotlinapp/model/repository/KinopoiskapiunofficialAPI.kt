package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.dto.MoviesDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

private const val REQUEST_API_KEY = "X-API-KEY"

interface KinopoiskapiunofficialAPI {

    @GET("api/v2.2/films/top")
    fun getMoviesTop(
        @Header(REQUEST_API_KEY) token: String
    ): Call<MoviesDTO>
}