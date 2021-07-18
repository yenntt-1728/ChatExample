package com.example.chatexample.ui.foward

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.chatexample.ui.utils.PrefsHelper
import dagger.hilt.android.scopes.ViewModelScoped

class ForwardViewModel @ViewModelInject constructor(
    private val prefsHelper: PrefsHelper
) : ViewModel() {

    fun logout() {
        prefsHelper.remove("IS_LOGIN")
    }
}