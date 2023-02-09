package com.example.kotlinapp.ui.moviesearch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinapp.AppState
import com.example.kotlinapp.model.dto.Genres
import com.example.kotlinapp.model.dto.MoviesFilterDTO
import com.example.kotlinapp.model.dto.MoviesTopDTO
import com.example.kotlinapp.model.repository.RemoteDataSource
import com.example.kotlinapp.model.repository.Repository
import com.example.kotlinapp.model.repository.RepositoryImpl
import com.example.kotlinapp.utils.convertMovieTopDtoToMovie
import com.example.kotlinapp.utils.convertMoviesFilterDtoToMovie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"


class MovieSearchViewModel(
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    val liveDataChipId : MutableLiveData<Int?> = MutableLiveData(),
    val liveDataRating: MutableLiveData<Float?> = MutableLiveData(),
    val liveDataYear: MutableLiveData<Int?> = MutableLiveData(),
    val liveDataGenre: MutableLiveData<String?> = MutableLiveData(),
    val liveDataListGenre: MutableLiveData<List<Genres>> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl (RemoteDataSource())
) : ViewModel() {

//    companion object {
//        fun getListGenres() : List<Genres> {
//
//        }
//    }


    fun getMoviesTop() = getMovieFromRemoteSource()

    fun getMoviesFilter() = getMoviesFilterFromRemoteSource(
        liveDataYear.value,
        liveDataGenre.value,
        liveDataRating.value
    )

    private fun getMovieFromRemoteSource(){
        liveData.value = AppState.Loading
        repositoryImpl.getMoviesTop(callbackTop)
    }

    private fun getMoviesFilterFromRemoteSource(year: Int?, genreString: String?, rating: Float?) {
        liveData.value = AppState.Loading
        val ratingInt = rating?.toInt()
        Log.d("OLGA", "getMoviesFilterFromRemoteSource ratingInt: $ratingInt")
        Log.d("OLGA", "getMoviesFilterFromRemoteSource year: $year")
        //TO DO genreString -> genreID
        val genreID : Int? = null
        repositoryImpl.getMoviesFilter(year, genreID, ratingInt, callbackFilter)
    }

    private val callbackFilter = object : Callback<MoviesFilterDTO> {
        override fun onResponse(call: Call<MoviesFilterDTO>, response: Response<MoviesFilterDTO>) {
            val serverResponse: MoviesFilterDTO? = response.body()
            Log.d("OLGA", "callbackFilter onResponse: 111111")
            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponseFilter(serverResponse)
                } else {
                    AppState.Error((Throwable(SERVER_ERROR)))
                }
            )
        }

        override fun onFailure(call: Call<MoviesFilterDTO>, t: Throwable) {
            liveData.postValue(AppState.Error(Throwable(t.message ?:
            REQUEST_ERROR)))
        }

    }

    private val callbackTop = object : Callback<MoviesTopDTO> {
        override fun onResponse(call: Call<MoviesTopDTO>, response: Response<MoviesTopDTO>) {
            val serverResponse: MoviesTopDTO? = response.body()
            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponseTop(serverResponse)
                } else {
                    AppState.Error((Throwable(SERVER_ERROR)))
                }
            )
        }

        override fun onFailure(call: Call<MoviesTopDTO>, t: Throwable) {
            liveData.postValue(AppState.Error(Throwable(t.message ?:
            REQUEST_ERROR)))
        }
    }

    private fun checkResponseTop(serverResponse: MoviesTopDTO): AppState {
        val movies = serverResponse.films
        for (movie in movies) {
            if (movie.filmId == null ||
                    movie.nameRu == null ||
                    movie.rating == null ||
                    movie.year == null) {// может еще какие проверки добавить
               return AppState.Error((Throwable(CORRUPTED_DATA)))
            }
        }
        return AppState.Success(convertMovieTopDtoToMovie(serverResponse))
    }

    private fun checkResponseFilter(serverResponse: MoviesFilterDTO): AppState {
        val movies = serverResponse.items
        for (movie in movies) {
            if (movie.kinopoiskId == null ||
                    movie.nameRu == null ||
                    movie.ratingKinopoisk == null ||
                    movie.year == null
                ) {// может еще какие проверки добавить
               return AppState.Error((Throwable(CORRUPTED_DATA)))
            }
        }
        Log.d("OLGA", "checkResponseFilter: ++++++")
        return AppState.Success(convertMoviesFilterDtoToMovie(serverResponse))
    }

    fun setLiveDataChipId (chipId: Int?) {
        liveDataChipId.value = chipId
    }

    fun getLiveDataChipId() : Int? {
        return liveDataChipId.value
    }

//    private fun searchGenreId (genreString: String?) : Int? {
//        when (genreString) {
//            "Аниме" -> return 1
//
//            else -> return null
//        }
//    }
}