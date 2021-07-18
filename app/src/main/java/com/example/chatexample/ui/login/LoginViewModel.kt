package com.example.chatexample.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatexample.ui.utils.PrefsHelper
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel @ViewModelInject constructor(
    private val prefsHelper: PrefsHelper
) : ViewModel() {
    var email = ""
    var password = ""
    val loginSuccessfully = MutableLiveData<Boolean>()

    fun loginUser(auth : FirebaseAuth) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                loginSuccessfully.postValue(true)
                prefsHelper.set("IS_LOGIN", true)
            } else {
                loginSuccessfully.postValue(false)
            }
        }
    }

    fun setUserLogin(){
        prefsHelper.set("IS_LOGIN", true)
    }
}