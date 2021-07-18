package com.example.chatexample.ui.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrefsHelper @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

    fun set(key: String, value: String) =
        sharedPreferences.edit().apply { putString(key, value) }.apply()

    fun get(key: String, defValue: String) = sharedPreferences.getString(key, defValue) ?: defValue

    fun set(key: String, value: Int) =
        sharedPreferences.edit().apply { putInt(key, value) }.apply()

    fun get(key: String, defValue: Int) = sharedPreferences.getInt(key, defValue)

    fun set(key: String, value: Long) =
        sharedPreferences.edit().apply { putLong(key, value) }.apply()

    fun get(key: String, defValue: Long) = sharedPreferences.getLong(key, defValue)

    fun set(key: String, value: Boolean) =
        sharedPreferences.edit().apply { putBoolean(key, value) }.apply()

    fun get(key: String, defValue: Boolean) = sharedPreferences.getBoolean(key, defValue)

    fun contains(key: String) = sharedPreferences.contains(key)

    fun clear() {
        sharedPreferences.apply {
            all.keys.forEach {
                remove(it)
            }
        }
    }

    fun remove(key: String) = sharedPreferences.edit().apply { remove(key) }.apply()

}