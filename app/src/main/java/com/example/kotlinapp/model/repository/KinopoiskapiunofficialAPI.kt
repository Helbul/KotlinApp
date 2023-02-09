package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.dto.FilterParametersDTO
import com.example.kotlinapp.model.dto.MoviesFilterDTO
import com.example.kotlinapp.model.dto.MoviesTopDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

private const val REQUEST_API_KEY = "X-API-KEY"

interface KinopoiskapiunofficialAPI {

    @GET("api/v2.2/films/top")
    fun getMoviesTop(
        @Header(REQUEST_API_KEY) token: String
    ): Call<MoviesTopDTO>

    @GET("api/v2.2/films/filter")
    fun getFilterParameters(
        @Header(REQUEST_API_KEY) token: String
    ): Call<FilterParametersDTO>

    @GET("api/v2.2/films")
    fun getMoviesFilterYear(
        @Header(REQUEST_API_KEY) token: String,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int
    ): Call<MoviesFilterDTO>

    @GET("api/v2.2/films")
    fun getMoviesFilterRating(
        @Header(REQUEST_API_KEY) token: String,
        @Query("ratingFrom") ratingMin: Int
    ): Call<MoviesFilterDTO>

    @GET("api/v2.2/films")
    fun getMoviesFilterGenre(
        @Header(REQUEST_API_KEY) token: String,
        @Query("genres") genreId: Int
    ): Call<MoviesFilterDTO>

    @GET("api/v2.2/films")
    fun getMoviesFilterYearGenreRating(
        @Header(REQUEST_API_KEY) token: String,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("genres") genreId: Int,
        @Query("ratingFrom") ratingMin: Int
    ): Call<MoviesFilterDTO>

    @GET("api/v2.2/films")
    fun getMoviesFilterYearGenre(
        @Header(REQUEST_API_KEY) token: String,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("genres") genreId: Int
    ): Call<MoviesFilterDTO>

    @GET("api/v2.2/films")
    fun getMoviesFilterYearRating(
        @Header(REQUEST_API_KEY) token: String,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("ratingFrom") ratingMin: Int
    ): Call<MoviesFilterDTO>

    @GET("api/v2.2/films")
    fun getMoviesFilterGenreRating(
        @Header(REQUEST_API_KEY) token: String,
        @Query("genres") genreId: Int,
        @Query("ratingFrom") ratingMin: Int
    ): Call<MoviesFilterDTO>


}