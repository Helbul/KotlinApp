package com.example.kotlinapp.model

import android.os.Parcelable
import com.example.kotlinapp.R
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class Genre(val rusName: String): Parcelable {
    ANIME (R.string.anime.toString()),
    ACTION (R.string.action.toString()),
    ADVENTURES (R.string.adventures.toString())
}