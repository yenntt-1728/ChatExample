package com.example.chatexample.ui.utils

import android.util.Patterns
import androidx.core.util.PatternsCompat

fun String.isEmail(): Boolean {
    return !isNullOrEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()
}
