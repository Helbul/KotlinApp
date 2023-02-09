package com.example.kotlinapp.model.repository

import com.example.kotlinapp.BuildConfig
import com.example.kotlinapp.model.dto.FilterParametersDTO
import com.example.kotlinapp.model.dto.MoviesFilterDTO
import com.example.kotlinapp.model.dto.MoviesTopDTO
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val kinopoiskapiunofficialAPI = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(KinopoiskapiunofficialAPI::class.java)

    fun getMoviesTop(callback: Callback<MoviesTopDTO>) {
        kinopoiskapiunofficialAPI.getMoviesTop(BuildConfig.MOVIES_API_KEY).enqueue(callback)
    }

    fun getFilterParameters(callback: Callback<FilterParametersDTO>) {
        kinopoiskapiunofficialAPI.getFilterParameters(BuildConfig.MOVIES_API_KEY).enqueue(callback)
    }

    fun getMoviesFilterYear(year: Int, callback: Callback<MoviesFilterDTO>){
        kinopoiskapiunofficialAPI.getMoviesFilterYear(BuildConfig.MOVIES_API_KEY, year, year)
            .enqueue(callback)
    }

    fun getMoviesFilterGenre(genreId: Int, callback: Callback<MoviesFilterDTO>){
        kinopoiskapiunofficialAPI.getMoviesFilterGenre(BuildConfig.MOVIES_API_KEY, genreId)
            .enqueue(callback)
    }

    fun getMoviesFilterRating(rating: Int, callback: Callback<MoviesFilterDTO>){
        kinopoiskapiunofficialAPI.getMoviesFilterGenre(BuildConfig.MOVIES_API_KEY, rating)
            .enqueue(callback)
    }

    fun getMoviesFilterYearGenreRating(
        year: Int,
        genreId: Int,
        rating: Int,
        callback: Callback<MoviesFilterDTO>
    ){
        kinopoiskapiunofficialAPI.getMoviesFilterYearGenreRating(
            BuildConfig.MOVIES_API_KEY,
            year,
            year,
            genreId,
            rating
        ).enqueue(callback)
    }

    fun getMoviesFilterYearGenre(
        year: Int,
        genreId: Int,
        callback: Callback<MoviesFilterDTO>
    ){
        kinopoiskapiunofficialAPI.getMoviesFilterYearGenre(
            BuildConfig.MOVIES_API_KEY,
            year,
            year,
            genreId
        ).enqueue(callback)
    }

    fun getMoviesFilterYearRating(
        year: Int,
        rating: Int,
        callback: Callback<MoviesFilterDTO>
    ){
        kinopoiskapiunofficialAPI.getMoviesFilterYearRating(
            BuildConfig.MOVIES_API_KEY,
            year,
            year,
            rating
        ).enqueue(callback)
    }

    fun getMoviesFilterGenreRating(
        genreId: Int,
        rating: Int,
        callback: Callback<MoviesFilterDTO>
    ){
        kinopoiskapiunofficialAPI.getMoviesFilterGenreRating(
            BuildConfig.MOVIES_API_KEY,
            genreId,
            rating
        ).enqueue(callback)
    }

}