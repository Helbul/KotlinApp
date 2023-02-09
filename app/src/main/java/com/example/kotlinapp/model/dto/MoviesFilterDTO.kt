package com.example.kotlinapp.model.dto

import com.google.gson.annotations.SerializedName

data class MoviesFilterDTO(
    @SerializedName("total"      ) var total      : Int?             = null,
    @SerializedName("totalPages" ) var totalPages : Int?             = null,
    @SerializedName("items"      ) var items      : ArrayList<Items> = arrayListOf()
)
