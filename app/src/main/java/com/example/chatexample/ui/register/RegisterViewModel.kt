package com.example.chatexample.ui.register

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatexample.data.User
import com.example.chatexample.ui.utils.PrefsHelper
import com.google.firebase.auth.FirebaseAuth


class RegisterViewModel @ViewModelInject constructor(
    private val prefsHelper: PrefsHelper) : ViewModel() {
    var email = ""
    var password = ""
    val registerSuccess = MutableLiveData<Boolean>()

    fun setUserRegister(user: User){
        prefsHelper.set("IS_LOGIN", true)
        prefsHelper.set("SELF_USER", user, User::class.java)
    }
}