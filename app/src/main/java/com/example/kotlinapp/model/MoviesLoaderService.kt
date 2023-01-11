package com.example.kotlinapp.model

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.kotlinapp.BuildConfig
import com.example.kotlinapp.model.dto.Films
import com.example.kotlinapp.model.dto.MoviesDTO
import com.example.kotlinapp.ui.moviesearch.*
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000
private const val REQUEST_API_KEY = "X-API-KEY"


class MoviesLoaderService(name: String = "MoviesLoaderService") : IntentService(name) {
    private val broadcastIntent = Intent(MOVIE_INTENT_FILTER)

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onHandleIntent(intent: Intent?) {
        if (intent == null) {
            onEmptyIntent()
        }
        //else //добавить проверку значения передавемых значений в intent (урок 6)
        // onEmptyData()
        else {
            loadMoviesTop()
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun loadMoviesTop() {
        try {
            val uri = URL("https://kinopoiskapiunofficial.tech/api/v2.2/films/top")
            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.apply {
                    requestMethod = REQUEST_GET
                    readTimeout = REQUEST_TIMEOUT
                    addRequestProperty(REQUEST_API_KEY, BuildConfig.MOVIES_API_KEY)
                }
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }

                val moviesDTO: MoviesDTO = Gson().fromJson(lines, MoviesDTO::class.java)

                onResponse(moviesDTO)

            } catch (e: Exception) {
                onErrorRequest(e.message ?: "Empty error")

            } finally {
                urlConnection.disconnect()
            }

        } catch (e: MalformedURLException) {
            onMalformedURL()
        }

    }

    private fun onErrorRequest(error: String) {
        putLoadResult(MOVIE_REQUEST_ERROR_EXTRA)
        broadcastIntent.putExtra(MOVIE_REQUEST_ERROR_MESSAGE_EXTRA, error)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onMalformedURL() {
        putLoadResult(MOVIE_URL_MALFORMED_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onResponse(moviesDTO: MoviesDTO) {
        val movies = moviesDTO.films
        if (movies == null) {
            onEmptyResponse()
        } else {
            onSuccessResponse(movies)
        }
    }

    private fun onEmptyResponse() {
        putLoadResult(MOVIE_RESPONSE_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyIntent() {
        putLoadResult(MOVIE_INTENT_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun onEmptyData() {
        putLoadResult(MOVIE_DATA_EMPTY_EXTRA)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }


    private fun onSuccessResponse(movies: ArrayList<Films>) {
        putLoadResult(MOVIE_RESPONSE_SUCCESS_EXTRA)
        //val bundle = Bundle()
        //bundle.putParcelableArrayList("111", movies)
        broadcastIntent.putParcelableArrayListExtra(MOVIES_TOP_EXTRA, movies)
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
    }

    private fun putLoadResult(result: String) {
        broadcastIntent.putExtra(MOVIE_LOAD_RESULT_EXTRA, result)
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }

        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

}