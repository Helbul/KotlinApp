package com.example.kotlinapp.model

import android.os.Parcelable
import com.example.kotlinapp.model.dto.Countries
import com.example.kotlinapp.model.dto.Genres
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var filmId: Int ? = null,
    var nameRu: String? = null,
    var nameEn: String? = null,
    var year: String? = null,
    var countries: ArrayList<String> = arrayListOf(),
    var genres: ArrayList<String> = arrayListOf(),
    var rating: Float? = null,
    var posterUrlPreview: String? = null
) : Parcelable
