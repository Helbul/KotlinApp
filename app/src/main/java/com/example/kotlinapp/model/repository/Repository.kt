package com.example.kotlinapp.model.repository

import com.example.kotlinapp.model.dto.Films

interface Repository {
    fun getALL(): ArrayList<Films>?
}