package com.example.kotlinapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinapp.databinding.ActivityMainBinding
import com.example.kotlinapp.ui.moviesearch.MovieSearchFragment
import com.example.kotlinapp.ui.showSnackbar


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val myBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            binding.root.showSnackbar("BroadcastReceiver - CONNECTIVITY_ACTION")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(myBroadcastReceiver, intentFilter)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieSearchFragment.newInstance())
                .commitNow()
        }

    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(myBroadcastReceiver)
    }
}