package com.example.kotlinapp.utils

fun startThread (f: () -> Unit) {
    Thread {
        try {
            f()
        } catch (e: Exception) {
            e.stackTrace
        }

    }.start()
}