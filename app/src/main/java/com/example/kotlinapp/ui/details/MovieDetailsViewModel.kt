package com.example.kotlinapp.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinapp.App
import com.example.kotlinapp.model.Movie
import com.example.kotlinapp.model.repository.LocalRepository
import com.example.kotlinapp.model.repository.LocalRepositoryImpl
import com.example.kotlinapp.utils.startThread

class MovieDetailsViewModel (
    private val liveDataMovie: MutableLiveData<Movie> = MutableLiveData(),
    private val liveDataIsFavourite: MutableLiveData<Boolean> = MutableLiveData(),
    private val favouritesRepository: LocalRepository = LocalRepositoryImpl(App.getFavouritesDao())
) : ViewModel() {

    fun getLiveDataIsFavourite() = liveDataIsFavourite

    fun setLiveDataFavourite(isFavourite: Boolean) {
        liveDataIsFavourite.value = isFavourite

        if (isFavourite) {
            saveMovieToDB(liveDataMovie.value!!)
        } else {
            removeMovieFromDB(liveDataMovie.value!!)
        }
    }

    fun getLiveDataMovie() = liveDataMovie

    fun setLiveDataMovie(movie: Movie) {
        liveDataMovie.value = movie
    }

    private fun saveMovieToDB (movie: Movie) {
        startThread { favouritesRepository.saveEntity(movie) }
    }

    private fun removeMovieFromDB (movie: Movie) {
        startThread { favouritesRepository.removeEntity(movie) }
    }

    fun containInFavourites() {
        startThread {
            if (liveDataMovie.value != null) {
            liveDataIsFavourite.postValue(favouritesRepository.containsEntity(liveDataMovie.value!!))
             } else {
            liveDataIsFavourite.postValue(false)
             }
        }
    }

}