package com.example.chatexample.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.chatexample.ui.utils.PrefsHelper

class HomeViewModel @ViewModelInject constructor(
    private val prefsHelper: PrefsHelper) : ViewModel() {

    fun isLogin() : Boolean {
        return prefsHelper.get("IS_LOGIN", false)
    }
}