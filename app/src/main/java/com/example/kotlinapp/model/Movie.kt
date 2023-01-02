package com.example.kotlinapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val name: String = "Фильм",
    val description: String? = "Описание",
    val rating: Float = 5.0F,
    val genre: Genre = Genre.ACTION,
    val year: Int = 1900
) : Parcelable
