package com.example.kotlinapp.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.kotlinapp.BuildConfig.MOVIES_API_KEY
import com.example.kotlinapp.model.dto.MoviesTopDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object MoviesLoader {
    fun loadMoviesTop(): MoviesTopDTO? {
        val uri = URL("https://kinopoiskapiunofficial.tech/api/yt")
        lateinit var urlConnection: HttpsURLConnection

        return try {
            urlConnection = uri.openConnection() as HttpsURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.addRequestProperty(
                "X-API-KEY", MOVIES_API_KEY
            )
            urlConnection.readTimeout = 10000
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                getLinesForOld(bufferedReader)
            } else {
                getLines(bufferedReader)
            }

            Gson().fromJson(lines, MoviesTopDTO::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        } finally {
            urlConnection.disconnect()
        }
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