package com.example.chatexample.ui.chat

import com.example.chatexample.data.User

interface ChatListener {
    fun openChatDetail(user : User)
}