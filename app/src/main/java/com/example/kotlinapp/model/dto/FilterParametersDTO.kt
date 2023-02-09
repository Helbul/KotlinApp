package com.example.kotlinapp.model.dto

import com.google.gson.annotations.SerializedName

data class FilterParametersDTO(
    @SerializedName("genres"    ) var genres    : ArrayList<Genres>    = arrayListOf(),
    @SerializedName("countries" ) var countries : ArrayList<Countries> = arrayListOf()
)
