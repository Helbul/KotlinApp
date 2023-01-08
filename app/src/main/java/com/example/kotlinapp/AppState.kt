package com.example.kotlinapp

import com.example.kotlinapp.model.dto.Films

sealed class AppState {
    data class Success(val movieData : ArrayList<Films>) : AppState()
    data class Error(val error : Throwable) : AppState()
    object Loading : AppState()
}
