package com.example.kotlinapp.model.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Genres (
    @SerializedName("genre" ) var genre : String? = null
) : Parcelable