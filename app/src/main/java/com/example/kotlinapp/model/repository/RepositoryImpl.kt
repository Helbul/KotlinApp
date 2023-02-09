package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.dto.FilterParametersDTO
import com.example.kotlinapp.model.dto.MoviesFilterDTO
import com.example.kotlinapp.model.dto.MoviesTopDTO
import retrofit2.Callback

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {

    override fun getMoviesTop(callback: Callback<MoviesTopDTO>) {
        remoteDataSource.getMoviesTop(callback)
    }

    override fun getMoviesFilter(
        year: Int?,
        genreId: Int?,
        rating: Int?,
        callback: Callback<MoviesFilterDTO>
    ) {

        if (year != null && genreId != null && rating != null) {
            remoteDataSource.getMoviesFilterYearGenreRating(year, genreId, rating, callback)
            return
        }

        if (year != null && genreId != null && rating == null) {
            remoteDataSource.getMoviesFilterYearGenre(year, genreId, callback)
            return
        }

        if (year != null && genreId == null && rating != null) {
            remoteDataSource.getMoviesFilterYearRating(year, rating, callback)
            return
        }

        if (year != null && genreId == null && rating == null) {
            remoteDataSource.getMoviesFilterYear(year, callback)
            return
        }

        if (year == null && genreId != null && rating != null) {
            remoteDataSource.getMoviesFilterGenreRating(genreId, rating, callback)
            return
        }

        if (year == null && genreId != null && rating == null) {
            remoteDataSource.getMoviesFilterGenre(genreId, callback)
            return
        }

        if (year == null && genreId == null && rating != null) {
            remoteDataSource.getMoviesFilterRating(rating, callback)
        }
    }


    override fun getFilterParameters(callback: Callback<FilterParametersDTO>) {
        remoteDataSource.getFilterParameters(callback)
    }
}