package com.example.kotlinapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep

class MovieSearchViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData()
) : ViewModel() {

    fun getLiveData() = liveData

    fun getMovie() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveData.value = AppState.Loading
        Thread {
            sleep(2000)
            liveData.postValue(
                AppState.Success(InMemoryMovieRepository.getALL()[0]))
        }.start()
    }
}