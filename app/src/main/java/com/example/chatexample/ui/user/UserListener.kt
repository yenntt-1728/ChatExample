package com.example.chatexample.ui.user

import com.example.chatexample.data.User

interface UserListener {
    fun onItemUserClick(item : User)
    fun onVideoCallUser(item : User)
}