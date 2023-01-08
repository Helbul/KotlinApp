package com.example.kotlinapp.model.dto

import com.example.kotlinapp.model.dto.Films
import com.google.gson.annotations.SerializedName

data class MoviesDTO(
//    val pagesCount: Int?,
//    val films: Array<MovieDTO

    @SerializedName("pagesCount")
    var pagesCount: Int? = null,

    @SerializedName("films")
    var films: ArrayList<Films> = arrayListOf()
)
