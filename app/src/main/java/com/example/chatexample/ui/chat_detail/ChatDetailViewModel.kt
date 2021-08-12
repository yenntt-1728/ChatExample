package com.example.chatexample.ui.chat_detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.chatexample.data.User
import com.example.chatexample.ui.utils.PrefsHelper

class ChatDetailViewModel @ViewModelInject constructor(private val prefsHelper: PrefsHelper) : ViewModel() {

    fun getSelfUser() : User? {
       return prefsHelper.get("SELF_USER", User::class.java)
    }
}