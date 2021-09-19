package com.example.chatexample.ui.list_call

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.chatexample.data.User
import com.example.chatexample.ui.utils.PrefsHelper

class ListCallViewModel @ViewModelInject constructor(
    prefsHelper: PrefsHelper
) : ViewModel() {
    val selfUser = prefsHelper.get("SELF_USER", User::class.java)
}