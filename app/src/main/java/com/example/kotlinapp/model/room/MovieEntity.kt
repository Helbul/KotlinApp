package com.example.kotlinapp.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kotlinapp.model.dto.Countries
import com.example.kotlinapp.model.dto.Genres

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long= 0,
    var filmId: Int ? = null,
    var nameRu: String? = null,
    var nameEn: String? = null,
    var year: String? = null,
    var countries: ArrayList<String> = arrayListOf(),
    var genres: ArrayList<String> = arrayListOf(),
    var rating: String? = null,
    var posterUrlPreview: String? = null
    )
