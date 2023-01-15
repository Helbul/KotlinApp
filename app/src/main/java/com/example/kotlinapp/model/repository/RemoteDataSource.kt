package com.example.kotlinapp.model.repository

import android.util.Log
import com.example.kotlinapp.BuildConfig
import com.example.kotlinapp.model.dto.MoviesDTO
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

    fun getMoviesTop(callback: Callback<MoviesDTO>) {
        kinopoiskapiunofficialAPI.getMoviesTop(BuildConfig.MOVIES_API_KEY).enqueue(callback)
    }

}