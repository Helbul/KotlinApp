package com.example.kotlinapp.ui.favourites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinapp.App
import com.example.kotlinapp.AppState
import com.example.kotlinapp.model.repository.LocalRepository
import com.example.kotlinapp.model.repository.LocalRepositoryImpl

class FavouritesViewModel (
    val favouritesLiveData: MutableLiveData<AppState> = MutableLiveData(),

) : ViewModel() {

    private lateinit var favouritesRepository: LocalRepository

    private var threadGetFavourirtesRepo = Thread {
        favouritesRepository = LocalRepositoryImpl(App.getFavouritesDao())
    }

    init {
        threadGetFavourirtesRepo.start()
    }

    fun getMovie() = getALLFavourites()

    private var threadGetMovieFromRepo = Thread {
        favouritesLiveData.postValue(AppState.Success(favouritesRepository.getAllFavourites()))
    }

    private fun getALLFavourites () {
        favouritesLiveData.value = AppState.Loading
        Thread {
            threadGetFavourirtesRepo.join()
            threadGetMovieFromRepo.start()
        }.start()
    }
}