package com.example.kotlinapp.model.room

import androidx.room.*


@Dao
interface FavouritesDao {
    @Query("SELECT * FROM MovieEntity")
    fun all() : List<MovieEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: MovieEntity)

    @Update
    fun update(entity: MovieEntity)

    //не работает из за неправильной конвертации Movie в MovieEntity
    @Delete
    fun delete(entity: MovieEntity)

    @Query("DELETE FROM MovieEntity WHERE id = :id")
    fun deleteById(id: Long)

    @Query("DELETE FROM MovieEntity WHERE filmId = :filmId")
    fun deleteByFilmId(filmId: Long)

    @Query("SELECT EXISTS (SELECT 1 FROM MovieEntity WHERE filmId = :filmId)")
    fun exists(filmId : Int): Boolean
}