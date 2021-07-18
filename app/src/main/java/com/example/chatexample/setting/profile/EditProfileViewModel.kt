package com.example.chatexample.setting.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditProfileViewModel @ViewModelInject constructor() : ViewModel() {
    var username = MutableLiveData<String>()
    var avatar = MutableLiveData<String>()
}