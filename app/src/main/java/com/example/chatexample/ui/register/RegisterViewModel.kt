package com.example.chatexample.ui.register

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatexample.ui.utils.PrefsHelper
import com.google.firebase.auth.FirebaseAuth


class RegisterViewModel @ViewModelInject constructor(
    private val prefsHelper: PrefsHelper) : ViewModel() {
    var email = ""
    var password = ""
    val registerSuccess = MutableLiveData<Boolean>()

    fun registerUser(auth : FirebaseAuth) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
            if (it.isSuccessful) {
                registerSuccess.postValue(true)
            } else {
                val e = it.exception
                Log.d("ERROR", it.exception.toString())
                registerSuccess.postValue(false)
            }
        }
    }

    fun setUserRegister(){
        prefsHelper.set("IS_LOGIN", true)
    }
}