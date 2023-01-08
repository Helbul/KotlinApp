package com.example.kotlinapp.ui.moviesearch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinapp.AppState
import com.example.kotlinapp.model.repository.InMemoryMovieRepository
import java.lang.Thread.sleep

class MovieSearchViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData()
) : ViewModel() {
//
    private val TAG = "TAG - MovieSearchViewModel"

    fun getLiveData() = liveData

    fun getMovie() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveData.value = AppState.Loading
        Thread {
            liveData.postValue(
                InMemoryMovieRepository.getALL()?.let {
                    AppState.Success(it)
                }
//                    ?: run {
//                   AppState.Error...
//                }
            )
        }.start()
    }
}