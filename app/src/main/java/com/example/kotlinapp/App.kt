package com.example.kotlinapp

import android.app.Application
import androidx.room.Room
import com.example.kotlinapp.model.room.FavouritesDao
import com.example.kotlinapp.model.room.FavouritesDataBase

class App : Application(){

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: FavouritesDataBase? = null
        private const val DB_NAME = "Favourites.db"

        fun getFavouritesDao() : FavouritesDao {
                if (db == null) {
                    synchronized(FavouritesDataBase::class.java) {
                        if (appInstance == null) throw IllegalStateException(" APP must not be null ")

                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            FavouritesDataBase::class.java,
                            DB_NAME
                        )
                            //.allowMainThreadQueries() // НЕ ИСПОЛЬЗОВАТЬ В ДЗ!! ИСПОЛЬЗОВАТЬ ОТДЕЛЬНЫЙ ПОТОК
                            .build()
                    }
                }
                return db!!.favouritesDao()
        }
    }
}