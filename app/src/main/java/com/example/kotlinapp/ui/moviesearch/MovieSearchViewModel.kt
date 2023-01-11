package com.example.kotlinapp.ui.moviesearch

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlinapp.AppState
import com.example.kotlinapp.model.MoviesLoaderService
import com.example.kotlinapp.model.dto.Films
import com.example.kotlinapp.model.repository.InMemoryMovieRepository

const val MOVIE_INTENT_FILTER = "MOVIE_INTENT_FILTER"
const val MOVIE_RESPONSE_SUCCESS_EXTRA = "MOVIE_RESPONSE_SUCCESS_EXTRA"
const val MOVIES_TOP_EXTRA = "MOVIES_TOP_EXTRA"
const val MOVIE_URL_MALFORMED_EXTRA = "MOVIE_URL_MALFORMED_EXTRA"
const val MOVIE_REQUEST_ERROR_EXTRA = "MOVIE_REQUEST_ERROR_EXTRA"
const val MOVIE_REQUEST_ERROR_MESSAGE_EXTRA = "MOVIE_REQUEST_ERROR_MESSAGE_EXTRA"
const val MOVIE_RESPONSE_EMPTY_EXTRA = "MOVIE_RESPONSE_EMPTY_EXTRA"
const val MOVIE_DATA_EMPTY_EXTRA = "MOVIE_DATA_EMPTY_EXTRA"
const val MOVIE_INTENT_EMPTY_EXTRA = "MOVIE_INTENT_EMPTY_EXTRA"
const val MOVIE_LOAD_RESULT_EXTRA = "MOVIE_LOAD_RESULT_EXTRA"

class MovieSearchViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val liveData: MutableLiveData<AppState> = MutableLiveData()
    private val liveMessage: MutableLiveData<String> = MutableLiveData()

    fun getLiveData() = liveData

    fun getLiveMessage() = liveMessage

    //fun getMovie() = getDataFromLocalSource()

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


    fun getMovie() = getDataFromService()

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(Context: Context, intent: Intent) {
            when (intent.getStringExtra(MOVIE_LOAD_RESULT_EXTRA)) {
                MOVIE_INTENT_EMPTY_EXTRA -> liveMessage.postValue(MOVIE_INTENT_EMPTY_EXTRA)
                MOVIE_DATA_EMPTY_EXTRA -> liveMessage.postValue(MOVIE_DATA_EMPTY_EXTRA)
                MOVIE_RESPONSE_EMPTY_EXTRA -> liveMessage.postValue(MOVIE_RESPONSE_EMPTY_EXTRA)
                MOVIE_REQUEST_ERROR_EXTRA -> liveMessage.postValue(MOVIE_REQUEST_ERROR_EXTRA)
                MOVIE_REQUEST_ERROR_MESSAGE_EXTRA -> liveMessage.postValue(
                    MOVIE_REQUEST_ERROR_MESSAGE_EXTRA)
                MOVIE_URL_MALFORMED_EXTRA -> liveMessage.postValue(MOVIE_URL_MALFORMED_EXTRA)
                MOVIE_RESPONSE_SUCCESS_EXTRA -> {
                    val movies: ArrayList<Films>? = intent.getParcelableArrayListExtra(MOVIES_TOP_EXTRA)
                    movies?.let {
                        liveData.postValue(AppState.Success(it))
                    }
                }
                else -> liveMessage.postValue("Error")
            }

        }

    }
    private fun getDataFromService() {
        liveData.value = AppState.Loading

        //может перенсти?
        getApplication<Application>().applicationContext?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(loadResultsReceiver,
                    IntentFilter(MOVIE_INTENT_FILTER)
                )
        }

        getApplication<Application>().applicationContext?.let {
            it.startService(Intent(it, MoviesLoaderService::class.java))
        //если для старта сервиса нужны будут параметры, добавить их в интент
        }


    }

    fun onDestroy() {
        getApplication<Application>().applicationContext?.let {
            LocalBroadcastManager.getInstance(it).unregisterReceiver(loadResultsReceiver)
        }
    }
}