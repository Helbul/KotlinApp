package com.example.kotlinapp.model.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Items(
    @SerializedName("kinopoiskId"      ) var kinopoiskId      : Int?                 = null,
    @SerializedName("imdbId"           ) var imdbId           : String?              = null,
    @SerializedName("nameRu"           ) var nameRu           : String?              = null,
    @SerializedName("nameEn"           ) var nameEn           : String?              = null,
    @SerializedName("nameOriginal"     ) var nameOriginal     : String?              = null,
    @SerializedName("countries"        ) var countries        : ArrayList<Countries> = arrayListOf(),
    @SerializedName("genres"           ) var genres           : ArrayList<Genres>    = arrayListOf(),
    @SerializedName("ratingKinopoisk"  ) var ratingKinopoisk  : Double?              = null,
    @SerializedName("ratingImdb"       ) var ratingImdb       : Double?              = null,
    @SerializedName("year"             ) var year             : Int?                 = null,
    @SerializedName("type"             ) var type             : String?              = null,
    @SerializedName("posterUrl"        ) var posterUrl        : String?              = null,
    @SerializedName("posterUrlPreview" ) var posterUrlPreview : String?              = null
) : Parcelable
