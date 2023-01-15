package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.dto.MoviesDTO
import retrofit2.Callback

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {
    override fun getMoviesTop(callback: Callback<MoviesDTO>) {
        remoteDataSource.getMoviesTop(callback)
    }
}