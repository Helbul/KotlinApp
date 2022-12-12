package com.example.kotlinapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.hello_button)
        button.setOnClickListener(View.OnClickListener {
            Toast.makeText(applicationContext, "Hello!", Toast.LENGTH_LONG).show()
        })

        val helloText: TextView = findViewById(R.id.hello_text)

        testFor()

        val message = testWhen(Genre.ACTION)
        helloText.text = message
    }


    private fun testFor() {
        val movies = Repository.movies
        for (movie in movies) {
            println(movie.toString())
        }
    }

    private fun testWhen(genre: Genre): String {
        val message = when (genre) {
            Genre.ACTION -> "Крутяк!"
            Genre.FICTION -> "Фантастика!"
            Genre.COMEDY -> "Уморительно!"
        }
        return message
    }

}