package com.example.kotlinapp.model.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Films (

    @SerializedName("filmId"           ) var filmId           : Int?                 = null,
    @SerializedName("nameRu"           ) var nameRu           : String?              = null,
    @SerializedName("nameEn"           ) var nameEn           : String?              = null,
    @SerializedName("year"             ) var year             : String?              = null,
    @SerializedName("filmLength"       ) var filmLength       : String?              = null,
    @SerializedName("countries"        ) var countries        : ArrayList<Countries> = arrayListOf(),
    @SerializedName("genres"           ) var genres           : ArrayList<Genres>    = arrayListOf(),
    @SerializedName("rating"           ) var rating           : String?              = null,
    @SerializedName("ratingVoteCount"  ) var ratingVoteCount  : Int?                 = null,
    @SerializedName("posterUrl"        ) var posterUrl        : String?              = null,
    @SerializedName("posterUrlPreview" ) var posterUrlPreview : String?              = null,
    @SerializedName("ratingChange"     ) var ratingChange     : String?              = null

) : Parcelable
