package com.example.kotlinapp.model.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Countries(
    @SerializedName("country" ) var country : String? = null
) : Parcelable
