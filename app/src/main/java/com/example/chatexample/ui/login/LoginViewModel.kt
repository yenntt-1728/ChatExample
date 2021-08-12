package com.example.chatexample.ui.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.chatexample.data.User
import com.example.chatexample.ui.utils.PrefsHelper
import com.example.chatexample.ui.utils.navigateWithAnim
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class LoginViewModel @ViewModelInject constructor(
    private val prefsHelper: PrefsHelper
) : ViewModel() {
    var email = ""
    var password = ""
    val loginSuccessfully = MutableLiveData<Boolean>()

    fun loginUser(listUser : List<User>) {
        listUser.forEach { user ->
            if (email == user.email && password == user.password) {
                setUserLogin(user)
                loginSuccessfully.postValue(true)
                return
            } else {
                loginSuccessfully.postValue(false)
            }
        }
    }

    private fun setUserLogin(user : User) {
        prefsHelper.set("IS_LOGIN", true)
        prefsHelper.set("SELF_USER", user, User::class.java)
    }
}