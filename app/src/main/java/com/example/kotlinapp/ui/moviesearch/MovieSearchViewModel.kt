package com.example.kotlinapp.ui.moviesearch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinapp.AppState
import com.example.kotlinapp.model.dto.MoviesDTO
import com.example.kotlinapp.model.repository.RemoteDataSource
import com.example.kotlinapp.model.repository.Repository
import com.example.kotlinapp.model.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"


class MovieSearchViewModel(
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl (RemoteDataSource())
) : ViewModel() {

    fun getMovie() = getMovieFromRemoteSource()

    private fun getMovieFromRemoteSource(){

        liveData.value = AppState.Loading
        repositoryImpl.getMoviesTop(callback)
    }

    private val callback = object : Callback<MoviesDTO> {
        override fun onResponse(call: Call<MoviesDTO>, response: Response<MoviesDTO>) {
            val serverResponse: MoviesDTO? = response.body()
            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error((Throwable(SERVER_ERROR)))
                }
            )
        }

        override fun onFailure(call: Call<MoviesDTO>, t: Throwable) {
            liveData.postValue(AppState.Error(Throwable(t.message ?:
            REQUEST_ERROR)))
        }
    }

    private fun checkResponse(serverResponse: MoviesDTO): AppState {
        val movies = serverResponse.films
        for (movie in movies) {
            if (movie.filmId == null ||
                    movie.nameRu == null ||
                    movie.rating == null ||
                    movie.year == null) {// может еще какие проверки добавить
               return AppState.Error((Throwable(CORRUPTED_DATA)))
            }
        }
        return AppState.Success(movies)
    }
}