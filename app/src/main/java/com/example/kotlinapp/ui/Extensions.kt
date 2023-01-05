package com.example.kotlinapp.ui

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(text: String, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, length).show()
}

fun View.showSnackbar(textFromResources: Int, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, resources.getString(textFromResources), length).show()
}
