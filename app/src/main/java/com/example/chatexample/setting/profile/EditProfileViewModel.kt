package com.example.chatexample.setting.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatexample.data.User
import com.example.chatexample.ui.utils.PrefsHelper

class EditProfileViewModel @ViewModelInject constructor(
    private val prefsHelper: PrefsHelper
) : ViewModel() {
    val user = prefsHelper.get("SELF_USER", User::class.java)
    var username = user?.username
    var avatar = MutableLiveData<String>().apply {
        value = user?.avatar
    }
    var email = user?.email
    var password = user?.password

    fun saveInfo(user : User) {
        prefsHelper.set("SELF_USER", user, User::class.java)
    }
}