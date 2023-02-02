package com.example.kotlinapp.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(MovieEntity::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class FavouritesDataBase : RoomDatabase() {
    abstract fun favouritesDao() : FavouritesDao
}